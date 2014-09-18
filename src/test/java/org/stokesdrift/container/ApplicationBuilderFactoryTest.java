package org.stokesdrift.container;

import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.spi.Bean;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.literal.InitializedLiteral;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.stokesdrift.container.jruby.RackApplicationBuilder;

public class ApplicationBuilderFactoryTest {

	private WeldContainer container;
	private Weld weld;
	private Context context;
	
	@Before
	public void setup() {
		 weld = new Weld();
		 container = weld.initialize();
		 context = container.getBeanManager().getContext(ApplicationScoped.class);		 
		 Assert.assertTrue(context.isActive());
	}
	
	
	@Test
	public void testRackApplicationBuilder() throws Exception {
		ApplicationBuilderFactory factory = container.instance().select(ApplicationBuilderFactory.class).get();
		ApplicationBuilder rackBuilder = factory.getBuilder("rack");
		Assert.assertNotNull(rackBuilder);
	}
	
	@Test
	public void testContext() {
		Assert.assertTrue(context.isActive());

		Set<Bean<?>> beans  = container.getBeanManager().getBeans("rack_builder");
		Iterator<Bean<?>> iter = beans.iterator();
		while(iter.hasNext()) {
			Bean<?> bean = iter.next();
			System.out.println("Bean name " + bean.getName());
		}
		ApplicationBuilderFactory builder = container.instance().select(ApplicationBuilderFactory.class).get();
		Assert.assertNotNull(builder);
		
		Assert.assertNotNull(builder.builder);

		Assert.assertFalse(builder.builder.isUnsatisfied());
		Iterator<ApplicationBuilder> builders = builder.builder.iterator();
		while(builders.hasNext()) {
			ApplicationBuilder builder_temp = builders.next();
			System.out.println(builder_temp);
			
		}
		
		ApplicationBuilder appBuilder = container.instance().select(ApplicationBuilder.class).get();
		Assert.assertNotNull(appBuilder);
		
	}
	
	@Test
	public void testWeld() throws Exception {
		container.event().select(ApplicationBuilder.class, InitializedLiteral.APPLICATION).fire(new RackApplicationBuilder(null));
		
		ApplicationBuilder builder = container.instance().select(ApplicationBuilder.class).get();
		System.out.println(builder);
		Set<Bean<?>> builders = container.getBeanManager().getBeans("rack_builder");
		System.out.println(builders);
		Iterator<Bean<?>> iter = builders.iterator();
		while(iter.hasNext()) {
			Bean<?> bean = iter.next();
			System.out.println(bean.getClass());
		}
	}
	
	
	@After
	public void teardown() {
		weld.shutdown();
	}
}
