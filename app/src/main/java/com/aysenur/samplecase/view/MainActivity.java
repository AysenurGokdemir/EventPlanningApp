package com.aysenur.samplecase.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.adapter.EventAdapter;
import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.viewmodel.EventViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private EventViewModel eViewModel;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // TextView tw= findViewById(R.id.hl);
            getInit();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EventAdapter adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        eViewModel= ViewModelProviders.of(this).get(EventViewModel.class);
        Intent data = getIntent();
        String title= data.getStringExtra(EventFragment.EXTRA_TITLE);
        String desc= data.getStringExtra(EventFragment.EXTRA_DESC);

        Event event = new Event(title,desc);
        eViewModel.insert(event);

        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();


        eViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                adapter.setEvents(events);
            }
        });

    }

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

}
