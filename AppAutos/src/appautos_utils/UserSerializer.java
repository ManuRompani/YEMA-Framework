package appautos_utils;

import model.UserBase;
import services.Serializer;

public class UserSerializer implements Serializer<UserBase>{

	@Override
	public String serialize(UserBase user) {
		String serialized = "ID | Username | Rol \n";
		serialized += user.getId() + " | " + user.getName() + " | " + user.getRole();
		return serialized;
	}

}
