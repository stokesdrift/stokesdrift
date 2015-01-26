package org.stokesdrift;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.stokesdrift.config.ApplicationConfig;
import org.stokesdrift.config.Options;
import org.stokesdrift.config.ServerConfig;
import org.stokesdrift.container.Application;
import org.stokesdrift.container.ApplicationBuilder;
import org.stokesdrift.container.ApplicationBuilderFactory;
import org.stokesdrift.web.listeners.CdiServletRequestListener;

/**
 * Main starting point of loading the container and populating the registry for
 * the micro services to interact with each other
 *
 * @author driedtoast
 *
 */
public class Server {

	private static final Logger logger = Logger.getLogger(Server.class.getName());

	private ServerConfig config;
	private List<Application> applications;
	private Undertow server;
	private Weld weld;
	private WeldContainer container;

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
		initialize(args);
	}

	public void initialize(String[] args) {
		Options options = new Options(args);
		config = createConfig(options);
	}

	public ServerConfig createConfig(Options options) {
		logger.log(Level.INFO, "stokesdrift:server:load_configuration[status=in_progress]");
		System.setProperty("org.jboss.weld.se.archive.isolation", "false");
		ServerConfig serverConfig = new ServerConfig(options);
		try {
			serverConfig.load();
			logger.log(Level.INFO, "stokesdrift:server:load_configuration[status=complete, root="+serverConfig.getRootPath() + "]");
		} catch(Throwable t) {
			logger.log(Level.SEVERE, "stokesdrift:server:load_configuration[status=failed]", t);
			serverConfig = null;
		}
		return serverConfig;
	}

	/**
	 * Loads up the application configuration for an application
	 *
	 * @param config
	 * @return list of applications
	 */
	public List<Application> loadApplicationDefinitions(ServerConfig config) {
		logger.log(Level.INFO, "stokesdrift:server:load_app_definitions[status=in_progress]");
		ApplicationBuilderFactory factory = container.instance().select(ApplicationBuilderFactory.class).get();
		List<Application> apps = new ArrayList<Application>();
		List<ApplicationConfig> appConfigs = config.getApplicationConfigs();
		for (ApplicationConfig appConfig : appConfigs) {
			ApplicationBuilder appBuilder = factory.getBuilder(appConfig.getType());
			Application app = appBuilder.addConfig(appConfig).build();
			// TODO add some debugging if app isn't able to be setup
			if (app != null) {
				logger.log(Level.INFO, "stokesdrift:server:loaded_app_definition[app="+appConfig.getName()+", type="+ appConfig.getType() +"]");
				apps.add(app);
			}
		}
		logger.log(Level.INFO, "stokesdrift:server:load_app_definitions[status=complete]");
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
		for (Application app : apps) {
			DeploymentInfo deployInfo = app.getDeploymentInfo();

			// Add default listeners
			ListenerInfo cdiListener = Servlets.listener(CdiServletRequestListener.class);
			deployInfo.addListener(cdiListener);

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

		logger.log(Level.INFO, "stokesdrift:server:load_cdi_container[status=in_progress]");
		weld = new Weld();
		container = weld.initialize();
		logger.log(Level.INFO, "stokesdrift:server:load_cdi_container[status=complete]");

		applications = loadApplicationDefinitions(config);

		Undertow.Builder builder = Undertow.builder().addHttpListener(config.getPort(), config.getHost());
		List<DeploymentManager> managers = deployApplications(applications);
		for (DeploymentManager deploymentManager : managers) {
			builder.setHandler(deploymentManager.start());
		}
		server = builder.build();
		server.start();
		logger.log(Level.INFO, "stokesdrift:server:http[port="+config.getPort()+",host="+config.getHost()+"]");
		logger.log(Level.INFO, "stokesdrift:server:start[status=complete]");
	}

	public void stop() {
		logger.log(Level.WARNING, "stokesdrift:server:stop[status=in_progress]");
		if (server != null) {
			server.stop();
		}

		if (weld != null) weld.shutdown();
		logger.log(Level.INFO, "stokesdrift:server:stop[status=complete]");
	}

}
