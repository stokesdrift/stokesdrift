package org.stokesdrift.container;

import io.undertow.servlet.api.DeploymentInfo;

import org.stokesdrift.config.ApplicationConfig;
/**
 * Contract of what applications need to provide to start up an application
 * 
 * @author driedtoast
 *
 */
public interface Application {

	/**
	 * Get the deployment information for the type of app based on the server configuration

	 * @return deploymentInfo Deployment information for setting up undertow
	 */
	DeploymentInfo getDeploymentInfo();
	
	
	void initializeConfig(ApplicationConfig config);
	
}
