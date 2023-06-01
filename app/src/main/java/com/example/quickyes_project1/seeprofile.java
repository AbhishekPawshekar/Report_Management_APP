package com.example.quickyes_project1;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class seeprofile extends AppCompatActivity {
    TextView nameaccount,useraccount,phoneaccount,emailaccount,departmentaccount,yearaccount;
    FirebaseAuth fba;
    FirebaseUser fbu;
    FirebaseFirestore fbfs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeprofile);
        nameaccount=findViewById(R.id.enternameaccount);
        useraccount=findViewById(R.id.enteruseraccount);
        phoneaccount=findViewById(R.id.enterphoneaccount);
        emailaccount=findViewById(R.id.enteremailaccount);
        departmentaccount=findViewById(R.id.enterdeptaccount);
        yearaccount=findViewById(R.id.enteryearaccount);

        fba= FirebaseAuth.getInstance();
        fbu=fba.getCurrentUser();
        fbfs= FirebaseFirestore.getInstance();

        fbfs.collection("Student").document(fbu.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    nameaccount.setText((String) task.getResult().get("First_Name")+" "+task.getResult().get("Last_Name"));
                    useraccount.setText((String)task.getResult().get("User_Name"));
                    phoneaccount.setText((String) task.getResult().get("Phone"));
                    emailaccount.setText((String) task.getResult().get("Email"));
                    departmentaccount.setText((String) task.getResult().get("Department"));
                    yearaccount.setText((String) task.getResult().get("Current_Year"));

                }else{
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}