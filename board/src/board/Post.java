package board;

public class Post {
	
	private String id;
	private String title;
	private String content;
	private int code;
	
	public Post(String id, String title, String content, int code) {
		this.id = id;
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
	
	@Override
	public String toString() {
		String info = "";
		info += "--------------------\n";
		info += "작성자) " + id;
		info += "\n--------------------";
		info += "Title : " + title;
		info += "Content : " + content;
		info += "\n--------------------";
		return info;
	}
	
}
