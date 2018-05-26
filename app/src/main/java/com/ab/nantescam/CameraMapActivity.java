package com.ab.nantescam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class CameraMapActivity extends FragmentActivity implements
		CameraListFragment.Callbacks, Marker.OnMarkerClickListener, MapListener,
		LoaderManager.LoaderCallbacks<List<WebCam>> {

	@Override
	public Loader<List<WebCam>> onCreateLoader(int id, Bundle bundle) {
		return new CamLoader(getApplicationContext());
	}
	@Override
	public void onLoadFinished(Loader<List<WebCam>> loader, List<WebCam> result) {
		getLoaderManager().destroyLoader(0);
		this.result = result;

		map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.camera_map)).getmMapView();
		if (map != null) {

			map.addMapListener(this);

			boolean startWithFavs = getPrefs().getBoolean(
					Cams.KEY_PREF_STARTFAVS, false);
			List<WebCam> values = Cams
					.getNames(startWithFavs ? getFavoritesPrefs() : null, this.result);
			addMarkers(values);

			float zoom = getPrefs().getFloat(Cams.KEY_MAP_ZOOM, 13);
			float lat = getPrefs().getFloat(Cams.KEY_MAP_LAT, 47.21462f);
			float lng = getPrefs().getFloat(Cams.KEY_MAP_LONG, -1.55710f);

            IMapController mapController = map.getController();
            mapController.setZoom(zoom);
            GeoPoint startPoint = new GeoPoint(lat, lng);
            mapController.setCenter(startPoint);
		}
	}
	@Override
	public void onLoaderReset(Loader<List<WebCam>> loader) {
	}

	private List<WebCam> result;
	private MapView map;
	private HashMap<Marker, WebCam> markers = new HashMap<Marker, WebCam>();

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
				// ok
			} else {
				// ko
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
			}
		}

		//Configuration.getInstance().setOsmdroidBasePath(new File(Environment.getExternalStorageDirectory(), "osmdroid"));
		//Configuration.getInstance().setOsmdroidTileCache(new File(Environment.getExternalStorageDirectory(), "osmdroid/tiles"));
		setContentView(R.layout.activity_camera_map);

		if (findViewById(R.id.camera_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
		}

		getSupportLoaderManager().initLoader(0, null, this);


	}

	private void addMarkers(List<WebCam> values) {
		for (Marker marker : markers.keySet()) {
            map.getOverlays().remove(marker);
		}
		markers.clear();
		for (WebCam cam : values) {
			if (cam.getLatitude() > 0.0) {
                Marker m = new Marker(map) {
                    public boolean onLongPress(final MotionEvent event, final MapView mapView) {
                        onMapLongClick(this);
                        return true;
                    }
                };
                m.setDraggable(false);
                m.setPosition(new GeoPoint(cam.getLatitude(), cam.getLongitude()));
                m.setTitle(cam.getName());
				m.setOnMarkerClickListener(this);
                map.getOverlays().add(m);
				markers.put(m, cam);
			}
		}
        map.invalidate();
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
			List<WebCam> values = Cams.getNames(getFavoritesPrefs(), result);
			addMarkers(values);
			Editor edt = getPrefs().edit();
			edt.putBoolean(Cams.KEY_PREF_STARTFAVS, true);
			edt.commit();
		} else if (item.getItemId() == R.id.menu_hidefav) {
			item.setChecked(true);
			List<WebCam> values = Cams.getNames(null, result);
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
			arguments.putParcelable(CameraDetailFragment.ARG_ITEM_ID, Cams.getWebCam(Integer.parseInt(id), result));
			CameraDetailFragment fragment = new CameraDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.camera_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CameraDetailActivity.class);
			detailIntent.putExtra(CameraDetailFragment.ARG_ITEM_ID, Cams.getWebCam(Integer.parseInt(id), result));
			startActivity(detailIntent);
		}
	}

    @Override
    public boolean onMarkerClick(Marker marker, MapView mapView) {
		WebCam cam = markers.get(marker);
		if (cam != null) {
			onItemSelected("" + cam.getCode());
			return true;
		}
		return false;
	}

	public void onMapLongClick(Marker marker) {
		boolean favs = getPrefs().getBoolean(
				Cams.KEY_PREF_STARTFAVS, false);
        WebCam nearest = markers.get(marker);
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
                    map.getOverlays().remove(marker);
				}
                map.invalidate();
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

	public List<WebCam> getCams() {
		return result;
	}

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
		if (map != null)
			map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
		if (map != null)
        	map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        Editor edt = getPrefs().edit();
        edt.putFloat(Cams.KEY_MAP_LAT, (float) event.getSource().getMapCenter().getLatitude());
        edt.putFloat(Cams.KEY_MAP_LONG, (float) event.getSource().getMapCenter().getLongitude());
        edt.commit();
        return false;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        Editor edt = getPrefs().edit();
        edt.putFloat(Cams.KEY_MAP_ZOOM, (float) event.getZoomLevel());
        edt.commit();
        return false;
    }
}
