package org.stokesdrift.web;

import javax.servlet.DispatcherType;

import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ListenerInfo;

import org.jruby.rack.RackFilter;
import org.jruby.rack.RackServletContextListener;
import org.stokesdrift.config.ServerConfig;

/**
 * Sets up the configuration for a rack application 
 * 
 * Jruby-Rack configs:
 * <ul>
 * <li>rackup: Rackup script for configuring how the Rack application is mounted. Required for Rack-based applications other than Rails. Can be omitted if a config.ru is included in the application root.</li>
 * <li>public.root: Relative path to the location of your application's static assets. Defaults to /.</li>
 * <li>gem.path: Relative path to the bundled gem repository. Defaults to /WEB-INF/gems.</li>
 * <li>jruby.compat.version: Set to "1.8" or "1.9" to make JRuby run a specific version of Ruby (same as the --1.8 / --1.9 command line flags).</li>
 * <li>jruby.min.runtimes: For non-threadsafe Rails applications using a runtime pool, specify an integer minimum number of runtimes to hold in the pool.</li>
 * <li>jruby.max.runtimes: For non-threadsafe Rails applications, an integer maximum number of runtimes to keep in the pool.</li>
 * <li>jruby.runtime.init.threads: How many threads to use for initializing application runtimes when pooling is used (default is 4). It does not make sense to set this value higher than</li>
 * </ul>
 * 
 * @author driedtoast
 *
 */
public class RackApplication implements Application {

	private static final String RACK_FILTER = "RackFilter";

	private ServerConfig config;
	
	@Override
	public DeploymentInfo getDeploymentInfo() {
		// TODO app configuration 
		FilterInfo filter = Servlets.filter(RACK_FILTER, RackFilter.class);
		ListenerInfo listenerInfo = Servlets.listener(RackServletContextListener.class);
		DeploymentInfo di = new DeploymentInfo()
				.addListener(listenerInfo)
		        .setContextPath("/")
		        .addFilter(filter)
		        .addFilterUrlMapping(RACK_FILTER, "/*", DispatcherType.ASYNC)
		        .setDeploymentName(config.getAppName())
		        .setClassLoader(ClassLoader.getSystemClassLoader());
		setupInitParams(di);
		return di;
	}
	
	protected void setupInitParams(DeploymentInfo deployInfo) {
		// TODO add jruby-rack context params
        
		// .addInitParameter(name, value)
		
	}

	@Override
	public void initializeConfig(ServerConfig config) {
		this.config = config;
	}
	
	
	
	

}
