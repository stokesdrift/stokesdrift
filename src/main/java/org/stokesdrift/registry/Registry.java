package org.stokesdrift.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.spi.Bean;

import org.jboss.weld.Container;
import org.jboss.weld.literal.NamedLiteral;
import org.stokesdrift.config.RuntimeType;


// TODO implements javax.naming.Context
// TODO make a configurable registry, bean registry
public class Registry implements Map<String, RegistryObject> {
	
	private Map<String,RegistryObject> registryMap = new ConcurrentHashMap<String,RegistryObject>();

	@Override
	public int size() {
		return registryMap.size();
	}

	@Override
	public boolean isEmpty() {
		return registryMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return registryMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return registryMap.containsValue(value);
	}

	@Override
	public RegistryObject get(Object key) {		
		RegistryObject localReference = registryMap.get(key);
		if(localReference == null) {			
			Container container = Container.instance();
			Iterator<Bean<?>> iterBeans = container.deploymentManager().getBeans().iterator();
			while(iterBeans.hasNext()) {
				Bean<?> bean = iterBeans.next();
				System.out.println(" NAME IS " + bean.getName());
			}
			
			Iterator<Object> iter = container.deploymentManager().instance().iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
			
			try {
			   Object obj = container.deploymentManager().instance().select(new NamedLiteral(key.toString()) ).get();
			   localReference = new RegistryObject(RuntimeType.CDI, obj);
			   put(key.toString(), localReference);			   
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}
		return localReference;
	}

	@Override
	public RegistryObject put(String key, RegistryObject value) {
		return registryMap.put(key, value);
	}

	@Override
	public RegistryObject remove(Object key) {
		return registryMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends RegistryObject> m) {
		registryMap.putAll(m);		
	}

	@Override
	public void clear() {
		registryMap.clear();		
	}

	@Override
	public Set<String> keySet() {
		return registryMap.keySet();
	}

	@Override
	public Collection<RegistryObject> values() {
		return registryMap.values();
	}

	@Override
	public Set<Map.Entry<String, RegistryObject>> entrySet() {
		return registryMap.entrySet();
	}
	
}
