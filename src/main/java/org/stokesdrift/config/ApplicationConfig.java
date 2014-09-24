package org.stokesdrift.config;

public class ApplicationConfig {
	private String name;
	private String rootUrlPath = "/";
	
	// TODO type should be based on the configuration file
	private String type = "rack";
	
	private String appFile = "config.ru";
	
	public String getAppFile() {
		return appFile;
	}

	public void setAppFile(String appFile) {
		this.appFile = appFile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRootUrlPath() {
		return rootUrlPath;
	}

	public void setRootUrlPath(String rootUrlPath) {
		this.rootUrlPath = rootUrlPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String appName) {
		this.name = appName;
	}

}
