package org.model.test;

public class UsersCompany {
	
	private String name;
	private String catchPhrase;
	private String bs;
	public UsersCompany(String name, String catchPhrase, String bs) {
		super();
		this.name = name;
		this.catchPhrase = catchPhrase;
		this.bs = bs;
	}
	public String getName() {
		return name;
	}
	public String getCatchPhrase() {
		return catchPhrase;
	}
	public String getBs() {
		return bs;
	}
	
	
}
