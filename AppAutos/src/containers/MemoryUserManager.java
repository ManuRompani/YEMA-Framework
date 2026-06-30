package containers;

import java.util.ArrayList;
import java.util.List;

import dtos.Command;
import dtos.Credentials;
import dtos.Response;
import framework_controllers.BaseController;
import interfaces.IUserManager;
import model.UserBase;
/*
 * ================================================
 * PERTENECE AL MODELO DE NEGOCIO, HAY QUE MOVERLO - HECHO
 * ================================================
 * 
 */
import services.ServiceLocator;
import utils.Context;

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
	
	public ArrayList<UserBase> getUsersList(){
		return new ArrayList<UserBase>(this.listUsers);
	}
	
	@Override
	public UserBase authenticate(Credentials creds) {
		for (UserBase ub : listUsers) {
			if (ub.getName().equals(creds.getUsername()) && ub.getPass().equals(creds.getPassword())) {
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
