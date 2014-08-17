package com.ab.nantescam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CameraMapActivity extends FragmentActivity implements
		CameraListFragment.Callbacks, OnMarkerClickListener,
		OnMapLongClickListener, OnCameraChangeListener {

	private GoogleMap map;
	private HashMap<Marker, WebCam> markers = new HashMap<Marker, WebCam>();

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_map);

		if (findViewById(R.id.camera_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
		}

		map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.camera_map)).getMap();
		if (map != null) {
			map.setTrafficEnabled(true);
			map.setOnMapLongClickListener(this);
			map.setOnMarkerClickListener(this);
			map.setOnCameraChangeListener(this);

			boolean startWithFavs = getPrefs().getBoolean(
					Cams.KEY_PREF_STARTFAVS, false);
			List<WebCam> values = Cams
					.getNames(startWithFavs ? getFavoritesPrefs() : null);
			addMarkers(values);

			float zoom = getPrefs().getFloat(Cams.KEY_MAP_ZOOM, 13);
			float lat = getPrefs().getFloat(Cams.KEY_MAP_LAT, 47.21462f);
			float lng = getPrefs().getFloat(Cams.KEY_MAP_LONG, -1.55710f);

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
					lng), zoom));
		}
	}

	private void addMarkers(List<WebCam> values) {
		for (Marker marker : markers.keySet()) {
			marker.remove();
		}
		markers.clear();
		for (WebCam cam : values) {
			if (cam.getLatitude() > 0.0) {
				Marker m = map.addMarker(new MarkerOptions().position(
						new LatLng(cam.getLatitude(), cam.getLongitude()))
						.title(cam.getName())
				// .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
						);
				markers.put(m, cam);
			}
		}
	}

	SharedPreferences getPrefs() {
		return getSharedPreferences(Cams.KEY_PREFS, 0);
	}

	List<Integer> getFavoritesPrefs() {
		List<Integer> values = new ArrayList<Integer>();
		String f = getPrefs().getString(Cams.KEY_PREF_FAVS, "");
		String fs[] = f.split("\\|");
		for (String s : fs) {
			if (s.length() > 0)
				values.add(Integer.parseInt(s));
		}
		return values;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		/*CameraDetailFragment cdf = ((CameraDetailFragment) getSupportFragmentManager()
				.findFragmentById(R.id.camera_detail_container));
		if (cdf != null)
			getMenuInflater().inflate(R.menu.image_main, menu);*/
		boolean startWithFavs = getSharedPreferences(Cams.KEY_PREFS, 0)
				.getBoolean(Cams.KEY_PREF_STARTFAVS, false);
		menu.getItem(startWithFavs ? 0 : 1).setChecked(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menu_displayfav) {
			item.setChecked(true);
			List<WebCam> values = Cams.getNames(getFavoritesPrefs());
			addMarkers(values);
			Editor edt = getPrefs().edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, true);
			edt.commit();
		} else if (item.getItemId() == R.id.menu_hidefav) {
			item.setChecked(true);
			List<WebCam> values = Cams.getNames(null);
			addMarkers(values);
			Editor edt = getPrefs().edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, false);
			edt.commit();
		} else if (item.getItemId() == R.id.menu_refresh) {
			CameraDetailFragment cdf = ((CameraDetailFragment) getSupportFragmentManager()
					.findFragmentById(R.id.camera_detail_container));
			if (cdf != null)
				cdf.launchDownload();
		} else if (item.getItemId() == R.id.menu_about) {
			View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
			 
	        // When linking text, force to always use default color. This works
	        // around a pressed color state bug.
	        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
	        int defaultColor = textView.getTextColors().getDefaultColor();
	        textView.setTextColor(defaultColor);
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setIcon(R.drawable.ic_launcher);
	        builder.setTitle(R.string.app_name);
	        builder.setView(messageView);
	        builder.create();
	        builder.show();
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
	public boolean onMarkerClick(Marker marker) {
		WebCam cam = markers.get(marker);
		if (cam != null) {
			onItemSelected("" + cam.getCode());
			return true;
		}
		return false;
	}

	@Override
	public void onMapLongClick(LatLng point) {
		boolean favs = getPrefs().getBoolean(
				Cams.KEY_PREF_STARTFAVS, false);
		WebCam nearest = null;
		Marker nearestMarker = null;
		double nearestL = Double.MAX_VALUE;
		for (Marker marker : markers.keySet()) {
			WebCam cam = markers.get(marker);
			double latdiff = Math.abs(cam.getLatitude() - point.latitude);
			double longdiff = Math.abs(cam.getLongitude() - point.longitude);
			double sum = latdiff * latdiff + longdiff * longdiff;
			if (sum < nearestL) {
				nearestL = sum;
				nearestMarker = marker;
				nearest = cam;
			}
		}
		if (nearest != null) {
			String itemRow = nearest.getCode() + "|";

			String fav = getPrefs().getString(Cams.KEY_PREF_FAVS, "");
			fav = fav + "|";
			if (fav.contains(itemRow)) {
				fav = fav.replace(itemRow, "");
				Toast.makeText(
						this,
						getString(R.string.deleted_favorite)
								+ nearest.getName(), Toast.LENGTH_SHORT).show();
				if (favs) {
					nearestMarker.remove();
				}
			} else if (!favs) {
				fav = fav + itemRow;
				Toast.makeText(this,
						getString(R.string.added_favorite) + nearest.getName(),
						Toast.LENGTH_SHORT).show();
			}
			Editor edt = getPrefs().edit();
			edt.putString(Cams.KEY_PREF_FAVS, fav);
			edt.commit();
		}

	}

	@Override
	public void onCameraChange(CameraPosition position) {
		Editor edt = getPrefs().edit();
		edt.putFloat(Cams.KEY_MAP_ZOOM, position.zoom);
		edt.putFloat(Cams.KEY_MAP_LAT, (float) position.target.latitude);
		edt.putFloat(Cams.KEY_MAP_LONG, (float) position.target.longitude);
		edt.commit();
	}

}
