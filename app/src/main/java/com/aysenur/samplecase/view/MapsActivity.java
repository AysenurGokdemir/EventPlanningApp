package com.aysenur.samplecase.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.adapter.ExpTestAdapter;
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

import java.io.IOException;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener {
    public static final int ADD_NOTE_REQUEST = 1;

    public static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Geocoder geocoder;
    private Toolbar toolbar;
    private EventViewModel eventViewModel;
    ExpandableListView expandableListView;
    ExpTestAdapter expandableListAdapter;
    Button btn;
    List<Event> expandableListDetail;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

       // getInit();
       // setExpandableListView();
       // expandableListView = findViewById(R.id.exp_list_view);

            FloatingActionButton btnAddNote = findViewById(R.id.btn_add);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                geocoder = new Geocoder(this);


                btnAddNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnAddNote.hide();
                        mMap.clear();
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
        LatLng latLng;
        // Add a marker in Sydney and move the camera

        try {
            List<Address> addreses = geocoder.getFromLocation(-34,151, 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                LatLng london = new LatLng(address.getLatitude(), address.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(london).draggable(true)
                        .title(address.getLocality());
                mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        goLocation();


    }

    public void goLocation(){
        Intent data = getIntent();
        String title = data.getStringExtra(ExpTestAdapter.EXTRA_TITLE);

        if (data!=null){
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                List<Address> addressList=geocoder.getFromLocationName(title,1);
                if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
                        .title(streetAdress) //marker üzerinde adres yazıyor
                        .draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) //suruklenebilir


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
        Log.d(TAG, "OnMarkerDragStart" + marker.getPosition().latitude);
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "OnMarkerDrag"+ marker.getPosition());
       // marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG, "OnMarkerDragEnd"+marker.getPosition());
        //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        LatLng latLng = marker.getPosition();
        try {
            List<Address> addreses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addreses.size() > 0) {
                Address address = addreses.get(0);
                String streetAdress = address.getAddressLine(0);
                marker.setTitle(streetAdress);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                eventViewModel= ViewModelProviders.of(MapsActivity.this).get(EventViewModel.class);
                eventViewModel.init();
                eventViewModel.sendData(streetAdress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /*
    public void getInit(){

        setSupportActionBar(toolbar);

        DrawerLayout drawer= findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer, toolbar,R.string.Open,R.string.Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer=findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    void setExpandableListView() {

        expandableListDataPump();
        expandableListView = findViewById(R.id.exp_list_view);

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        " List Collapsed." ,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void expandableListDataPump() {
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        Intent data = getIntent();
        String title = data.getStringExtra(EventFragment.EXTRA_TITLE);
        String desc = data.getStringExtra(EventFragment.EXTRA_DESC);

        Event event = new Event(title, desc);
        eventViewModel.insert(event);

        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();


        eventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                //expandableListAdapter.setEvents(events);
                expandableListAdapter=new ExpTestAdapter(getApplicationContext(),events);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListAdapter.notifyDataSetChanged();

                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {


                        Toast.makeText(getApplicationContext(), " List Expanded."+ events.get(groupPosition).getJobName(),
                                Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_event_menu,menu);

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
*/




}
