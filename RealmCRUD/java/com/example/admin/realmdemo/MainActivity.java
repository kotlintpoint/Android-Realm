package com.example.admin.realmdemo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment=new NewPersonFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout,fragment)
                .commit();
    }
}
