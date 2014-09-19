package org.stokesdrift.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yecht.IoFileRead;
import org.yecht.Parser;

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
	public void load() throws Exception {
		String rootPath = options.getValue(Options.Key.ROOT_PATH);
		String configFile = options.getValue(Options.Key.CONFIG_FILE);
		StringBuilder fileName = new StringBuilder(rootPath).append(File.separator).append(configFile);
		File file = new File(fileName.toString());
		FileInputStream fis = new FileInputStream(file);
		
		
		Yaml yaml = new Yaml();
		Map yamlResults = yaml.loadAs(fis, Map.class);
		System.out.println(yamlResults);
		host = yamlResults.get("host").toString();
		
		
//		Parser parser = org.yecht.Parser.newParser();
//		parser.file(fis, new IoFileRead.Default());
//		Object parsedObject = parser.parse();
//		System.out.println(parsedObject);
//		
		
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
