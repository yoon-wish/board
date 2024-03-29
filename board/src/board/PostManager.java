package board;

import java.util.ArrayList;

public class PostManager {

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
	
	
}
