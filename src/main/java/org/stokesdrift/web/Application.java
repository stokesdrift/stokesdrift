package org.stokesdrift.web;

import org.stokesdrift.config.ServerConfig;

import io.undertow.servlet.api.DeploymentInfo;
/**
 * Contract of what applications need to provide to start up an application
 * 
 * @author driedtoast
 *
 */
public interface Application {

	/**
	 * Get the deployment information for the type of app based on the server configuration
	 * 
	 * @param config
	 * @return
	 */
	DeploymentInfo getDeploymentInfo();
	
	
	void initializeConfig(ServerConfig config);
	
}
