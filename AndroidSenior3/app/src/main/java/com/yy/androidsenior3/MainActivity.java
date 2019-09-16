package com.yy.androidsenior3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new MyView1(this));
        setContentView(new MyView2(this));
//        setContentView(new MyView3(this));
    }
}
