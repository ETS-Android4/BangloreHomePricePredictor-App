package com.anjaniy.banglorehomepricepredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.anjaniy.banglorehomepricepredictor.databinding.ActivityLoginBinding;
import com.anjaniy.banglorehomepricepredictor.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupbutton.setOnClickListener(v -> {
            Toast.makeText(SignupActivity.this, "Sign up", Toast.LENGTH_SHORT).show();
        });

        binding.alreadyAccountButton.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });

    }
}