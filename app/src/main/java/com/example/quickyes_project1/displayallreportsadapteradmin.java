package com.example.quickyes_project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class displayallreportsadapteradmin extends FirestoreRecyclerAdapter<allreportsretiveclass,displayallreportsadapteradmin.myholder> {
        public displayallreportsadapteradmin(@NonNull FirestoreRecyclerOptions<allreportsretiveclass> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull allreportsretiveclass model) {
            holder.t1.setText(model.getReport_Name());
            FirebaseFirestore fbfs = FirebaseFirestore.getInstance();
            DocumentReference dr = fbfs.collection("Student").document(model.getEmail());
            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String name = (String) task.getResult().get("First_Name") + task.getResult().get("Last_Name");
                        holder.t2.setText(name);
                    }
                }
            });
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), whytodelete.class);
                    i.putExtra("report_name", model.getReport_Name());
                    i.putExtra("email", model.getEmail());
                    i.putExtra("report_id",model.getReport_ID());
                    v.getContext().startActivity(i);

                }
            });


            holder.cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(v.getContext(),detailreportsofchoosentopic.class);
                    i.putExtra("Report1", model.getReport1());
                    i.putExtra("Report_Name", model.getReport_Name());
                    i.putExtra("Report2", model.getReport2());
                    i.putExtra("Email", model.getEmail());
                    i.putExtra("Acadamic_Year", model.getAcadamic_Year());
                    i.putExtra("Class_And_Division", model.getClass_And_Division());
                    i.putExtra("Name_Of_Program", model.getName_Of_Program());
                    i.putExtra("Name_Of_Activity", model.getName_Of_Activity());
                    i.putExtra("Name_Of_Activity_Codinator", model.getName_Of_Activity_Codinator());
                    i.putExtra("Name_Of_Student_Codinator", model.getName_Of_Student_Codinator());
                    i.putExtra("Name_And_Address_Of_Guest", model.getName_And_Address_Of_Guest());
                    i.putExtra("Date_Of_Conduction", model.getDate_Of_Conduction());
                    i.putExtra("Total_Beneficiaries", model.getTotal_Beneficiaries());
                    i.putExtra("CO", model.getCO());
                    i.putExtra("PO", model.getPO());
                    i.putExtra("PSO", model.getPSO());
                    i.putExtra("Activity_Description", model.getActivity_Description());
                    v.getContext().startActivity(i);
                }
            });
        }

        @NonNull
        @Override
        public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleentitiesallreportnamedisplayadmin,parent,false);
            return new myholder(view);
        }

        class myholder extends RecyclerView.ViewHolder {
            ImageView iv;
            TextView t1,t2;
            CardView cd;
            public myholder(@NonNull View itemView) {
                super(itemView);
                t1=itemView.findViewById(R.id.singlereportnameadmin);
                t2=itemView.findViewById(R.id.singlestudentnameadmin);
                cd=itemView.findViewById(R.id.reporttopicadmin);
                iv=itemView.findViewById(R.id.deletereportadmin);

            }
        }
    }

