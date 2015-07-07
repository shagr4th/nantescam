package com.ab.nantescam;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * An activity representing a single Camera detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link CameraListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link CameraDetailFragment}.
 */
public class CameraDetailActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putParcelable(CameraDetailFragment.ARG_ITEM_ID, getIntent()
					.getParcelableExtra(CameraDetailFragment.ARG_ITEM_ID));
			CameraDetailFragment fragment = new CameraDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.camera_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String fav = Util.getPrefs(this).getString(Cams.KEY_PREF_FAVS, "");
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this,
					CameraMapActivity.class));
			return true;
		case R.id.menu_refresh: {
			CameraDetailFragment cdf = ((CameraDetailFragment) getSupportFragmentManager().findFragmentById(
					R.id.camera_detail_container));
			if (cdf != null)
				cdf.launchDownload();
		}
		case R.id.add_favorite: {
			CameraDetailFragment cdf = ((CameraDetailFragment) getSupportFragmentManager().findFragmentById(
					R.id.camera_detail_container));
			if (cdf != null) {
				WebCam itemr = cdf.getWebCam();
				String itemRow = itemr.getCode() + "|";
				if (!fav.contains(itemRow))
					fav = fav + itemRow;
				Editor edt = Util.getPrefs(this).edit();
				edt.putString(Cams.KEY_PREF_FAVS, fav);
				edt.commit();
			}
		}
		case R.id.del_favorite: {
			CameraDetailFragment cdf = ((CameraDetailFragment) getSupportFragmentManager().findFragmentById(
					R.id.camera_detail_container));
			if (cdf != null) {
				WebCam itemr = cdf.getWebCam();
				String itemRow = itemr.getCode() + "|";
				if (fav.contains(itemRow))
					fav = fav.replace(itemRow, "");
				Editor edt = Util.getPrefs(this).edit();
				edt.putString(Cams.KEY_PREF_FAVS, fav);
				edt.commit();
			}
		}
		}
		return super.onOptionsItemSelected(item);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.image_main, menu);
		getMenuInflater().inflate(R.menu.addfavorite, menu);
		getMenuInflater().inflate(R.menu.delfavorite, menu);
		return true;
	}*/
}
