package com.yy.androidsenior11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void day(View view) {
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setWindowAnimations(R.style.dayNight);
            recreate();
        }
    }

    public void night(View view) {
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setWindowAnimations(R.style.dayNight);
            recreate();
        }
    }
}
