package board;

public class Post {
	
	private String title;
	private String content;
	private int code;
	
	public Post(String title, String content, int code) {
		this.title = title;
		this.content = content;
		this.code = code;
	}
	
	// Admin용 포스트
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
		this.code = 0000;
	}
	
	public int getCode() {
		return this.code;
	}
	
}
