package com.aysenur.samplecase.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.databinding.ActivityLoginBinding;
import com.aysenur.samplecase.db.model.User;
import com.aysenur.samplecase.viewmodel.LoginViewModel;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG="result";
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUSer().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (TextUtils.isEmpty(Objects.requireNonNull(user).getMail())){
                    binding.inEmail.setError("Enter an E-Mail Address");
                    binding.inEmail.requestFocus();
                }
                else if (!user.isEmailValid()){
                    binding.inEmail.setError("Enter a Valid E-Mail Address");
                    binding.inEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(Objects.requireNonNull(user).getPassword())){
                    binding.inPassword.setError("Enter a Password");
                    binding.inPassword.requestFocus();


                }
                else if (!user.isPasswordLengthGreaterThan5()){
                    binding.inPassword.setError("Enter st least 6 Digit password");
                    binding.inPassword.requestFocus();
                }
                else {
                    Log.d(TAG, user.getMail());
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }


}
