package org.stokesdrift.container;

import jakarta.enterprise.inject.spi.*;
import jakarta.inject.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.event.*;

@Singleton
public class ContextListener implements Extension {

	private static final Logger logger = Logger.getLogger(ContextListener.class.getName());
	
	public void observe(@Observes ProcessBean<?> bean) {
		logger.log(Level.INFO, "stokesdrift:bean[" +bean.getAnnotated().getBaseType()+ "]");
	}
}
