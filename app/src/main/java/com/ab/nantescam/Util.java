package com.ab.nantescam;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;

public class Util {
	
	public static SharedPreferences getPrefs(Activity activity) {
		return activity.getSharedPreferences(Cams.KEY_PREFS, 0);
	}

	public static List<Integer> getFavoritesPrefs(Activity activity) {
		List<Integer> values = new ArrayList<Integer>();
		String f = getPrefs(activity).getString(Cams.KEY_PREF_FAVS, "");
		String fs[] = f.split("\\|");
		for (String s : fs) {
			if (s.length() > 0)
				values.add(Integer.parseInt(s));
		}
		return values;
	}

}
