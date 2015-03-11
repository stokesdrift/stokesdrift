package org.stokesdrift.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.spi.Bean;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.literal.InitializedLiteral;
import org.jboss.weld.literal.NamedLiteral;
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
		ApplicationBuilderFactory builder = container.instance().select(ApplicationBuilderFactory.class).get();
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
		
		ApplicationBuilder appBuilder = container.instance().select(ApplicationBuilder.class).get();
		Assert.assertNotNull(appBuilder);
		
	}
	
	@Test
	public void testWeld() throws Exception {
		container.event().select(ApplicationBuilder.class, InitializedLiteral.APPLICATION).fire(new RackApplicationBuilder(null));
		
		ApplicationBuilder builder = container.instance().select(ApplicationBuilder.class).get();
		Assert.assertNotNull(builder);

		Assert.assertNotNull(container.instance().select(new NamedLiteral("rack_builder") ).get());
		
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
