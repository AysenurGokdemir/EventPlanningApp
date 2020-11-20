package com.aysenur.samplecase.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String jobName;
    private String jobDescription;

    public Event(String jobName, String jobDescription) {
        this.jobName = jobName;
        this.jobDescription = jobDescription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobDescription() {
        return jobDescription;
    }
}
