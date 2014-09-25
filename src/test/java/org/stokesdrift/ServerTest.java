package org.stokesdrift;

import java.io.File;
import java.net.URL;

import org.junit.Test;


public class ServerTest {

	boolean failed = false;
	
	@Test
	public void testStartup() throws Exception {
		URL configRuFile = this.getClass().getClassLoader().getResource("examples/config.ru");
		File file = new File(configRuFile.toURI());
		String directoryName = file.getParent();
		String[] args = new String[] { "-r", directoryName };
		final Server server = new Server(args);
		
		Thread t = new Thread(new Runnable() {
			 @Override
			public void run() {
			  try {
				  server.start();
			  } catch (Exception e) {
				  e.printStackTrace();
				  failed = true;
			  }
			}
		});
		t.start();
		
		Thread.sleep(8000);
		server.stop();
		
		
    }
	
}
