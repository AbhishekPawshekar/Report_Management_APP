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
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class whytodeletepost extends AppCompatActivity {
String emaildeletepost,postid,post1,post2,post3,profileimage,username,postdescription;
    String deletecounter;
    Button submitpost;
    int deletecounter1;
    TextInputLayout til;
FirebaseFirestore fbfs=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whytodeletepost);

til=findViewById(R.id.whydeletepost);
submitpost=findViewById(R.id.submit_post);
        DocumentReference drmembers=fbfs.collection("Counter_Details").document("Delete_Post");
        drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                deletecounter= (String) task.getResult().get("Total_Count");
                deletecounter1=Integer.parseInt(deletecounter);
            }
        });




submitpost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Sure To Delete ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore fbfs = FirebaseFirestore.getInstance();
                        Intent i=getIntent();
                        emaildeletepost=i.getStringExtra("Email");
                        postid=i.getStringExtra("Post_Id");
                        post1=i.getStringExtra("Post1");
                        post2=i.getStringExtra("Post2");
                        post3=i.getStringExtra("Post3");
                        profileimage=i.getStringExtra("Profile_Image");
                        username=i.getStringExtra("User_Name");
                        postdescription=i.getStringExtra("Post_Description");
                        fbfs.collection("Temp").document(postid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Map<String,String> data=new HashMap<>();
                                    data.put("Email",emaildeletepost);
                                    data.put("Reason",til.getEditText().getText().toString());
                                    data.put("Post1",post1);
                                    data.put("Post2",post2);
                                    data.put("Post",post3);
                                    data.put("Profile_Image",profileimage);
                                    data.put("User_Name",username);
                                    data.put("Post_Description",postdescription);
                                    fbfs.collection("Deletepost").document(deletecounter).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                DocumentReference dr1=fbfs.collection("Counter_Details").document("Delete_Post");
                                                String setcounter=String.valueOf(deletecounter1+1);
                                                dr1.update("Total_Count",setcounter).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(v.getContext(), "Post Deleted Successfully!!!.", Toast.LENGTH_SHORT).show();
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

                                } else {
                                    Toast.makeText(v.getContext(), "No Such Post", Toast.LENGTH_SHORT).show();
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