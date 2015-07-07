package com.ab.nantescam;

import android.os.Parcel;
import android.os.Parcelable;

public class WebCam implements Comparable<WebCam>, Parcelable {
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

	public WebCam(Parcel parcel) {
		code = parcel.readInt();
		name = parcel.readString();
		URL = parcel.readString();
		latitude = parcel.readDouble();
		longitude = parcel.readDouble();
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(code);
		dest.writeString(name);
		dest.writeString(URL);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public WebCam createFromParcel(Parcel in) {
			return new WebCam(in);
		}

		public WebCam[] newArray(int size) {
			return new WebCam[size];
		}
	};
}
