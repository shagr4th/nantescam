package com.ab.nantescam;

public class WebCam implements Comparable<WebCam> {
	private int code;
	private String name;
	private String URL;
	private double latitude;
	private double longitude;
	
	public WebCam(int code, String name, String URL) {
		this.code = code;
		this.name = name;
		this.URL = URL;
	}
	
	public WebCam(int code, String name, String URL, double latitude, double longitude) {
		this (code, name, URL);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
	public int compareTo(WebCam another) {
		return name.compareTo(another.name);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

}
