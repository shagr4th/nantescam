package com.ab.nantescam;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * An activity representing a list of Cameras. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link CameraDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CameraListFragment} and the item details (if present) is a
 * {@link CameraDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link CameraListFragment.Callbacks} interface to listen for item selections.
 */
public class CameraListActivity extends FragmentActivity implements
		CameraListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_list);

		if (findViewById(R.id.camera_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((CameraListFragment) getSupportFragmentManager().findFragmentById(
					R.id.camera_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		//getMenuInflater().inflate(R.menu.image_main, menu);
		boolean startWithFavs = getSharedPreferences(Cams.KEY_PREFS, 0).getBoolean(Cams.KEY_PREF_STARTFAVS, false);
		menu.getItem(startWithFavs?0:1).setChecked(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		CameraListFragment clf = ((CameraListFragment) getSupportFragmentManager().findFragmentById(
				R.id.camera_list));
				
		if (item.getItemId() == R.id.menu_displayfav) {
			item.setChecked(true);
			clf.adapter.clear();
			List<WebCam> values = Cams.getNames(Util.getFavoritesPrefs(this));
			for(WebCam w:values)
				clf.adapter.add(w);
			clf.adapter.notifyDataSetChanged();
			Editor edt = Util.getPrefs(this).edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, true);
			edt.commit();
		} else if (item.getItemId() == R.id.menu_hidefav) {
			item.setChecked(true);
			clf.adapter.clear();
			List<WebCam> values = Cams.getNames(null);
			for(WebCam w:values)
				clf.adapter.add(w);
			clf.adapter.notifyDataSetChanged();
			Editor edt = Util.getPrefs(this).edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, false);
			edt.commit();
		} else if (item.getItemId() == R.id.menu_refresh) {
			CameraDetailFragment cdf = ((CameraDetailFragment) getSupportFragmentManager().findFragmentById(
					R.id.camera_detail_container));
			cdf.launchDownload();
		}
		return false;
	}

	/**
	 * Callback method from {@link CameraListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(CameraDetailFragment.ARG_ITEM_ID, id);
			CameraDetailFragment fragment = new CameraDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.camera_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CameraDetailActivity.class);
			detailIntent.putExtra(CameraDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		
		CameraListFragment clf = ((CameraListFragment) getSupportFragmentManager().findFragmentById(
				R.id.camera_list));
		
		WebCam item = clf.adapter.getItem(info.position);
		String itemRow = item.getCode() + "|";
		
		String fav = Util.getPrefs(this).getString(Cams.KEY_PREF_FAVS, "");
		fav = fav + "|";
		if (fav.contains(itemRow)) {
			menu.add(0, 1, 0, R.string.del_favorite);
		} else
			menu.add(0, 2, 0, R.string.add_favorite);

	}
	
	@Override
    public boolean onContextItemSelected(MenuItem item) {
		CameraListFragment clf = ((CameraListFragment) getSupportFragmentManager().findFragmentById(
				R.id.camera_list));
		
		String fav = Util.getPrefs(this).getString(Cams.KEY_PREF_FAVS, "");
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		WebCam itemr = clf.adapter.getItem(info.position);
		String itemRow = itemr.getCode() + "|";
		if (item.getItemId() == 1) { // remove
			if (fav.contains(itemRow))
				fav = fav.replace(itemRow, "");
		} else if (item.getItemId() == 2) { // add
			if (!fav.contains(itemRow))
				fav = fav + itemRow;
		}
		Editor edt = Util.getPrefs(this).edit();
		edt.putString(Cams.KEY_PREF_FAVS, fav);
		edt.commit();
		if (Util.getPrefs(this).getBoolean(Cams.KEY_PREF_STARTFAVS, false)) {
			clf.adapter.clear();
			List<WebCam> values = Cams.getNames(Util.getFavoritesPrefs(this));
			for(WebCam w:values)
				clf.adapter.add(w);
		}
		clf.adapter.notifyDataSetChanged();
		return false;
	}
}
