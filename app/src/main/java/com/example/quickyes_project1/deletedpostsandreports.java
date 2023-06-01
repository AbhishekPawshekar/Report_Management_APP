package com.example.quickyes_project1;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class deletedpostsandreports extends AppCompatActivity {
TextView report,post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletedpostsandreports);
        report=findViewById(R.id.request_to_reasonofdeletereport);
        post=findViewById(R.id.request_to_reasonofdeletepost);
        post.setBackgroundColor(Color.argb(255,83,181,241));

        getSupportFragmentManager().beginTransaction().replace(R.id.reportandpostframelayoutadmin,new deletepostreason()).commit();

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setBackgroundColor(Color.argb(255,255,255,255));
                report.setBackgroundColor(Color.argb(255,83,181,241));
                getSupportFragmentManager().beginTransaction().replace(R.id.reportandpostframelayoutadmin,new deletedreportsreason()).commit();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setBackgroundColor(Color.argb(255,255,255,255));
                post.setBackgroundColor(Color.argb(255,83,181,241));
                getSupportFragmentManager().beginTransaction().replace(R.id.reportandpostframelayoutadmin,new deletepostreason()).commit();

            }
        });
    }
}