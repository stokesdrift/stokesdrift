package org.stokesdrift;

import java.io.File;
import java.net.URL;

import org.junit.Test;


public class ServerTest {

	@Test
	public void testStartup() throws Exception {
		URL configRuFile = this.getClass().getClassLoader().getResource("examples/config.ru");
		String configRuPath = configRuFile.getFile();
		File file = new File(configRuPath);
		String directoryName = file.getParent();
		String[] args = new String[] { "-r", directoryName, "-c", file.getName() };
		Server server = new Server(args);
		
    }
	
}
