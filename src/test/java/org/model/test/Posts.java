package org.model.test;

public class Posts {
	private int userId;
	private int id;
	private String title;
	private String body;	
	
	public Posts() {
		super();
	}

	public Posts(int userId, int id, String title, String body) {
		super();
		this.userId = userId;
		this.id = id;
		this.title = title;
		this.body = body;
	}

	public int getUserId() {
		return userId;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}
	
}
