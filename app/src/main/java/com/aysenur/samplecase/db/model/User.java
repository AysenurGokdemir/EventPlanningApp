package com.aysenur.samplecase.db.model;

import android.util.Patterns;

import androidx.annotation.NonNull;

public class User {

    @NonNull
    private String mail;

    @NonNull
    private String password;

    public User(@NonNull String mail, @NonNull String password) {
        this.mail = mail;
        this.password = password;
    }

    @NonNull
    public String getMail() {return mail;}

    public void setMail(@NonNull String mail) {this.mail = mail;}

    @NonNull
    public String getPassword() {return password;}

    public void setPassword(@NonNull String password) {this.password = password;}

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getMail()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }
}
