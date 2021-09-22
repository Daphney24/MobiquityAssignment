package org.model.test;

public class User {
	private int id;
	private String name;
	private String username;
	private String email;
	private String phone;
	private String website;
	private UsersAddress useraddress;
	private UsersCompany userscompany;	
	
	public User() {
		super();
	}

	public User(int id, String name, String username, String email, String phone, String website,
			UsersAddress useraddress, UsersCompany userscompany) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.website = website;
		this.useraddress = useraddress;
		this.userscompany = userscompany;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getWebsite() {
		return website;
	}

	public UsersAddress getUseraddress() {
		return useraddress;
	}

	public UsersCompany getUserscompany() {
		return userscompany;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", phone=" + phone
				+ ", website=" + website + ", useraddress=" + useraddress + ", userscompany=" + userscompany + "]";
	}
	
	
	
}
