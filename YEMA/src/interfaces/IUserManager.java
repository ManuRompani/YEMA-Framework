package interfaces;
import java.util.List;
import model.UserBase;

public interface IUserManager {
	
	UserBase authenticate(String username, String password);//Es mejor pasarle un objeto con las credenciales	
	UserBase getUser(String username);
	public void registerUser(UserBase user);	
	public boolean userExists(String user);

}
