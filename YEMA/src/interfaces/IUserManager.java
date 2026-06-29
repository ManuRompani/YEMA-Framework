package interfaces;
import java.util.List;
import model.UserBase;

public interface IUserManager {
	
	UserBase authenticate(String username, String password);	
	UserBase getUser(String username);

}
