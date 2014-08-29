package org.stokesdrift.registry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


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
		return registryMap.get(key);
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
