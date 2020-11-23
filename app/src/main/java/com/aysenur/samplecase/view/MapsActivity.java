package com.aysenur.samplecase.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.viewmodel.EventViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Geocoder geocoder;

    private EventViewModel eventViewModel;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
            FloatingActionButton btnAddNote = findViewById(R.id.btn_add);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                geocoder = new Geocoder(this);

                btnAddNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnAddNote.hide();
                        handler = new Handler();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, EventFragment.newInstance())
                                .commit();
                        startActivityForResult(getIntent(), ADD_NOTE_REQUEST);
                    }

                });
            }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);
        // Add a marker in Sydney and move the camera

        try {
            List<Address> addreses = geocoder.getFromLocationName("sydney", 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                LatLng london = new LatLng(address.getLatitude(), address.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(london)
                        .title(address.getLocality());
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {

        Log.d(TAG, "onMapLongClick" + latLng.toString());
        try {
            List<Address> addreses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                String streetAdress = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(streetAdress) //marker üzerinde adres yazıyor
                        .draggable(true) //suruklenebilir


                );
                eventViewModel= ViewModelProviders.of(MapsActivity.this).get(EventViewModel.class);
                eventViewModel.init();
                eventViewModel.sendData(streetAdress);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d(TAG, "OnMarkerDragStart");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "OnMarkerDrag");

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG, "OnMarkerDragEnd");

    }


/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    //replace(R.id.fragment_container,EventFragment.newInstance());
                    remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
        }
    }
*/

}
