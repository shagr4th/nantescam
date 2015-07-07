package com.ab.nantescam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cams {
	
	public static final String KEY_PREFS = "CamPrefs";
	public static final String KEY_PREF_FAVS = "CamPrefFavs";
	public static final String KEY_PREF_STARTFAVS = "CamPrefStartFavs";
	public static final String KEY_IMAGEURL = "ImageURL";
	public static final String KEY_MAP_ZOOM = "mapZoom";
	public static final String KEY_MAP_LAT = "mapLat";
	public static final String KEY_MAP_LONG = "mapLong";
	
	public static List<WebCam> getNames (List<Integer> favorites, List<WebCam> allWebCams) {
		ArrayList<WebCam> values = new ArrayList<WebCam>();
		for(WebCam s:allWebCams) {
			if (favorites == null || favorites.contains(s.getCode()))
				values.add(s);
		}
		Collections.sort(values);
		return values;
	}

	public static WebCam getWebCam(int parseInt, List<WebCam> allWebCams) {
		for(WebCam s:allWebCams) {
			if (s.getCode() == parseInt)
				return s;
		}
		return null;
	}

}
