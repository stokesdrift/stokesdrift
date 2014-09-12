package org.stokesdrift.config;

import java.util.List;

public class ServerConfig {

	private String rootPath;
	private int port = 8888;
	private String host = "0.0.0.0";
	private List<ApplicationConfig> applicationConfigs;

	public List<ApplicationConfig> getApplicationConfigs() {
		return applicationConfigs;
	}

	public void setApplicationConfigs(List<ApplicationConfig> applicationConfigs) {
		this.applicationConfigs = applicationConfigs;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

}
