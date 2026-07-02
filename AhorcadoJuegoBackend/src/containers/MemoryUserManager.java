package containers;
import java.util.ArrayList;
import java.util.List;
import dtos.Credentials;
import interfaces.IUserManager;
import model.UserBase;
import models.Player;


public class MemoryUserManager implements IUserManager {
	// se utiliza para asignar id al usuario sin repetir
	private int nextUserId = 1;
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
	public UserBase registerUser(UserBase user) {
		Player player = new Player(
				this.nextUserId, 
				user.getName(),
				user.getPass(),
				user.getRole(),
				0,
				0
				);
		this.listUsers.add(player);
		this.nextUserId ++;
		
		return player;
	}
	
	public UserBase registerAdmin(UserBase user) {
		user.setId(nextUserId);
		
		this.listUsers.add(user);
		
		this.nextUserId ++;
		
		return user;
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
