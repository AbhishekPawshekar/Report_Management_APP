package com.example.quickyes_project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class whytodelete extends AppCompatActivity {
String s,mail,reportid;
TextView t1;
Button submit;
TextInputLayout reason;
String deletecounter;
FirebaseFirestore fbfs=FirebaseFirestore.getInstance();
    int deletecounter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whytodelete);

submit=findViewById(R.id.submitwhydelete);
        t1=findViewById(R.id.reportnamewhyadmin);
        reason=findViewById(R.id.whytodelete);



        DocumentReference drmembers=fbfs.collection("Counter_Details").document("Delete_Report");
        drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                deletecounter= (String) task.getResult().get("Total_Count");
                deletecounter1=Integer.parseInt(deletecounter);
            }
        });

submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=getIntent();
        s=i.getStringExtra("report_name");
        mail=i.getStringExtra("email");
        reportid=i.getStringExtra("report_id");
        t1.setText(s);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Sure To Delete " + s)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore fbfs = FirebaseFirestore.getInstance();
                        fbfs.collection("Report").document(reportid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Map<String,String> setdelete=new HashMap<>();
                                    setdelete.put("Report_Name",s);
                                    setdelete.put("Reason",reason.getEditText().getText().toString());
                                    setdelete.put("Email",mail);

                                    fbfs.collection("Deletereport").document(deletecounter).set(setdelete).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                DocumentReference dr1=fbfs.collection("Counter_Details").document("Delete_Report");
                                                String setcounter=String.valueOf(deletecounter1+1);
                                                dr1.update("Total_Count",setcounter).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                    }
                                                });

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Error:"+e,Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    Toast.makeText(v.getContext(), "Report Deleted Successfully!!!.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(v.getContext(), "No Such Report", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
});


    }
}