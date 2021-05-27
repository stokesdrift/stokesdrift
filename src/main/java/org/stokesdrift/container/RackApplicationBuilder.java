package org.stokesdrift.container;

import jakarta.enterprise.inject.Typed;
import jakarta.inject.Named;
import jakarta.enterprise.context.ApplicationScoped;

import org.stokesdrift.config.ApplicationConfig;
import org.stokesdrift.container.jruby.RackApplication;


@Named("rack_builder")
@ApplicationScoped
@Typed(ApplicationBuilder.class)
public class RackApplicationBuilder extends BaseApplicationBuilder implements ApplicationBuilder {
	
	public RackApplicationBuilder() {
		
	}
	
	public RackApplicationBuilder(ApplicationConfig config) {
		super(config);
	}

	@Override
	public ApplicationBuilder createBuilder(ApplicationConfig config) {
		return new RackApplicationBuilder(config);
	}

	@Override
	public Class<? extends Application> getApplicationClass() {
		return RackApplication.class;
	}
	
	
}
