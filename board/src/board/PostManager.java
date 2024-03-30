package board;

import java.util.ArrayList;
import java.util.Scanner;

public class PostManager {

	private Scanner sc = new Scanner(System.in);
	ArrayList<Post> posts = new ArrayList<>();
	
	// CRUD
	// C
	public void creatPost(String id, String title, String content, int code) {
		Post post = new Post(id, title, content, code);
		posts.add(post);
	}
	
	// R
	public Post readPost(int index) {
		return posts.get(index);
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
		sc.nextLine();	// 버퍼 비우기
		System.out.println("<Title>");
		String title = inputStringLine();
		
		if(title.length() < 2) {
			System.err.println("제목은 두 글자 이상이어야 합니다.");
			return "";
		}
		return title;
	}
	
	private String inputStringLine() {
		return sc.nextLine();
	}
	
}
