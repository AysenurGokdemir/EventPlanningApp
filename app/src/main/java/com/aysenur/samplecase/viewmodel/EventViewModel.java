package com.aysenur.samplecase.viewmodel;

import android.app.Application;

import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.repository.EventRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EventViewModel extends AndroidViewModel {

    private EventRepository repository;
    private LiveData<List<Event>> allEvents;
    private MutableLiveData<String> stringMutableLiveData;


    public void init(){
        stringMutableLiveData=new MutableLiveData<>();
    }
    public void sendData(String msg){
        stringMutableLiveData.setValue(msg);
    }


    public LiveData<String> getMessage(){
        return stringMutableLiveData;
    }

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
       allEvents=repository.getAllEvents();

    }

    // butona tıkladıgımızda cagrılacak
    public void insert(Event event){

        repository.insert(event);
    }

    public  LiveData<List<Event>> getAllEvents(){return allEvents;}

}
