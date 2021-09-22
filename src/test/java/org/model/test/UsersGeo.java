package org.model.test;

public class UsersGeo {
	
	private float lat;
	private float lng;
		
	public UsersGeo(float lat, float lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public float getLat() {
		return lat;
	}
	
	public float getLng() {
		return lng;
	}

	public UsersGeo() {
		super();
	}
}
