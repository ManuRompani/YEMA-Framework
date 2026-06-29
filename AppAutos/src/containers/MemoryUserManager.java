package containers;

import java.util.ArrayList;
import java.util.List;

import interfaces.IUserManager;
import model.UserBase;
/*
 * ================================================
 * PERTENECE AL MODELO DE NEGOCIO, HAY QUE MOVERLO - HECHO
 * ================================================
 * 
 */

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
	
	@Override
	public void registerUser(UserBase user) {
		this.listUsers.add(user);
	}


	@Override
	public boolean userExists(String user) {
		boolean result = false;
		for(UserBase ub :listUsers) {
			if(ub.getName().equals(user)) {
				result = true;
			}
		}
		return result;
	}

}
