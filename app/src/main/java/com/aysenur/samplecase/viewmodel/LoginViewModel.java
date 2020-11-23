package com.aysenur.samplecase.viewmodel;

import android.view.View;

import com.aysenur.samplecase.db.model.User;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {


    public MutableLiveData<String> mailAdress = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;

    public MutableLiveData<User> getUSer(){
        if (userMutableLiveData ==  null){
            userMutableLiveData= new MutableLiveData<>();
        }

        return userMutableLiveData;
    }



    public void onClick(View view){

        User user= new User(mailAdress.getValue(), password.getValue());

        userMutableLiveData.setValue(user);

    }


}
