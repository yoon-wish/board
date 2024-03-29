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
	
	public User readUserById(String id) {
		int index = findIndexById(id);
		return group.get(index);
	}

	// D
	public void deleteUser(int index) {
		group.remove(index);
	}

	public boolean checkDuplId(String id) {
		for (int i = 0; i < group.size(); i++) {
			if (group.get(i).getId().equals(id)) {
				System.err.println("이미 존재하는 아이디입니다.");
				return true;
			}
		}
		return false;
	}
	
	public int findIndexById(String id) {
		int index = -1;
		for(int i=0; i<group.size(); i++ ) {
			User user = group.get(i);
			if(user.getId().equals(id))
				index = i;
		}
		return index;
	}

}
