package com.aysenur.samplecase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.db.model.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private List<Event> events= new ArrayList<>();


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new EventHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

        Event currentEvent = events.get(position);
        holder.twTitle.setText(currentEvent.getJobName());
        holder.twDescription.setText(currentEvent.getJobDescription());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events){
        this.events=events;
        notifyDataSetChanged();
    }

    class EventHolder extends RecyclerView.ViewHolder{

        private TextView twTitle;
        private TextView twDescription;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            twTitle=itemView.findViewById(R.id.tw_title);
            twDescription=itemView.findViewById(R.id.tw_description);
        }
    }
}
