package board;

import java.util.ArrayList;

public class UserManager {
	
	ArrayList<User> group = new ArrayList<>();
	
	// CRUD
	// C
	public void creatUser(String id, String pw) {
		User user = new User(id, pw);
		group.add(user);
	}
	
	// R
	public User readUser(int index) {
		return group.get(index); 
	}
	
	// D
	public void deleteUser(int index) {
		group.remove(index);
	}
	
	
}
