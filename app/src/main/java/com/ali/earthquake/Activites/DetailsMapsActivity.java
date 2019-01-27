package com.ali.earthquake.Activites;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.ali.earthquake.Model.EarthQuake;
import com.ali.earthquake.R;
import com.ali.earthquake.UI.CustomInfoWindow;
import com.ali.earthquake.Util.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        EarthQuake earthQuake = (EarthQuake) getIntent().getSerializableExtra("obj");
        String timeFormatted = Constant.getDate(Long.valueOf(earthQuake.getTime()));
        // Add a marker in Sydney and move the camera
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(earthQuake.getPlace());
        markerOptions.position(new LatLng(earthQuake.getLat(), earthQuake.getLon()));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        markerOptions.snippet("Mag: " + earthQuake.getMagnitude() + "\n" + "Date: " + timeFormatted);

        //Add circle around marker have mag >2
        if (earthQuake.getMagnitude() >= 2.0) {
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(new LatLng(earthQuake.getLat(), earthQuake.getLon()));
            circleOptions.radius(1000);
            circleOptions.strokeWidth(3.5f);
            circleOptions.fillColor(Color.RED);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addCircle(circleOptions);


        }

        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(earthQuake.getDetailsLink());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(earthQuake.getLat(), earthQuake.getLon()), 10));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
