package board;

public class Admin extends User {

	boolean isAdmin;

	public Admin(String id, String pw) {
		super(id, pw);
		isAdmin = true;
	}

	public boolean getAdmin() {
		return this.isAdmin;
	}

}
