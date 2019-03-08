package com.devandreid.dev.challengevenues.map;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.devandreid.dev.challengevenues.R;
import com.devandreid.dev.challengevenues.details.DetailsActivity;
import com.devandreid.dev.challengevenues.models.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private List<Venue> venueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void drawMarkers(List<Venue> venueList) {
        for(Venue v : venueList) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(
                    v.getLocation().getLat(), v.getLocation().getLng()
            )).title(v.getName())).setTag(v.getId());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        mMap.setOnInfoWindowClickListener(this);
        LatLng seattle = new LatLng(47.6062, -122.3321);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seattle, 14));

        Intent i = getIntent();
        if (i != null) {
            venueList = (List<Venue>) i.getSerializableExtra("venueList");
            drawMarkers(venueList);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Venue venue = null;
        for(Venue v : venueList) {
            if (v.getId().equals(marker.getTag())) {
                venue = v;
                break;
            }
        }
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("venue", venue);
        startActivity(i);
    }
}
