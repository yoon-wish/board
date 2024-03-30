package board;

import java.util.ArrayList;
import java.util.Scanner;

public class PostManager {

	private Scanner sc = new Scanner(System.in);
	ArrayList<Post> posts = new ArrayList<>();

	// CRUD
	// C
	public Post creatPost(String id, String title, String content, String code) {
		Post post = new Post(id, title, content, code);
		posts.add(post);
		return post;
	}

	public Post createNoticePost(String title, String content) {
		Post post = new Post(title, content);
		posts.add(post);
		return post;
	}

	// R
	public Post readPost(int index) {
		return posts.get(index);
	}

	public void printAllPost() {
		Post post = null;
		for (int i = 0; i < posts.size(); i++) {
			post = posts.get(i);
			System.out.println(post);
		}
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	// U
	public void updatePost(int index) {
		Post post = posts.get(index);
		
		String code = inputCode();
		if(!post.getCode().equals(code)) {
			System.err.println("틀렸습니다. 수정불가.");
			return;
		}
		
		sc.nextLine();
		String title = writeTitle();
		post.setTitle(title);
		String content = writeContent();
		post.setContent(content);
		
		System.out.println("수정완료");
	}

	public String inputCode() {
		System.out.print("암호(4글자) : ");
		return sc.next();
	}
	
	public int findIndex(String id, int pageNumber) {
		int index = -1;
		int count = 0;
		for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getId().equals(id)) {
				count ++;
			}
			
			if(count == pageNumber + 1) {
				index = i;
				break;
			}
		}
		return index;
	}
	// D
	public void deletePost(int index) {
		posts.remove(index);
	}

	public String writeTitle() {		
		System.out.println("<Title>");
		String title = inputStringLine();
		
		while (title.length() < 2) {
			System.err.println("제목은 두 글자 이상이어야 합니다.");
			title = inputStringLine();
		}
		return title;
	}

	public String writeContent() {
		String content = "";
		System.out.println("<Content>");
		while (true) {
			String temp = inputStringLine();
			if (temp.equals(".")) {
				break;
			}
			content += temp + "\n";
		}

		if (content != "") {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	public String writeCode() {
		String code = inputString("암호(4자리)");
		while (code.length() != 4 || code.equals("0000")) {
			code = inputString("다시입력(4자리)");
		}

		return code;
	}

	public int lookPostsTitle(int pageSize, int totalPosts, ArrayList<Post> posts) {
		int currentPage = 1;
		boolean isRun = true;
		while (isRun) {
			int totalPages = totalPosts / pageSize;
			if (totalPages % pageSize != 0)
				totalPages++;
			displayPage(currentPage, totalPosts, pageSize, posts);
			System.out.print("이전(1), 다음(2), 선택(3), 종료(4): ");
			int input = sc.nextInt();
			switch (input) {
			case 1:
				if (currentPage > 1)
					currentPage--;
				else
					System.out.println("첫 번째 페이지입니다.");
				break;
			case 2:
				if (currentPage < totalPages)
					currentPage++;
				else
					System.out.println("마지막 페이지입니다.");
				break;
			case 3:
				return lookPosts(posts);
			case 4:
				isRun = false;
				break;
			}
		}
		
		return -1;
	}

	private int lookPosts(ArrayList<Post> posts) {
		System.out.print("번호 : ");
		int pageNumber = sc.nextInt() - 1;
		if (pageNumber < 0 || pageNumber >= posts.size())
			return -1;
		Post post = posts.get(pageNumber);
		System.out.println(post);
		return pageNumber;
	}

	private void displayPage(int page, int totalPosts, int pageSize, ArrayList<Post> posts) {
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		if (end > totalPosts) {
			end = totalPosts;
		}

		for (int i = start; i < end; i++) {
			System.out.print(i + 1 + ") ");
			System.out.println(posts.get(i).getTitle());
		}
	}

	private String inputString(String message) {
		System.out.print(message + " : ");
		return sc.next();
	}

	private String inputStringLine() {
		return sc.nextLine();
	}

}
