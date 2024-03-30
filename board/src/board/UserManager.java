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
	
	public void createAdmin(Admin admin) {
		group.add(admin);
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
	public int deleteUser(int log, String pw) {
		User user = readUser(log);
		if(user.getPw().equals(pw)) {
			group.remove(log);
			System.out.println("탈퇴완료");
			return log;
		}
		System.err.println("비밀번호를 틀렸습니다.");
		return -1;
	}

	public boolean checkDuplId(String id) {
		for (int i = 0; i < group.size(); i++) {
			if (group.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkDuplIdAndPw(String id, String pw) {
		int index = findIndexById(id);
		if(!group.get(index).getPw().equals(pw)) {
			System.err.println("회원 정보를 다시 확인하세요.");
			return false;
		}
		return true;
	}

	public int findIndexById(String id) {
		int index = -1;
		for(int i=0; i<group.size(); i++) {
			User user = group.get(i);
			if(user.getId().equals(id)) 
				index = i;
		}
		return index;
	}
	
	
	
}
