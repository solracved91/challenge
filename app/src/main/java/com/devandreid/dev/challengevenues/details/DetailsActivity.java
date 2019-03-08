package com.devandreid.dev.challengevenues.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;
import android.widget.TextView;

import com.devandreid.dev.challengevenues.R;
import com.devandreid.dev.challengevenues.models.Venue;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends Activity implements OnMapReadyCallback {

    Venue venue;

    @BindView(R.id.map)
    MapView mapView;

    @BindView(R.id.sliding_panel)
    SlidingUpPanelLayout sliding;

    @BindView(R.id.name_venue)
    TextView nameVenue;

    @BindView(R.id.address_venue)
    TextView addressVenue;

    @BindView(R.id.postal_venue)
    TextView postalVenue;

    @BindView(R.id.country_venue)
    TextView countryVenue;

    @BindView(R.id.state_venue)
    TextView stateVenue;

    @BindView(R.id.city_venue)
    TextView cityVenue;

    @BindView(R.id.category_venue)
    TextView categoryVenue;

    @BindView(R.id.sort_venue)
    TextView sortVenue;

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        sliding.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

        Intent i = getIntent();
        if (i != null) {
            venue = (Venue) i.getSerializableExtra("venue");
            nameVenue.setText(venue.getName());
            addressVenue.setText(venue.getLocation().getAddress());
            postalVenue.setText(venue.getLocation().getPostalCode());
            countryVenue.setText(venue.getLocation().getCountry() + " - ");
            stateVenue.setText(venue.getLocation().getState());
            stateVenue.setText(venue.getLocation().getCity());
            categoryVenue.setText(venue.getCategories().get(0).getPluralName());
            sortVenue.setText(venue.getCategories().get(0).getShortName());
        }

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        MapsInitializer.initialize(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        if (this.venue != null) {
            LatLng latLngVenue = new LatLng(this.venue.getLocation().getLat(), this.venue.getLocation().getLng());
            drawMarkers(latLngVenue, venue.getName());
            LatLng seattle = new LatLng(47.6062, -122.3321);
            drawMarkers(seattle, "Centro");

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(latLngVenue);
            builder.include(seattle);
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) ((width * 0.10)+1);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }

    private void drawMarkers(LatLng latLng, String name) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(name));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null)
            mapView.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }
}
