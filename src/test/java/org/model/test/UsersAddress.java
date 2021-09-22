package org.model.test;

public class UsersAddress {
	private String street;
	private String suite;
	private String city;
	private String zipcode;
	private UsersGeo usersgeo;
	
	public UsersAddress(String street, String suite, String city, String zipcode, UsersGeo usersgeo) {
		super();
		this.street = street;
		this.suite = suite;
		this.city = city;
		this.zipcode = zipcode;
		this.usersgeo = usersgeo;
	}

	public UsersAddress() {
		super();
	}

	public String getStreet() {
		return street;
	}

	public String getSuite() {
		return suite;
	}

	public String getCity() {
		return city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public UsersGeo getUsersgeo() {
		return usersgeo;
	}

}
