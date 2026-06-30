package model;

/* ============================
 * Modelo base de un usuario
 * del sistema. 
 * =========================== */
 
public class UserBase {
	private int id;
	private String name;
	private String pass;		
	private RoleBase role;
	
	
	public UserBase(int id, String name, String pass, RoleBase role) {
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
