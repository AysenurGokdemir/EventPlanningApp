package com.aysenur.samplecase.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.adapter.ExpTestAdapter;
import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.viewmodel.EventViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ExpTestMain extends AppCompatActivity {

    private EventViewModel eViewModel;
    ExpandableListView expandableListView;
    ExpTestAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list);
        setExpandableListView();

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
        eViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        Intent data = getIntent();
        String title = data.getStringExtra(EventFragment.EXTRA_TITLE);
        String desc = data.getStringExtra(EventFragment.EXTRA_DESC);

        Event event = new Event(title, desc);
        eViewModel.insert(event);

        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();


        eViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
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
}