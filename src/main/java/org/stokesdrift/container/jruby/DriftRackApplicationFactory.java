package org.stokesdrift.container.jruby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.exceptions.RaiseException;
import org.jruby.javasupport.JavaUtil;
import org.jruby.rack.DefaultRackApplicationFactory;
import org.jruby.rack.DefaultRackConfig;
import org.jruby.rack.RackContext;
import org.jruby.runtime.builtin.IRubyObject;

public class DriftRackApplicationFactory extends DefaultRackApplicationFactory implements RubyRuntimeManager {

	private RackContext rackServletContext;
	private RubyInstanceConfig runtimeConfig;

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

	/**
	 * Sets all the jar files as load paths
	 *
	 * @param config
	 */
	protected void setLoadPaths(RubyInstanceConfig config) {
	   String libDir = System.getProperty("STOKESDRIFT_LIB_DIR");
	   if (libDir != null) {
		  File dir = new File(libDir);
		  String[] fileList = dir.list();
		  List<String> loadPaths = new ArrayList<String>();
		  for(String fileName : fileList) {
			  StringBuilder sb = new StringBuilder(libDir).append(File.separatorChar).append(fileName);
			  loadPaths.add(sb.toString());
		  }
		  config.setLoadPaths(loadPaths);
		  config.setRunRubyInProcess(true);
		  config.setDebug(true);
		  config.setLoader(ClassLoader.getSystemClassLoader());
	   }
	}

    /**
     * Initialize this factory using the given context.
     * <br/>
     * NOTE: exception handling is left to the outer factory.
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
     * TODO contribute back hooks for integration purposes
     * Initializes the runtime (exports the context, boots the Rack handler).
     *
     * NOTE: (package) visible due specs
     *
     * @param runtime
     */
    protected void initDriftRuntime(final Ruby runtime) {
        // set $servlet_context :
        runtime.getGlobalVariables().set(
            "$servlet_context", JavaUtil.convertJavaToRuby(runtime, rackServletContext)
        );
        // load our (servlet) Rack handler :
        runtime.evalScriptlet("require 'rack/handler/servlet'");

        // NOTE: this is experimental stuff and might change in the future :
        String env = rackServletContext.getConfig().getProperty("jruby.rack.handler.env");
        // currently supported "env" values are 'default' and 'servlet'
        if ( env != null ) {
            runtime.evalScriptlet("Rack::Handler::Servlet.env = '" + env + "'");
        }
        String response = rackServletContext.getConfig().getProperty("jruby.rack.handler.response");
        if ( response == null ) {
            response = rackServletContext.getConfig().getProperty("jruby.rack.response");
        }
        if ( response != null ) { // JRuby::Rack::JettyResponse -> 'jruby/rack/jetty_response'
            runtime.evalScriptlet("Rack::Handler::Servlet.response = '" + response + "'");
        }

        // configure (Ruby) bits and pieces :
        String dechunk = rackServletContext.getConfig().getProperty("jruby.rack.response.dechunk");
        Boolean dechunkFlag = (Boolean) DefaultRackConfig.toStrictBoolean(dechunk, null);
        if ( dechunkFlag != null ) {
            runtime.evalScriptlet("JRuby::Rack::Response.dechunk = " + dechunkFlag + "");
        }
        else { // dechunk null (default) or not a true/false value ... we're patch :
            runtime.evalScriptlet("JRuby::Rack::Booter.on_boot { require 'jruby/rack/chunked' }");
            // `require 'jruby/rack/chunked'` that happens after Rack is loaded
        }
        String swallowAbort = rackServletContext.getConfig().getProperty("jruby.rack.response.swallow_client_abort");
        Boolean swallowAbortFlag = (Boolean) DefaultRackConfig.toStrictBoolean(swallowAbort, null);
        if ( swallowAbortFlag != null ) {
            runtime.evalScriptlet("JRuby::Rack::Response.swallow_client_abort = " + swallowAbortFlag + "");
        }
    }

    public RackContext getContext()
    {
    	return rackServletContext;
    }
}
