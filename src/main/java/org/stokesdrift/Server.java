package org.stokesdrift;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.stokesdrift.config.ServerConfig;
import org.stokesdrift.web.Application;


/**
 * Main starting point of loading the container and populating the registry for the micro services
 * to interact with each other
 * @author driedtoast
 *
 */
public class Server {
	
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	
	private ServerConfig config;
	private List<Application> applications;
	private Undertow server;
	
	public static void main(String[] args) {
		Server server = new Server(args);
		try {
			server.start();
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "stokesdrift:server:start[status=failed]", t);
			server.stop();
		}
	}
	
	public Server(String[] args) {
		// TODO parse out the options for the config file
		// TODO parse out the config.ru path
			
	}
	
	public void initialize(String[] args) {
		config = createConfig(args);
		applications = loadApplicationConfig(config);
		
	}
	
	
	public ServerConfig createConfig(String[] args) {
		// TODO load configuration from the root paths and directories and such
		ServerConfig serverConfig = new ServerConfig();
		return serverConfig;
	}
	
	/**
	 * Loads up the application configuration for an application
	 * 
	 * @param config
	 * @return list of applications
	 */
	public List<Application> loadApplicationConfig(ServerConfig config) {
		List<Application> apps = new ArrayList<Application>();
		// application.initializeConfig(ServerConfig config)
		// TODO fancy stuff with the configuration that will load particular application configs
		return apps;
	}
	
	/** 
	 * Setup the deployment managers for a given set of applications
	 * 
	 * @param apps
	 * @return list of deployment managers
	 */
	public List<DeploymentManager> deployApplications(List<Application> apps) {
		List<DeploymentManager> deployManagers = new ArrayList<DeploymentManager>();
		for(Application app: apps) {
			DeploymentInfo deployInfo = app.getDeploymentInfo(); 
			DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(deployInfo);
			try {
				deploymentManager.deploy();
				deployManagers.add(deploymentManager);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "stokesdrift:server:start[status=app_deploy_fail,app_type=" + app.getClass().getSimpleName() + "]", e);
			}
		}
		return deployManagers;
	}
 	
	public void start() throws Exception {
	   logger.log(Level.INFO, "stokesdrift:server:start[status=in_progress]");
	   Undertow.Builder builder = Undertow.builder().addHttpListener(config.getPort(), config.getHost());
	   List<DeploymentManager> managers = deployApplications(applications);
	   for(DeploymentManager deploymentManager: managers) {
		   builder.setHandler(deploymentManager.start());
	   }
	   server = builder.build();
	   server.start();
	}

	public void stop() {
	   logger.log(Level.WARNING, "stokesdrift:server:stop[status=in_progress]");
	   if (server != null) {
		   server.stop();
	   }
	}
		
}
