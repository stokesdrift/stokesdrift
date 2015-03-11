package org.stokesdrift.registry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.Context;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.stokesdrift.config.RuntimeType;

public class RegistryTest {

	@Test
	public void testRegistrySetup() {
		Registry registry = new Registry();

		Object rubyObject = null;
		RegistryObject regObject = new RegistryObject(RuntimeType.RUBY,rubyObject);

		registry.put("myObject", regObject);

		Assert.assertNotNull(registry.get("myObject"));
		Assert.assertEquals(RuntimeType.RUBY, registry.get("myObject").getRuntimeType());
		
	}
	
	@Test
	public void testComponentBackup() {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		Context context = container.getBeanManager().getContext(ApplicationScoped.class);		 
		Assert.assertTrue(context.isActive());
		Registry registry = new Registry();
		Object helloComponent = registry.get("hello_component");
		
		Assert.assertNotNull(helloComponent);
	}
}
