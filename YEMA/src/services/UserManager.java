package services;

import java.util.List;

import model.UserBase;

public class UserManager {
	private List<UserBase> listUsers = null;
	
	public void registerUser(UserBase user) {
		this.listUsers.add(user);
	}
	
	public void removeUser(int id) {
		this.listUsers.removeIf(u -> u.getId() == id);
	}
	
	/*public UserBase isRegistered(UserBase user) {
		
	}*/
}
