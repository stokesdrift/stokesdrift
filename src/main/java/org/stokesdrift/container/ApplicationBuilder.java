package org.stokesdrift.container;

import org.stokesdrift.config.ApplicationConfig;

public interface ApplicationBuilder {

	ApplicationBuilder addConfig(ApplicationConfig config);
	
	Application build();
	
}
