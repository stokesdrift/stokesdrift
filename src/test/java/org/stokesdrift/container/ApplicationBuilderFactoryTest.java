package org.stokesdrift.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.spi.*;
// import javax.enterprise.context.spi.Context;
// import javax.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.Bean;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.AnnotatedType;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.InjectionTarget;

public class ApplicationBuilderFactoryTest {

	private WeldContainer container;
	private Weld weld;
	
	@Before
	public void setup() {
		 weld = new Weld();
		 weld.enableDiscovery();
		 container = weld.initialize();
		 
		 
         jakarta.enterprise.context.spi.CreationalContext<Object> creationalContext = container.getBeanManager().createCreationalContext(null);

         
	}
	
	
	@Test
	public void testRackApplicationBuilder() throws Exception {
		ApplicationBuilderFactory factory = container.select(ApplicationBuilderFactory.class).get();
		ApplicationBuilder rackBuilder = factory.getBuilder("rack");
		Assert.assertNotNull(rackBuilder);
	}
	
	@Test
	public void testContext() {
		ApplicationBuilderFactory builder = container.select(ApplicationBuilderFactory.class).get();
		Assert.assertNotNull(builder);
	
		Assert.assertNotNull(builder.builder);

		Assert.assertFalse(builder.builder.isUnsatisfied());
		Iterator<ApplicationBuilder> builders = builder.builder.iterator();
		List<ApplicationBuilder> builderList = new ArrayList<ApplicationBuilder>();
		while(builders.hasNext()) {
			ApplicationBuilder builder_temp = builders.next();
			builderList.add(builder_temp);
		}
		Assert.assertTrue(builderList.size() > 0 );
		
		ApplicationBuilder appBuilder = container.select(ApplicationBuilder.class).get();
		Assert.assertNotNull(appBuilder);
		
	}
	
	@Test
	public void testWeld() throws Exception {
		container.event().select(ApplicationBuilder.class, Initialized.Literal.APPLICATION).fire(new RackApplicationBuilder(null));
		
		
		ApplicationBuilder builder = container.select(ApplicationBuilder.class).get();
		Assert.assertNotNull(builder);

		Set<Bean<?>> builders = container.getBeanManager().getBeans("rack_builder");
		Iterator<Bean<?>> iter = builders.iterator();
		List<Class<?>> names = new ArrayList<Class<?>>();
		while(iter.hasNext()) {
			Bean<?> bean = iter.next();
			names.add(bean.getClass());
		}
		Assert.assertTrue(names.size() > 0 );
	}
	
	
	@After
	public void teardown() {
		weld.shutdown();
	}
}
