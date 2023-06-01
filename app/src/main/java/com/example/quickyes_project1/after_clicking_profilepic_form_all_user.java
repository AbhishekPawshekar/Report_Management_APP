package com.example.quickyes_project1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class after_clicking_profilepic_form_all_user extends AppCompatActivity {
    FirebaseAuth fba;
    FirebaseUser fbu;
    String downloadurl1;
    FirebaseFirestore fbfs;
    String fullname,username,profileimage,email;
    TextView studentname,studentdescription;
    ImageView studentprofileimage,addpost;
    RecyclerView rv;
    Uri imagefile1;
    StorageReference riversRef;
    postofeachstudent_adapter myadpter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_clicking_profilepic_form_all_user);
        rv = findViewById(R.id.studentpersonalpostrecycleview1);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        studentname = findViewById(R.id.studentnamedisplayonprofile1);
        studentprofileimage = findViewById(R.id.studentprofileimage1);
        studentdescription=findViewById(R.id.studentuserdisplayonprofile1);
        Intent i=getIntent();
        email=i.getStringExtra("Email");
        fullname=i.getStringExtra("Full_Name");
        username=i.getStringExtra("User_Name");
        profileimage=i.getStringExtra("Profile_Image");
        if(profileimage!=null){
            Picasso.get().load(profileimage).into(studentprofileimage);
        }
        studentname.setText(fullname);
        studentdescription.setText(username);
        FirestoreRecyclerOptions<retrivepost_of_each_student_retriveclass> options = new FirestoreRecyclerOptions.Builder<retrivepost_of_each_student_retriveclass>()
                .setQuery(FirebaseFirestore.getInstance().collection("Post").whereEqualTo("Email", email), retrivepost_of_each_student_retriveclass.class)
                .build();
        myadpter = new postofeachstudent_adapter(options);
        rv.setAdapter(myadpter);
    }

    @Override
    public void onStart() {
        super.onStart();
        myadpter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myadpter.stopListening();
    }

}