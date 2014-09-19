package org.stokesdrift.registry;

import org.junit.Assert;
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
}
