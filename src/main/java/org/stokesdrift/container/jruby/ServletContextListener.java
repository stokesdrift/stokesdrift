package org.stokesdrift.container.jruby;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jruby.rack.PoolingRackApplicationFactory;
import org.jruby.rack.RackApplicationFactory;
import org.jruby.rack.RackConfig;
import org.jruby.rack.RackServletContextListener;
import org.jruby.rack.SerialPoolingRackApplicationFactory;
import org.jruby.rack.SharedRackApplicationFactory;
import org.jruby.rack.servlet.DefaultServletRackContext;
import org.jruby.rack.servlet.ServletRackConfig;
import org.jruby.rack.servlet.ServletRackContext;

public class ServletContextListener extends RackServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		super.contextDestroyed(event);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		ServletRackConfig config = new ServletRackConfig(servletContext);
		RackApplicationFactory factory = newApplicationFactory(config);
		servletContext.setAttribute(RackApplicationFactory.FACTORY, factory);
		ServletRackContext context = new DefaultServletRackContext(config);
		servletContext.setAttribute(RackApplicationFactory.RACK_CONTEXT, context);
		//  factory.getManagedApplications(); Should put in registry for management api
		try {
			// TODO debug factory.getDelegate().init(context)
			factory.init(context);
		} catch (Exception e) {
			handleInitializationException(e, factory, context);
		}
	}

	@Override
	protected RackApplicationFactory newApplicationFactory(RackConfig config) {
		final RackApplicationFactory factory = new DriftRackApplicationFactory();
		final Integer maxRuntimes = config.getMaximumRuntimes();
		// for backwards compatibility when runtime mix/max values not specified
		// we assume a single shared (threadsafe) runtime to be used :
		if (maxRuntimes == null || maxRuntimes.intValue() == 1) {
			return new SharedRackApplicationFactory(factory);
		} else {
			return config.isSerialInitialization() ? new SerialPoolingRackApplicationFactory(factory) : new PoolingRackApplicationFactory(factory);
		}
	}

}
