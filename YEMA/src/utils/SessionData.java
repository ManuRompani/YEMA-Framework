package utils;

import java.util.HashMap;

import model.UserBase;

public class SessionData {
	private HashMap<String, Object> attributes;	 
	
	public SessionData(UserBase user) {
		this.attributes = new HashMap<>();
		this.attributes.put("userName", user.getName());
	}

	//Es un atajo, pero se puede usar tranquilamente el getAttribute
	public String getUserName() {
		return (String) this.attributes.get("userName");
	}
	
	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}

}
