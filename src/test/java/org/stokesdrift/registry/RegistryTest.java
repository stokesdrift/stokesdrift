package org.stokesdrift.registry;

import java.util.Hashtable;

import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.jglue.cdiunit.internal.CdiUnitContext;
import org.stokesdrift.config.RuntimeType;

public class RegistryTest extends TestCase {

	public void testRegistrySetup() {
		Registry registry = new Registry();

		Object rubyObject = null;
		RegistryObject regObject = new RegistryObject(RuntimeType.RUBY,rubyObject);

		registry.put("myObject", regObject);

		assertNotNull(registry.get("myObject"));
		assertEquals(RuntimeType.RUBY, registry.get("myObject").getRuntimeType());
		
		
		
	}
	
	public void testCdi() {
		CDIProvider provider = null;
		CDI<Object> cdi = provider.getCDI();
		// cdi.select(arg0)
		
		CdiUnitContext context = null;
		
	}
	
	static class TestContext implements  javax.naming.Context {

		@Override
		public Object lookup(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object lookup(String name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void bind(Name name, Object obj) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void bind(String name, Object obj) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rebind(Name name, Object obj) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rebind(String name, Object obj) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unbind(Name name) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unbind(String name) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rename(Name oldName, Name newName) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rename(String oldName, String newName)
				throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public NamingEnumeration<NameClassPair> list(Name name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NamingEnumeration<NameClassPair> list(String name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NamingEnumeration<Binding> listBindings(Name name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NamingEnumeration<Binding> listBindings(String name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void destroySubcontext(Name name) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void destroySubcontext(String name) throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Context createSubcontext(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Context createSubcontext(String name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object lookupLink(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object lookupLink(String name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NameParser getNameParser(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NameParser getNameParser(String name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Name composeName(Name name, Name prefix) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String composeName(String name, String prefix)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object addToEnvironment(String propName, Object propVal)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object removeFromEnvironment(String propName)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Hashtable<?, ?> getEnvironment() throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() throws NamingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getNameInNamespace() throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
