package com.example.quickyes_project1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class reasontodeletedisplayallreportadapter extends FirestoreRecyclerAdapter<allreportsretiveclass,reasontodeletedisplayallreportadapter.myholder> {
    public reasontodeletedisplayallreportadapter(@NonNull FirestoreRecyclerOptions<allreportsretiveclass> options) {
        super(options);
    }

    @Override
        protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull allreportsretiveclass model) {
            holder.t1.setText(model.getReport_Name()+".Report");
            holder.t2.setText(model.getReason());

        }

        @NonNull
        @Override
        public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleentitiesallreportsnamedisplayreasontodelete,parent,false);
            return new myholder(view);
        }

        class myholder extends RecyclerView.ViewHolder {
            TextView t1,t2;
            public myholder(@NonNull View itemView) {
                super(itemView);
                t1=itemView.findViewById(R.id.singlereportnamedeletereason);
                t2=itemView.findViewById(R.id.singledeletereportname);

            }
        }
    }

