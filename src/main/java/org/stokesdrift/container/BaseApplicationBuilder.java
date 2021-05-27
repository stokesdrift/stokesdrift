package org.stokesdrift.container;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.stokesdrift.config.ApplicationConfig;

public abstract class BaseApplicationBuilder implements ApplicationBuilder {
	private static final Logger logger = Logger.getLogger(BaseApplicationBuilder.class.getName());
	
	private ApplicationConfig config;
	
	public BaseApplicationBuilder() {
		
	}
	
	public BaseApplicationBuilder(ApplicationConfig config) {
		this.config = config;
	}
	
	@Override
	public ApplicationBuilder addConfig(ApplicationConfig config) {
		return createBuilder(config);
	}

	@Override
	public Application build() { 
		Application application = null;
		try {
			application = getApplicationClass().getDeclaredConstructor().newInstance();
		} catch (IllegalArgumentException | InstantiationException | SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			logger.log(Level.SEVERE, "stokesdrift:application_builder[status=failed]", e);
		} 
		if (application != null) {
			application.initializeConfig(config);
		}
		return application;
	}
	
	public abstract ApplicationBuilder createBuilder(ApplicationConfig config);
	public abstract Class<? extends Application> getApplicationClass();
}
