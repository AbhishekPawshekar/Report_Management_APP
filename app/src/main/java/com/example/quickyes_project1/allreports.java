package com.example.quickyes_project1;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.TextView;

public class allreports extends AppCompatActivity {
TextView allreport,addreport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allreports);
        allreport=findViewById(R.id.request_to_allreport);
        addreport=findViewById(R.id.request_to_addreport);
        allreport.setBackgroundColor(Color.argb(255,83,181,241));
        getSupportFragmentManager().beginTransaction().replace(R.id.reportframelayout,new displayallreport()).commit();

        allreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addreport.setBackgroundColor(Color.argb(255,255,255,255));
                allreport.setBackgroundColor(Color.argb(255,83,181,241));
                getSupportFragmentManager().beginTransaction().replace(R.id.reportframelayout,new displayallreport()).commit();
            }
        });
        addreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allreport.setBackgroundColor(Color.argb(255,255,255,255));
                addreport.setBackgroundColor(Color.argb(255,83,181,241));
                getSupportFragmentManager().beginTransaction().replace(R.id.reportframelayout,new addreports()).commit();

            }
        });

    }
}