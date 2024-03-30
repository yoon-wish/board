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
		for(int i=0; i<posts.size(); i++) {
			post = posts.get(i);
			System.out.println(post);
		}
	}

	public ArrayList<Post> getPosts(){
		return posts;
	}
	
	// U
	public void updatePostTitle(int index, String newTitle) {
		
	}
	
	public void updatePostContent(int index, String newContent) {
		
	}
	
	// D
	public void deletePost(int index) {
		posts.remove(index);
	}
	
	public String writeTitle() {
		System.out.println("<Title>");
		String title = inputStringLine();
		
		if(title.length() < 2) {
			System.err.println("제목은 두 글자 이상이어야 합니다.");
			return "";
		}
		return title;
	}
	
	public String writeContent() {
		String content = "";
		System.out.println("<Content>");
		while(true) {
			String temp = inputStringLine();
			if(temp.equals(".")) {
				break;
			}
			content += temp + "\n";
		}
		
		if(content != "") {
			content = content.substring(0, content.length() -1);
		}
		
		return content;
	}
	
	public String writeCode() {
		String code = inputString("암호(4자리)");
		while(code.length() != 4 || code.equals("0000")) {
			code = inputString("다시입력(4자리)");
		}

		return code;
	}
	
	public void lookPostsTitle(int pageSize, int totalPosts, ArrayList<Post> posts) {
		int currentPage = 1;
		while(true) {
			int  totalPages = totalPosts / pageSize;
			if(totalPages % pageSize !=0)
				totalPages++;
			displayPage(currentPage, totalPosts, pageSize, posts);
			System.out.print("이전(1), 다음(2), 선택(3) : ");
			int input = sc.nextInt();
			switch(input) {
			case 1:
				if(currentPage > 1)
					currentPage --;
				else
					System.out.println("첫 번째 페이지입니다.");
				break;
			case 2:
				if(currentPage < totalPages)
					currentPage++;
				else
					System.out.println("마지막 페이지입니다.");
				break;
			case 3:
				lookPosts(posts);
			}
		}
	}
	
	private void lookPosts(ArrayList<Post> posts) {
		System.out.print("번호 : ");
		int pageNumber = sc.nextInt() - 1;
		if(pageNumber < 0 || pageNumber >= posts.size())
			return;
		Post post = posts.get(pageNumber);
		System.out.println(post);
	}
	
	private void displayPage(int page, int totalPosts, int pageSize, ArrayList<Post> posts) {
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		if(end > totalPosts) {
			end = totalPosts;
		}
		
		for(int i=start; i<end; i++) {
			System.out.print(i+1 + ") ");
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
