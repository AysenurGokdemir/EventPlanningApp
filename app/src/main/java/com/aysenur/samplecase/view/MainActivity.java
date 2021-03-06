package com.aysenur.samplecase.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.adapter.ExpAdapter;
import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.viewmodel.EventViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.aysenur.samplecase.R.drawable.ic_check;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, View.OnClickListener {


    public static final String TAG = "Maps";
    private EventViewModel eViewModel;
    private ExpandableListView expandableListView;
    private ExpAdapter expandableListAdapter;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private FloatingActionButton fabAdd;
    private Marker marker = null;
    int sayac = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getInit();
        expandableListView = findViewById(R.id.exp_list_view);
        fabAdd = findViewById(R.id.btn_add);
        fabAdd.setOnClickListener(this);

        eViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        expandableListAdapter = new ExpAdapter(this);

        setExpandableListView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_event_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);

        // Add a marker in Sydney and move the camera

        try {
            List<Address> addreses = geocoder.getFromLocation(-33.86, 151.20, 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                LatLng london = new LatLng(address.getLatitude(), address.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(london).draggable(true)
                        .title(address.getLocality());
                marker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        goLocation();
    }


    @Override
    public void onMapClick(LatLng latLng) {

        try {
            List<Address> addreses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                String streetAdress = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(streetAdress)
                        .draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))


                );
                eViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
                eViewModel.init();
                eViewModel.sendData(streetAdress);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d(TAG, "OnMarkerDragStart" + marker.getPosition().latitude);
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "OnMarkerDrag" + marker.getPosition());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG, "OnMarkerDragEnd" + marker.getPosition());

        LatLng latLng = marker.getPosition();
        try {
            List<Address> addreses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                String streetAdress = address.getAddressLine(0);
                marker.setTitle(streetAdress);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                eViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
                eViewModel.init();
                eViewModel.sendData(streetAdress);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                sayac++;
                fabAdd.setImageResource(ic_check);
                if (sayac == 2) {
                    fabAdd.hide();
                    sayac = 0;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, EventFragment.newInstance())
                            .commit();

                }

                break;
            default:
        }


    }

    void removeMarker() {
        marker.remove();
    }

    void setExpandableListView() {

        expandableListDataPump();

    }

    public void expandableListDataPump() {

        eViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        eViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {

                if (events.size() > 0) {

                    expandableListAdapter.setData(events);
                    expandableListView.setAdapter(expandableListAdapter);
                }
            }
        });

    }

    public void getInit() {

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.Open, R.string.Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    public void goLocation() {
        Intent data = getIntent();
        String title = data.getStringExtra(ExpAdapter.EXTRA_TITLE);

        if (data != null) {
            Geocoder geocoder = new Geocoder(MainActivity.this);
            try {
                List<Address> addressList = geocoder.getFromLocationName(title, 1);
                if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    if (marker != null) {
                        removeMarker();
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
