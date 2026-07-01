package containers;
import java.util.ArrayList;
import java.util.List;
import dtos.Credentials;
import interfaces.IUserManager;
import model.UserBase;
import models.Player;


public class MemoryUserManager implements IUserManager {
	private List<Player> listUsers = new ArrayList<Player>();

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
		Player player = new Player(
				user.getId(), 
				user.getName(),
				user.getPass(),
				user.getRole(),
				0,
				0
				);
		this.listUsers.add(player);
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

	public ArrayList<Player> getPlayersList(){
	ArrayList<Player> players = new ArrayList<Player>();
	for(UserBase ub : this.listUsers) {
		players.add((Player)ub);
	}
	
	return players;
	}
	
}
