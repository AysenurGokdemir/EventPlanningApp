package com.aysenur.samplecase.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.aysenur.samplecase.db.EventDao;
import com.aysenur.samplecase.db.EventDatabase;
import com.aysenur.samplecase.db.model.Event;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        EventDatabase database = EventDatabase.getInstance(application);
        eventDao = database.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public void insert(Event event){
      new InsertEventAsyncTask(eventDao).execute(event);
    }

    public LiveData<List<Event>> getAllEvents(){return allEvents;}

    private static class InsertEventAsyncTask extends AsyncTask<Event,Void,Void>{
        private EventDao eventDao;

        public InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.insert(events[0]);
            return null;
        }
    }
}
