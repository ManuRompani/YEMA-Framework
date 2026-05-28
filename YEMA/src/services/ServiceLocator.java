package services;

import java.util.HashMap;

import exceptions.ServiceNotImplementedException;

public class ServiceLocator<T> {
	private HashMap<String, T> hServices;
	
	public ServiceLocator() {
		this.hServices = new HashMap<String, T>();
	}
	
	public T getService(String key) throws ServiceNotImplementedException {
		T service = this.hServices.get(key);
		
		if(service == null) {
			throw new ServiceNotImplementedException();
		}
		
		return service;
	}
	
	public void registerService(String key, T service) {
		this.hServices.put(key, service);
	}
}
