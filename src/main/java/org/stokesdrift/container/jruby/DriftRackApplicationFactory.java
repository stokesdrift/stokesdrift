package org.stokesdrift.container.jruby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.exceptions.RaiseException;
import org.jruby.javasupport.JavaUtil;
import org.jruby.rack.DefaultRackApplicationFactory;
import org.jruby.rack.DefaultRackConfig;
import org.jruby.rack.RackConfig;
import org.jruby.rack.RackContext;
import org.jruby.rack.RackLogger;
import org.jruby.runtime.builtin.IRubyObject;

public class DriftRackApplicationFactory extends DefaultRackApplicationFactory implements RubyRuntimeManager {

	private RackContext rackServletContext;
	private RubyInstanceConfig runtimeConfig;
	private static final Logger logger = Logger.getLogger(DriftRackApplicationFactory.class.getName());


	/**
	 * Creates a new application instance (without initializing it). <br/>
	 * NOTE: exception handling is left to the outer factory.
	 *
	 * @return new application instance
	 */
	@Override
	public org.jruby.rack.RackApplication newApplication() {
		return new JrubyRackApplicationImpl(new ApplicationObjectFactory() {
			public IRubyObject create(Ruby runtime) {
				return createApplicationObject(runtime);
			}
		}, this);
	}

	public RubyInstanceConfig getRuntimeConfig() {
		return runtimeConfig;
	}

	/**
	 * Sets all the jar files as load paths
	 *
	 * @param config
	 */
	protected void setLoadPaths(RubyInstanceConfig config) {
		String libDir = getEnvVariable("STOKESDRIFT_LIB_DIR");
		List<String> loadPaths = new ArrayList<String>();

		if (libDir != null) {
			File dir = new File(libDir);
			String[] fileList = dir.list();

			for (String fileName : fileList) {
				StringBuilder sb = new StringBuilder(libDir).append(File.separatorChar).append(fileName);
				loadPaths.add(sb.toString());
			}
		}
		// Add root gems eg: /opt/jruby/lib/ruby/gems/shared/gems/
		String jrubyRootDir = getEnvVariable("JRUBY_HOME");
		if (jrubyRootDir != null) {
			StringBuilder gemPath = new StringBuilder();
			gemPath.append(jrubyRootDir);
			String[] paths = new String[] { "lib", "jruby", "gems", "shared", "gems" };
			for (String path : paths) {
				gemPath.append(File.separator).append(path);
			}
			File gemPathDir = new File(gemPath.toString());
			if (gemPathDir.exists()) {
				File[] gemPaths = gemPathDir.listFiles();
				for (File path : gemPaths) {
					loadPaths.add(path.getAbsolutePath());
				}
			}
		}
		String gemPathEnv = getEnvVariable("GEM_PATH");
		if (gemPathEnv != null) {
			loadPaths.add(gemPathEnv);
		}
		config.setLoadPaths(loadPaths);
		config.setRunRubyInProcess(true);
		config.setDebug(true);
		config.setLoader(ClassLoader.getSystemClassLoader());
	}

	protected RubyInstanceConfig initRuntimeConfig(final RubyInstanceConfig config) {
		final RackConfig rackConfig = rackServletContext.getConfig();
		config.setLoader(Thread.currentThread().getContextClassLoader());
		final Map<String, String> newEnv = rackConfig.getRuntimeEnvironment();
		if (newEnv != null) {
			config.setEnvironment(newEnv);
		}
		config.processArguments(rackConfig.getRuntimeArguments());
		if (rackConfig.getCompatVersion() != null) {
			config.setCompatVersion(rackConfig.getCompatVersion());
		}
		String home = getEnvVariable("JRUBY_HOME");
		logger.info("stokesdrift.env JRUBY_HOME=" + home);
		try {
			if (home != null) {
				config.setJRubyHome(home);
			}
		} catch (Exception e) {
			rackServletContext.log(RackLogger.DEBUG, "won't set-up jruby.home from jar", e);
		}
		return config;
	}

	/**
	 * Returns either a system property or an environment variable
	 *
	 */
	public String getEnvVariable(String name) {
		String envValue = System.getProperty(name);
		if (envValue == null) {
			envValue = System.getenv(name);
		}
		return envValue;
	}

	/**
	 * Initialize this factory using the given context. <br/>
	 * NOTE: exception handling is left to the outer factory.
	 *
	 * @param rackContext
	 */
	@Override
	public void init(final RackContext rackContext) {
		this.rackServletContext = rackContext;
		super.init(rackContext);
		this.runtimeConfig = createRuntimeConfig();
	}

	// TODO contribute back on integration points for the Jruby Rack project
	@Override
	public Ruby newRuntime() throws RaiseException {
		setLoadPaths(runtimeConfig);
		Ruby runtime = Ruby.getThreadLocalRuntime();
		if (runtime == null) {
			runtime = Ruby.newInstance(runtimeConfig);
		}
		initDriftRuntime(runtime);
		return runtime;
	}

	/**
	 * TODO contribute back hooks for integration purposes Initializes the
	 * runtime (exports the context, boots the Rack handler).
	 *
	 * NOTE: (package) visible due specs
	 *
	 * @param runtime
	 */
	protected void initDriftRuntime(final Ruby runtime) {
		// set $servlet_context :
		runtime.getGlobalVariables().set("$servlet_context", JavaUtil.convertJavaToRuby(runtime, rackServletContext));
		// load our (servlet) Rack handler :
		runtime.evalScriptlet("require 'rack/handler/servlet'");

		// NOTE: this is experimental stuff and might change in the future :
		String env = rackServletContext.getConfig().getProperty("jruby.rack.handler.env");
		// currently supported "env" values are 'default' and 'servlet'
		if (env != null) {
			runtime.evalScriptlet("Rack::Handler::Servlet.env = '" + env + "'");
		}
		String response = rackServletContext.getConfig().getProperty("jruby.rack.handler.response");
		if (response == null) {
			response = rackServletContext.getConfig().getProperty("jruby.rack.response");
		}
		if (response != null) { // JRuby::Rack::JettyResponse ->
								// 'jruby/rack/jetty_response'
			runtime.evalScriptlet("Rack::Handler::Servlet.response = '" + response + "'");
		}

		// configure (Ruby) bits and pieces :
		String dechunk = rackServletContext.getConfig().getProperty("jruby.rack.response.dechunk");
		Boolean dechunkFlag = (Boolean) DefaultRackConfig.toStrictBoolean(dechunk, null);
		if (dechunkFlag != null) {
			runtime.evalScriptlet("JRuby::Rack::Response.dechunk = " + dechunkFlag + "");
		} else { // dechunk null (default) or not a true/false value ... we're
					// patch :
			runtime.evalScriptlet("JRuby::Rack::Booter.on_boot { require 'jruby/rack/chunked' }");
			// `require 'jruby/rack/chunked'` that happens after Rack is loaded
		}
		String swallowAbort = rackServletContext.getConfig().getProperty("jruby.rack.response.swallow_client_abort");
		Boolean swallowAbortFlag = (Boolean) DefaultRackConfig.toStrictBoolean(swallowAbort, null);
		if (swallowAbortFlag != null) {
			runtime.evalScriptlet("JRuby::Rack::Response.swallow_client_abort = " + swallowAbortFlag + "");
		}
	}

	public RackContext getContext() {
		return rackServletContext;
	}
}
