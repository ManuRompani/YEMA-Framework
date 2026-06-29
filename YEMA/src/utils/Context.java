package utils;

import services.ServiceLocator;
import utils.SessionData;

public class Context {
	private SessionData sessionData;
	private ServiceLocator sl;
	
	public Context(SessionData sessionData) {
		this.sessionData = sessionData;
	}
	
	public SessionData getSessionData() {
		return sessionData;
	}
	
}
