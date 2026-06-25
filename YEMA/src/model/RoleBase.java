package model;

public class RoleBase {
	private int Id;
	private String Name;
	
	public RoleBase(int id, String name) {
		super();
		Id = id;
		Name = name;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
