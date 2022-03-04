package com.anjaniy.banglorehomepricepredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anjaniy.banglorehomepricepredictor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widgetSetup();
    }

    private void widgetSetup() {

    }
}