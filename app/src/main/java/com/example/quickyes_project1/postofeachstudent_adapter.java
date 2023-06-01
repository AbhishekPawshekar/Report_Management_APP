package com.example.quickyes_project1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class postofeachstudent_adapter extends FirestoreRecyclerAdapter<retrivepost_of_each_student_retriveclass,postofeachstudent_adapter.myholder> {
        public postofeachstudent_adapter(@NonNull FirestoreRecyclerOptions<retrivepost_of_each_student_retriveclass> options) {
            super(options);
        }
    FirebaseAuth fa=FirebaseAuth.getInstance();
    FirebaseUser fu=fa.getCurrentUser();
    FirebaseFirestore fb=FirebaseFirestore.getInstance();
    int counter;
    String likecountercheck;
        @Override
        protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull retrivepost_of_each_student_retriveclass model) {
            Picasso.get().load(model.getPost1()).into(holder.img);
            DocumentReference drmembers = fb.collection("Post").document(model.getPost_ID());
            drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    likecountercheck= (String) task.getResult().get("Like_Count");
                    counter = Integer.parseInt(likecountercheck);
                    holder.total_like.setText(likecountercheck+" likes");
                }
            });
            }

        @NonNull
        @Override
        public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleentitiesofeachstudent,parent,false);
            return new myholder(view);
        }

        class myholder extends RecyclerView.ViewHolder {

            ImageView img;
            TextView total_like;
            public myholder(@NonNull View itemView) {
                super(itemView);
               img=itemView.findViewById(R.id.postsimage);
               total_like=itemView.findViewById(R.id.total_like_of_perticular_post);
            }
        }
    }

