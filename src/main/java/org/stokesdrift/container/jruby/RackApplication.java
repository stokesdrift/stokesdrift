package org.stokesdrift.container.jruby;

import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ListenerInfo;

import java.io.File;
import java.io.FileInputStream;

import javax.inject.Named;
import javax.servlet.DispatcherType;

import org.jruby.rack.RackFilter;
import org.jruby.rack.util.IOHelpers;
import org.stokesdrift.config.ApplicationConfig;
import org.stokesdrift.container.Application;

/**
 * Sets up the configuration for a rack application
 * 
 * Jruby-Rack configs:
 * <ul>
 * <li>rackup: Rackup script for configuring how the Rack application is
 * mounted. Required for Rack-based applications other than Rails. Can be
 * omitted if a config.ru is included in the application root.</li>
 * <li>public.root: Relative path to the location of your application's static
 * assets. Defaults to /.</li>
 * <li>gem.path: Relative path to the bundled gem repository. Defaults to
 * /WEB-INF/gems.</li>
 * <li>jruby.compat.version: Set to "1.8" or "1.9" to make JRuby run a specific
 * version of Ruby (same as the --1.8 / --1.9 command line flags).</li>
 * <li>jruby.min.runtimes: For non-threadsafe Rails applications using a runtime
 * pool, specify an integer minimum number of runtimes to hold in the pool.</li>
 * <li>jruby.max.runtimes: For non-threadsafe Rails applications, an integer
 * maximum number of runtimes to keep in the pool.</li>
 * <li>jruby.runtime.init.threads: How many threads to use for initializing
 * application runtimes when pooling is used (default is 4). It does not make
 * sense to set this value higher than</li>
 * </ul>
 * 
 * @author driedtoast
 * 
 */
@Named(value = "rack_application")
public class RackApplication implements Application {

	private static final String RACK_FILTER = "RackFilter";

	private ApplicationConfig config;

	@Override
	public DeploymentInfo getDeploymentInfo() {
		FilterInfo filter = Servlets.filter(RACK_FILTER, RackFilter.class);
		// TODO extend listener and setup the jruby env based on reloaded gems
		// and jars
		// Override appfactory?

		ListenerInfo listenerInfo = Servlets.listener(ServletContextListener.class);

		DeploymentInfo di = new DeploymentInfo().addListener(listenerInfo).setContextPath(config.getRootUrlPath()).addFilter(filter)
				.addFilterUrlMapping(RACK_FILTER, "/*", DispatcherType.REQUEST).setDeploymentName(config.getName())
				.setClassLoader(ClassLoader.getSystemClassLoader());
		setupInitParams(di);
		return di;
	}

	protected void setupInitParams(DeploymentInfo deployInfo) {
		deployInfo.addInitParameter("jruby.runtime.init.threads", "1");
		deployInfo.addInitParameter("rackup", getRackupString());
		deployInfo.addInitParameter("app.root", config.getRootPath());
		deployInfo.addInitParameter("jruby.rack.debug.load", "true");
		StringBuilder gemPath = new StringBuilder(config.getRootPath());
		gemPath.append(File.separator);
		gemPath.append("vendor").append(File.separator).append("jruby");

		gemPath.append(File.separator).append("1.9"); // TODO needs to come from a config
		gemPath.append(File.separator).append("gems"); // TODO need to figure out based on cache vs bundle
		
		File gemPathDir = new File(gemPath.toString());
		if (gemPathDir.exists()) {
			gemPath.append(File.pathSeparator);
		} else {
			gemPath = new StringBuilder();
		}
		deployInfo.addInitParameter("gem.path", gemPath.toString());
		deployInfo.addInitParameter("jruby.rack.layout_class", "RailsFilesystemLayout");
		deployInfo.addInitParameter("jruby.rack.logging.name", "jul");
	}

	protected String getRackupString() {
		try {
			File file = new File(config.getRootPath() + File.separator + config.getAppFile());
			FileInputStream fis = new FileInputStream(file);
			String script = IOHelpers.inputStreamToString(fis);

			StringBuilder withHeader = new StringBuilder();
			withHeader.append("$:.unshift('").append(config.getRootPath()).append("/app')\n");
			String libDir = System.getProperty("STOKESDRIFT_LIB_DIR");
			if (libDir != null) {

			}
			withHeader.append(script);
			script = withHeader.toString();
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void initializeConfig(ApplicationConfig config) {
		this.config = config;
	}

}