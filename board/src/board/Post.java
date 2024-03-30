package board;

public class Post {

	private String id;
	private String title;
	private String content;
	private String code;

	public Post(String id, String title, String content, String code) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.code = code;
	}

	// Admin용 포스트
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
		this.id = "admin";
		this.code = "0000";
	}

	public String getId() {
		return this.id;
	}

	public String getCode() {
		return this.code;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		String info = "";
		info += "--------------------";
		info += "\n작성자) " + id;
		info += "\n--------------------";
		info += "\nTitle : " + title;
		info += "\nContent : " + content;
		info += "\n--------------------";
		return info;
	}

}
