package com.ab.nantescam;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

public class MapFragment extends Fragment {

    protected MapView mMapView;
    protected CompassOverlay mCompassOverlay;

    public MapView getmMapView(){
        return mMapView;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.map, container,false);
        mMapView = (MapView) root.findViewById(R.id.mapview);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mCompassOverlay = new CompassOverlay(getActivity(), new InternalCompassOrientationProvider(getActivity()), mMapView);
        this.mCompassOverlay.enableCompass();
        mMapView.getOverlays().add(this.mCompassOverlay);

        if (mMapView!=null) {
            mMapView.setBuiltInZoomControls(true);
            mMapView.setMultiTouchControls(true);
            //mMapView.setTilesScaledToDpi(true);
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if (mMapView!=null)
            mMapView.onDetach();
        mMapView=null;
    }
}
