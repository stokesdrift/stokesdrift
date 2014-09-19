package org.stokesdrift.config;

public class ApplicationConfig {
	private String name;
	private String rootUrlPath = "/";
	private String type = "rack";
	
	
	
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
