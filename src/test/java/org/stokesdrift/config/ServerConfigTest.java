package org.stokesdrift.config;

import java.io.File;
import java.net.URL;

import org.junit.Assert;

import org.junit.Test;

public class ServerConfigTest {

	@Test
	public void testConfigParsing() throws Exception {
		URL configRuFile = this.getClass().getClassLoader().getResource("examples/config.ru");
		File file = new File(configRuFile.toURI());
		String directoryName = file.getParent();
		
		String[] args = new String[]{ "-r", directoryName};
		Options options = new Options(args);
		ServerConfig config = new ServerConfig(options);
		
		config.load();
		
		Assert.assertNotNull(config.getApplicationConfigs());
		
		
	}
	
}
