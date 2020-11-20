package com.aysenur.samplecase.db;

import com.aysenur.samplecase.db.model.Event;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();
}
