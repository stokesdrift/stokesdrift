package org.stokesdrift.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ServerConfig {
	

	private String rootPath;
	private int port = 8888;
	private String host = "0.0.0.0";
	private List<ApplicationConfig> applicationConfigs;

	private Options options;
	 
	public ServerConfig(Options options) {
		this.options = options;
	}
	
	// 
	@SuppressWarnings("rawtypes")
	public void load() throws Exception {
		String rootPath = options.getValue(Options.Key.ROOT_PATH);
		String configFile = options.getValue(Options.Key.CONFIG_FILE);
		StringBuilder fileName = new StringBuilder(rootPath).append(File.separator).append(configFile);
		File file = new File(fileName.toString());
		FileInputStream fis = new FileInputStream(file);
		
		
		Yaml yaml = new Yaml();
		Map yamlResults = yaml.loadAs(fis, Map.class);
		
		Map serverConfig = (Map)yamlResults.get("server");
		host = serverConfig.get("host").toString();
		port = Integer.parseInt(serverConfig.get("port").toString());
		
		List apps = (List)yamlResults.get("apps");
		applicationConfigs = new ArrayList<ApplicationConfig>();
		for(Object app: apps) {
			Map appYaml = (Map)app;
			ApplicationConfig appConfig = new ApplicationConfig();
			appConfig.setName(appYaml.get("name").toString());
			appConfig.setRootUrlPath(appYaml.get("url_path").toString());
			appConfig.setAppFile(appYaml.get("app_file").toString());
			applicationConfigs.add(appConfig);
		}
		
	}
	
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
