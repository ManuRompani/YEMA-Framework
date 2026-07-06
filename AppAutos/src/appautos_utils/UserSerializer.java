package appautos_utils;

import model.UserBase;
import services.Serializer;

public class UserSerializer implements Serializer{

	@Override
	public String serialize(Object user) {
		String serialized = "ID | Username | Rol \n";
		serialized += ((UserBase) user).getId() + " | " + ((UserBase) user).getName() + " | " + ((UserBase) user).getRole();
		return serialized;
	}

}
