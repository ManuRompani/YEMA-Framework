package model;

public class UserBase {
	private String pass;
	private int id;
	private String name;
	private RoleBase role;
	
	
	public UserBase(String pass, int id, String name, RoleBase role) {
		super();
		this.pass = pass;
		this.id = id;
		this.name = name;
		this.role = role;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoleBase getRole() {
		return role;
	}

	public void setRole(RoleBase role) {
		this.role = role;
	}

	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
