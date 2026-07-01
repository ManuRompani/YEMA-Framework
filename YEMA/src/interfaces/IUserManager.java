package interfaces;
import java.util.List;

import annotations.AuthorizedRoles;
import dtos.Credentials;
import model.UserBase;

public interface IUserManager {
	
	UserBase authenticate(Credentials creds);//Es mejor pasarle un objeto con las credenciales	
	UserBase getUser(String username);
	public void registerUser(UserBase user);	
	public boolean userExists(String user);
	
}
