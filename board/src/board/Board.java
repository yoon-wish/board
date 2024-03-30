package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private Scanner sc = new Scanner(System.in);

	private final int PAGE_SIZE = 3;
	
	private final int JOIN = 1;
	private final int LEAVE = 2;
	private final int LOGIN = 3;
	private final int LOGOUT = 4;
	private final int WRITE = 5;
	private final int SEARCH = 6;
	private final int MYPAGE = 7;
	private final int MANAGER = 8;

	private final int CHECK_LOGIN = 1;
	private final int CHECK_LOGOUT = 2;

	private final int NOTICE = 1;
	private final int ALLPOST = 2;

	private int log;
	private Map<User, ArrayList<Post>> board;
	private UserManager userManager;
	private PostManager postManager;
	
	public Board() {
		setBoard();
	}

	private void setBoard() {
		board = new HashMap<>();
		userManager = new UserManager();
		postManager = new PostManager();
		Admin admin = new Admin("admin", "1234");
		userManager.createAdmin(admin);
		board.put(admin, null);
		log = -1;
	}

	private int printMenu() {
		System.out.println("==== Board ====");
		System.out.println("[1] 회원가입");
		System.out.println("[2] 회원탈퇴");
		System.out.println("[3] 로 그 인");
		System.out.println("[4] 로그아웃");
		System.out.println("[5] 작성하기");
		System.out.println("[6] 조회하기");
		System.out.println("[7] 마이페이지");
		System.out.println("[8] 관리자");

		return inputNumber("Menu");
	}

	private int inputNumber(String message) {
		System.out.print(message + " : ");
		int number = -1;
		try {
			String input = sc.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자를 입력하세요.");
		}
		return number;
	}

	private void runMenu(int sel) {
		if (sel == JOIN && isLogin(CHECK_LOGOUT))
			join();
		else if (sel == LEAVE && isLogin(CHECK_LOGIN))
			leave();
		else if (sel == LOGIN && isLogin(CHECK_LOGOUT))
			login();
		else if (sel == LOGOUT && isLogin(CHECK_LOGIN))
			logout();
		else if (sel == WRITE && isLogin(CHECK_LOGIN))
			write();
		else if (sel == SEARCH && isLogin(CHECK_LOGIN))
			runSearch(printSearchSubMenu());
//		else if (sel == MYPAGE && isLogin(CHECK_LOGIN) && log != 0)
//			myPage();	
//		else if (sel == MANAGER && log == 0) 
//			manager();

	}

	private void join() {
		String id = inputString("id");
		if (userManager.checkDuplId(id)) {
			System.err.println("이미 존재하는 아이디입니다.");
			return;
		}

		// 유저 추가
		String pw = inputString("pw");
		userManager.creatUser(id, pw);

		// 추가한 유저를 board에 put
		User user = userManager.readUserById(id);
		board.put(user, null);

		System.out.printf("[%s]님 환영합니다.\n", id);
	}

	private void leave() {
		if (log == 0) {
			System.err.println("관리자 계정은 탈퇴 불가");
			return;
		}

		String pw = inputString("pw");

		User user = userManager.readUser(log);
		int result = userManager.deleteUser(log, pw);
		if (result != -1) {
			board.remove(user);
			log = -1;
		}
	}

	private void login() {
		String id = inputString("id");
		String pw = inputString("pw");

		int index = userManager.findIndexById(id);
		if (index == -1) {
			System.err.println("존재하지 않는 계정입니다.");
			return;
		}

		if (userManager.checkDuplIdAndPw(id, pw)) {
			log = index;
			System.out.println("로그인 성공");
		}
	}

	private void logout() {
		log = -1;
		System.out.println("로그아웃 완료");
	}

	private void write() {
		User user = userManager.readUser(log);
		String title = postManager.writeTitle();
		if (title.equals("")) {
			return;
		}
		String content = postManager.writeContent();
		
		Post post = null;
		if (log != 0) {
			String code = postManager.writeCode();
			String id = user.getId();
			post = postManager.creatPost(id, title, content, code);
		} else {
			post = postManager.createNoticePost(title, content);
		}
		
		ArrayList<Post> userPosts = board.get(user);
		if(userPosts == null) {
			userPosts = new ArrayList<>();
		}
		
		userPosts.add(post);
		board.put(user, userPosts);

	}

	private int printSearchSubMenu() {
		System.out.println("1) 공지사항");
		System.out.println("2) 전 체 글");
		return inputNumber("Menu");
	}

	private void runSearch(int sel) {
		if (sel == NOTICE)
			searchNotice();
//		else if (sel == ALLPOST)
//			searchAllPost();
	}
	
	private void searchNotice() {
		User user = userManager.readUser(0);
		ArrayList<Post> userPosts = board.get(user);
		if(userPosts == null) {
			System.err.println("공지사항이 없습니다.");
			return;
		}
		
		int totalPosts = board.get(user).size();
		postManager.lookNoticeTitle(PAGE_SIZE, totalPosts, userPosts);
	}

	private boolean isLogin(int check) {
		if (check == CHECK_LOGOUT && log != -1) {
			System.err.println("로그아웃 후 이용가능");
			return false;
		}

		if (check == CHECK_LOGIN && log == -1) {
			System.err.println("로그인 후 이용가능");
			return false;
		}

		return true;
	}

	private String inputString(String message) {
		System.out.print(message + " : ");
		return sc.next();
	}

	public void run() {
		while (true) {
			System.out.println(board);
			System.out.println(userManager.group);
			runMenu(printMenu());
		}
	}
}
