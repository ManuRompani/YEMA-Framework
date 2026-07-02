package utils;

import model.UserBase;
import services.ServiceLocator;
import utils.SessionData;

public class Context {
	private UserBase user;
	private SessionData sessionData;
	private ServiceLocator sl;
	

	public Context(ServiceLocator sl) {		
		this.sl = sl;
	}
	
	public SessionData getSessionData() {
		return sessionData;
	}
	
	public ServiceLocator getServiceLocator() {
		return sl;
	}
	
	
	public UserBase getUser() {
		return user;
	}

	public void setUser(UserBase user) {
		this.user = user;
		if (this.sessionData == null && user != null) {
	        this.sessionData = new SessionData(user);
	    } else if (this.sessionData != null && user != null) {
	        this.sessionData = new SessionData(user);  // recrear con el nuevo usuario
	    }
	}
}
