package services;

import java.util.ArrayList;
import java.util.List;

import interfaces.IUserManager;
import model.UserBase;

public class MemoryUserManager implements IUserManager {
	private List<UserBase> listUsers = new ArrayList<UserBase>();

	@Override
	public UserBase getUser(String username) {
		for (UserBase ub : listUsers) {
			if (ub.getName().equals(username)) {
				return ub;
			}
		}
		return null;
	}
	
	@Override
	public UserBase authenticate(String username, String password) {
		for (UserBase ub : listUsers) {
			if (ub.getName().equals(username) && ub.getPass().equals(password)) {
				return ub;
			}
		}
		return null;
	}
	
	public void registerUser(UserBase user) {
		this.listUsers.add(user);
	}

	public void removeUser(int id) {
		this.listUsers.removeIf(u -> u.getId() == id);
	}

}
