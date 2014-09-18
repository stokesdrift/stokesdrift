package org.stokesdrift.container.jruby;

import javax.enterprise.inject.Typed;
import javax.inject.Named;

import org.stokesdrift.config.ApplicationConfig;
import org.stokesdrift.container.Application;
import org.stokesdrift.container.ApplicationBuilder;
import org.stokesdrift.container.BaseApplicationBuilder;


@Named("rack_builder")
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
