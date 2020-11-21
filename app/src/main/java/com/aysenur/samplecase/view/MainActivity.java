package com.aysenur.samplecase.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.adapter.EventAdapter;
import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.viewmodel.EventViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    private EventViewModel eViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // TextView tw= findViewById(R.id.hl);
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

}
