package board;

import java.util.ArrayList;

public class PostManager {

	private ArrayList<Post> posts = new ArrayList<>();
	
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
		
		String title = writeTitle();
		post.setTitle(title);
		String content = writeContent();
		post.setContent(content);
		
		System.out.println("수정완료");
	}

	public String inputCode() {
		return Board.inputString("암호(4글자)");
	}
	
	public int findIndex(String id, int pageNumber, ArrayList<Post> posts) {
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
	public boolean deletePost(int index) {
		Post post = posts.get(index);
		
		String code = inputCode();
		if(!post.getCode().equals(code)) {
			System.err.println("틀렸습니다. 삭제불가.");
			return false;
		}
		posts.remove(index);
		System.out.println("삭제완료");
		return true;
	}
	
	public int deletePostByManager(int index) {
		String id = posts.get(index).getId();
		int count = 0;
		int number = 0;
		for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getId().equals(id)) {
				count ++;
			}
			
			if(count == index) {
				number = i;
			}
		}
		posts.remove(index);
		return number;
	}
	
	public String findUserIdByPageNumber(int pageNumber) {
		System.out.println("pageNumber :  " + pageNumber);
		return posts.get(pageNumber).getId();
	}

	public String writeTitle() {
		String title = Board.inputString("<Title>");
		
		while (title.length() < 2) {
			System.err.println("제목은 두 글자 이상이어야 합니다.");
			title = Board.inputString("<Title>");
		}
		return title;
	}

	public String writeContent() {
		String content = "";
		System.out.println("<Content>");
		while (true) {
			String temp = Board.inputString();
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
		String code = Board.inputString("암호(4자리)");
		while (code.length() != 4 || code.equals("0000")) {
			code = Board.inputString("다시입력(4자리)");
		}
		
		return code;
	}

	public int lookPostsTitle(int pageSize, int totalPosts, ArrayList<Post> posts) {
		int currentPage = 1;
		boolean isRun = true;
		while (isRun) {
			int totalPages = totalPosts / pageSize;
			if (totalPosts % pageSize != 0 || totalPosts <= pageSize)
				totalPages++;
			displayPage(currentPage, totalPosts, pageSize, posts);
			System.out.printf("(%d/%d)\n", currentPage, totalPages);
			String input = Board.inputString("이전(1), 다음(2), 선택(3), 종료(4): ");
			switch (input) {
			case "1":
				if (currentPage > 1)
					currentPage--;
				else
					System.out.println("첫 번째 페이지입니다.");
				break;
			case "2":
				if (currentPage < totalPages)
					currentPage++;
				else
					System.out.println("마지막 페이지입니다.");
				break;
			case "3":
				return lookPosts(posts);
			case "4":
				isRun = false;
				break;
			}
		}
		
		return -1;
	}

	private int lookPosts(ArrayList<Post> posts) {
		System.out.print("번호 : ");
		int pageNumber = Integer.parseInt(Board.inputString()) - 1;
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

}
