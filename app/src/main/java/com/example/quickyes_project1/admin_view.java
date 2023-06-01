package com.example.quickyes_project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class admin_view extends AppCompatActivity {
TextView allreportadmin,allpostadmin,logouttextview;
FirebaseAuth fba=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        allreportadmin = findViewById(R.id.request_to_addreportadmin);
        allpostadmin = findViewById(R.id.request_to_allpostadmin);
        logouttextview=findViewById(R.id.logouttextview);
        allpostadmin.setBackgroundColor(Color.argb(255,83,181,241));

        getSupportFragmentManager().beginTransaction().replace(R.id.reportframelayoutadmin, new allpostadmin()).commit();
logouttextview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),login_page.class);
        fba.signOut();
        startActivity(i);
    }
});
        allreportadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allpostadmin.setBackgroundColor(Color.argb(255, 255, 255, 255));
                allreportadmin.setBackgroundColor(Color.argb(255, 83, 181, 241));
                getSupportFragmentManager().beginTransaction().replace(R.id.reportframelayoutadmin, new displayallreportadmin()).commit();
            }
        });
        allpostadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allreportadmin.setBackgroundColor(Color.argb(255, 255, 255, 255));
                allpostadmin.setBackgroundColor(Color.argb(255, 83, 181, 241));
                getSupportFragmentManager().beginTransaction().replace(R.id.reportframelayoutadmin, new allpostadmin()).commit();

            }
        });
    }
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure You Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    }