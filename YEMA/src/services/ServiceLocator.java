package services;

import java.util.HashMap;

import exceptions.ServiceNotImplementedException;

public class ServiceLocator {
	private HashMap<Class<?>, Object> dict;
	
	public ServiceLocator() {
		this.dict = new HashMap<Class<?>, Object>();
	}
	
	public <T> T getService(Class<T> clazz) throws ServiceNotImplementedException {
		T service = (T) dict.get(clazz);
		
		if(service == null) {
			throw new ServiceNotImplementedException();
		}
		
		return service;
	}
	
	public void registerService(Object newObject) {
		Class<?> clazz = newObject.getClass();
		this.dict.put(clazz, newObject);
	}
}
