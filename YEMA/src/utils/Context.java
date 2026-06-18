package utils;

import utils.SessionData;

public class Context {
	private SessionData sessionData;
	
	public Context(SessionData sessionData) {
		this.sessionData = sessionData;
	}
	
	public SessionData getSessionData() {
		return sessionData;
	}
	
}
