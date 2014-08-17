package com.ab.nantescam;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends ListActivity {

	private CamAdapter adapter;
	
	private SharedPreferences getPrefs() {
		return getSharedPreferences(Cams.KEY_PREFS, 0);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		registerForContextMenu(getListView());
		boolean startWithFavs = getPrefs().getBoolean(Cams.KEY_PREF_STARTFAVS, false);
		List<WebCam> values = Cams.getNames(startWithFavs?getFavoritesPrefs():null);
		adapter = new CamAdapter(this, values);
		setListAdapter(adapter);
	}
	
	private List<Integer> getFavoritesPrefs() {
		List<Integer> values = new ArrayList<Integer>(); 
		String f = getPrefs().getString(Cams.KEY_PREF_FAVS, "");
		String fs [] = f.split("\\|");
		for(String s:fs) {
			if (s.length() > 0)
				values.add(Integer.parseInt(s));
		}
		return values;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		boolean startWithFavs = getPrefs().getBoolean(Cams.KEY_PREF_STARTFAVS, false);
		menu.getItem(startWithFavs?0:1).setChecked(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_displayfav) {
			item.setChecked(true);
			adapter.clear();
			List<WebCam> values = Cams.getNames(getFavoritesPrefs());
			for(WebCam w:values)
				adapter.add(w);
			adapter.notifyDataSetChanged();
			Editor edt = getPrefs().edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, true);
			edt.commit();
		} else if (item.getItemId() == R.id.menu_hidefav) {
			item.setChecked(true);
			adapter.clear();
			List<WebCam> values = Cams.getNames(null);
			for(WebCam w:values)
				adapter.add(w);
			adapter.notifyDataSetChanged();
			Editor edt = getPrefs().edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, false);
			edt.commit();
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		WebCam item = adapter.getItem(position);
		Intent myIntent = new Intent(MainActivity.this, ImageActivity.class);
		myIntent.putExtra(Cams.KEY_IMAGEURL, new String [] {item.getURL(), item.getName()});
		startActivity(myIntent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		
		WebCam item = adapter.getItem(info.position);
		String itemRow = item.getCode() + "|";
		
		String fav = getPrefs().getString(Cams.KEY_PREF_FAVS, "");
		fav = fav + "|";
		if (fav.contains(itemRow)) {
			menu.add(0, 1, 0, R.string.del_favorite);
		} else
			menu.add(0, 2, 0, R.string.add_favorite);

	}
	
	@Override
    public boolean onContextItemSelected(MenuItem item) {
		String fav = getPrefs().getString(Cams.KEY_PREF_FAVS, "");
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		WebCam itemr = adapter.getItem(info.position);
		String itemRow = itemr.getCode() + "|";
		if (item.getItemId() == 1) { // remove
			if (fav.contains(itemRow))
				fav = fav.replace(itemRow, "");
		} else if (item.getItemId() == 2) { // add
			if (!fav.contains(itemRow))
				fav = fav + itemRow;
		}
		Editor edt = getPrefs().edit();
		edt.putString(Cams.KEY_PREF_FAVS, fav);
		edt.commit();
		if (getPrefs().getBoolean(Cams.KEY_PREF_STARTFAVS, false)) {
			adapter.clear();
			List<WebCam> values = Cams.getNames(getFavoritesPrefs());
			for(WebCam w:values)
				adapter.add(w);
		}
		adapter.notifyDataSetChanged();
		return false;
	}
}
