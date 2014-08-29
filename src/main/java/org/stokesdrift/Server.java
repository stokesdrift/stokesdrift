package org.stokesdrift;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main starting point of loading the container and populating the registry for the micro services
 * to interact with each other
 * @author dmarchant
 *
 */
public class Server {
	
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	
	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.start();
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "stokesdrift:server:start[status=failed]", t);
			server.stop();
		}
	}
	
	
	public void start() {
	   logger.log(Level.INFO, "stokesdrift:server:start[status=in_progress]");
	}

	public void stop() {
	   logger.log(Level.WARNING, "stokesdrift:server:stop[status=in_progress]");
	}
		
}
