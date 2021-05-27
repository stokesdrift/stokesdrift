package org.stokesdrift.container;

import org.junit.Assert;
import org.junit.Test;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.Set;

import jakarta.enterprise.inject.Instance;
//import jakarta.enterprise.inject.spi.Instance;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.CDI;

public class StandardContainerTest {
	
	@Test
	public void testContainer() throws Exception {
		Weld weld = new Weld();
		 WeldContainer container = weld.initialize();
		 ApplicationBuilderFactory builder = container.select(ApplicationBuilderFactory.class).get();
		 Assert.assertNotNull(builder);
		
		 Assert.assertNotNull(builder.builder);
		 ApplicationBuilder rackBuilder = builder.getBuilder("rack");
		 Assert.assertNotNull(rackBuilder);
		 
		 container.close();
	}
	
	// Debug test for cdi implementation changes
	@Test
	public void testStandardContainer() throws Exception {
		Weld weld = new Weld();
		 WeldContainer container = weld.initialize();
		CDI<?> cdi = CDI.current();
		BeanManager beanManager = cdi.getBeanManager();
		Set<Bean<?>> beans = beanManager.getBeans(ApplicationBuilderFactory.class);
		Assert.assertNotNull(beans);
		for(Bean<?> bean:  beans) {
			System.out.println("bean " + bean.getBeanClass());
		}
		Instance<ApplicationBuilderFactory> factory = container.select(ApplicationBuilderFactory.class);
		Assert.assertTrue(factory.isResolvable());
		ApplicationBuilderFactory appFactory = factory.get();
		Assert.assertNotNull(appFactory);
		Assert.assertNotNull(appFactory.builder);
		appFactory.builder.stream().map(e -> e.getClass()).forEach(System.out::println);
		ApplicationBuilder rackBuilder = appFactory.getBuilder("rack");
		Assert.assertNotNull(rackBuilder);
		
	}

}
