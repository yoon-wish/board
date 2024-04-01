package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private static final Scanner sc = new Scanner(System.in);

	private final int PAGE_SIZE = 3;

	private final String JOIN = "1";
	private final String LEAVE = "2";
	private final String LOGIN = "3";
	private final String LOGOUT = "4";
	private final String WRITE = "5";
	private final String SEARCH = "6";
	private final String MYPAGE = "7";
	private final String MANAGER = "8";

	private final int CHECK_LOGIN = 1;
	private final int CHECK_LOGOUT = 2;

	private final String NOTICE = "1";
	private final String ALLPOST = "2";

	private final String MODIFY = "1";
	private final String DELETE = "2";

	private int log;
	private Map<User, ArrayList<Post>> board;
	private UserManager userManager;
	private PostManager postManager;
	private FileManager fileManager;

	public Board() {
		setBoard();
	}

	private void setBoard() {
		board = new HashMap<>();
		userManager = new UserManager();
		postManager = new PostManager();
		fileManager = new FileManager();
		Admin admin = new Admin("admin", "1234");
		userManager.createAdmin(admin);
		board.put(admin, null);
		log = -1;
	}

	private String printMenu() {
		System.out.println("==== Board ====");
		System.out.println("[1] 회원가입");
		System.out.println("[2] 회원탈퇴");
		System.out.println("[3] 로 그 인");
		System.out.println("[4] 로그아웃");
		System.out.println("[5] 작성하기");
		System.out.println("[6] 조회하기");
		System.out.println("[7] 마이페이지");
		System.out.println("[8] 관리자");

		return inputString("Menu");
	}

	private void runMenu(String sel) {
		if (sel.equals(JOIN)&& isLogin(CHECK_LOGOUT))
			join();
		else if (sel.equals(LEAVE) && isLogin(CHECK_LOGIN))
			leave();
		else if (sel.equals(LOGIN)&& isLogin(CHECK_LOGOUT))
			login();
		else if (sel.equals(LOGOUT)&& isLogin(CHECK_LOGIN))
			logout();
		else if (sel.equals(WRITE)&& isLogin(CHECK_LOGIN))
			write();
		else if (sel.equals(SEARCH)&& isLogin(CHECK_LOGIN))
			runSearch(printSearchSubMenu());
		else if (sel.equals(MYPAGE)&& isLogin(CHECK_LOGIN))
			myPage();
		else if (sel.equals(MANAGER)&& log == 0)
			manager(printManageMenu());

	}

	private String printManageMenu() {
		System.out.println("1) 삭제");
		return inputString("menu");
	}

	private void manager(String sel) {
		ArrayList<Post> posts = postManager.getPosts();
		int totalPosts = posts.size();

		int index = postManager.lookPostsTitle(PAGE_SIZE, totalPosts, posts);
		if(index == -1) {
			return;
		}
		String id = postManager.findUserIdByPageNumber(index);

		int userIndex = findUserIndexById(id);

		int deleteIndex = postManager.deletePostByManager(index);
		User user = userManager.readUser(userIndex);
		board.get(user).remove(deleteIndex);
		System.out.println("삭제완료");

		fileManager.save(saveInfo());
	}

	private int findUserIndexById(String id) {
		int userIndex = 0;

		for (int i = 0; i < board.size(); i++) {
			User user = userManager.readUser(i);
			if (user.getId().equals(id))
				userIndex = i;
		}

		return userIndex;
	}

	private void myPage() {
		User user = userManager.readUser(log);
		ArrayList<Post> userPosts = board.get(user);
		if (userPosts == null) {
			System.err.println("작성한 글이 없습니다.");
			return;
		}
		int totalPosts = board.get(user).size();
		int pageNumber = postManager.lookPostsTitle(PAGE_SIZE, totalPosts, userPosts);
		int index = postManager.findIndex(user.getId(), pageNumber, userPosts);
		if (index != -1) {
			String sel = inputString("1)수정\n2)삭제\n");
			if (sel.equals(MODIFY)) {
				postManager.updatePost(index);
			} else if (sel.equals(DELETE)) {
				if(postManager.deletePost(index))
					board.get(user).remove(index);
			}
			fileManager.save(saveInfo());
		}


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

		fileManager.save(saveInfo());
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

		fileManager.save(saveInfo());
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
		if (userPosts == null) {
			userPosts = new ArrayList<>();
		}

		userPosts.add(post);
		board.put(user, userPosts);

		fileManager.save(saveInfo());
	}

	private String printSearchSubMenu() {
		System.out.println("1) 공지사항");
		System.out.println("2) 전 체 글");
		return inputString("Menu");
	}

	private void runSearch(String sel) {
		if (sel.equals(NOTICE))
			searchNotice();
		else if (sel.equals(ALLPOST))
			searchAllPost();
	}

	private void searchAllPost() {
		ArrayList<Post> posts = postManager.getPosts();
		int totalPosts = posts.size();

		postManager.lookPostsTitle(PAGE_SIZE, totalPosts, posts);
	}

	private void searchNotice() {
		User user = userManager.readUser(0);
		ArrayList<Post> adminPosts = board.get(user);
		if (adminPosts == null) {
			System.err.println("공지사항이 없습니다.");
			return;
		}

		int totalPosts = board.get(user).size();
		postManager.lookPostsTitle(PAGE_SIZE, totalPosts, adminPosts);
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

	private String saveInfo() {
		String info = "";
		for (int i = 1; i < board.size(); i++) {
			User user = userManager.readUser(i);
			info += user.getId() + "/" + user.getPw();

			if (i < board.size() - 1) {
				info += "/";
			}
		}

		for (int i = 0; i < board.size(); i++) {
			User user = userManager.readUser(i);
			if (i == 0) {
				info += "\n";
			}
			if (board.get(user) == null) {
				continue;
			}
			for (int j = 0; j < board.get(user).size(); j++) {
				Post post = board.get(user).get(j);
				info += post.getId() + "/" + post.getTitle() + "/" + post.getContent() + "/" + post.getCode();

				if (j < board.get(user).size() - 1)
					info += "/";
			}
			if (i < board.size() - 1)
				info += "\n";
		}

		return info;
	}

	private void init() {
		String text = fileManager.load();

		if (text != null && text != "") {
			String[] temp = text.split("\n");
			String[] userInfo = temp[0].split("/");
			for (int i = 0; i < userInfo.length; i += 2) {
				userManager.creatUser(userInfo[i], userInfo[i + 1]);

				User user = userManager.readUserById(userInfo[i]);
				board.put(user, null);
			}

			Post post = null;

			for (int i = 1; i < temp.length; i++) {
				String[] postInfo = temp[i].split("/");
				for (int j = 0; j < postInfo.length; j += 4) {
					if (postInfo[j].equals("admin")) {
						post = postManager.createNoticePost(postInfo[j + 1], postInfo[j + 2]);
					} else
						post = postManager.creatPost(postInfo[j], postInfo[j + 1], postInfo[j + 2], postInfo[j + 3]);

					int index = -1;
					if (postInfo[j].equals("null"))
						index = 0;
					else
						index = userManager.findIndexById(postInfo[j]);
					
					User user = userManager.readUser(index);
					ArrayList<Post> userPosts = board.get(user);
					if (userPosts == null) {
						userPosts = new ArrayList<>();
					}

					userPosts.add(post);
					board.put(user, userPosts);
				}
			}
		}
	}

	public static String inputString(String message) {
		System.out.print(message + " : ");
		return sc.nextLine();
	}
	
	public static String inputString() {
		return sc.nextLine();
	}
	
	public void run() {
		init();
		while (true) {
			runMenu(printMenu());
		}
	}
}
