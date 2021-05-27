package org.stokesdrift;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ServerTest {

	boolean failed = false;
	
	Server server;
	
	@Before
	public void before() throws Exception {
		URL configRuFile = this.getClass().getClassLoader().getResource("examples/config.ru");
		File file = new File(configRuFile.toURI());
		String directoryName = file.getParent();
		String[] args = new String[] { "-r", directoryName };
		server = new Server(args);		
	}
	
	@Test
	public void testStartup() throws Exception {
		
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
		
		Thread.sleep(4000);
		
		URL serverUrl = new URL("http://localhost:8888/");
        BufferedReader in = new BufferedReader(new InputStreamReader(serverUrl.openStream()));
        
        StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        	sb.append(inputLine);
        in.close();
     	Assert.assertEquals("hello world",sb.toString());		
    }
	
	@After
	public void after() throws Exception {
		if (server != null) server.stop();
	}
	
}
