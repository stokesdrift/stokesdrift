package org.stokesdrift.registry;

import org.stokesdrift.config.RuntimeType;

/**
 * Wraps an object in the registry to allow for CDI integration
 * 
 * @author driedtoast
 *
 */
public class RegistryObject {
	
	private RuntimeType runtimeType;
	private Object object;
	
	public RegistryObject(RuntimeType type, Object object) 
	{
		this.runtimeType = type;
		this.object = object;
	}

	public RuntimeType getRuntimeType() {
		return runtimeType;
	}

	public Object getObject() {
		return object;
	}
	
	
}
