package com.example.quickyes_project1;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class  allpostdisplay_adapter extends FirestoreRecyclerAdapter<allpostretrivedataclass,allpostdisplay_adapter.myholder> {
        public allpostdisplay_adapter(@NonNull FirestoreRecyclerOptions<allpostretrivedataclass> options) {
            super(options);
        }
FirebaseAuth fa=FirebaseAuth.getInstance();
        FirebaseUser fu=fa.getCurrentUser();
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        int counter;
        String likecountercheck;
        @Override
        protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull allpostretrivedataclass model) {
            List<SlideModel> imagelist = new ArrayList<>();
            String img1,img2,img3;
            img1=model.getPost1();
            img2=model.getPost2();
            img3=model.getPost3();
            holder.is.setImageList(checkimagestatus(imagelist,img1,img2,img3), false);
            holder.description.setText(model.getPost_Description());
            fb.collection("Likes").document(model.getPost_ID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        String likecount=(String) task.getResult().get(fu.getEmail());
                        if(likecount!=null){
                            Picasso.get().load(R.drawable.after_like).into(holder.like);

                        }
                        if(model.getEmail()!=null){
                            DocumentReference dr=fb.collection("Student").document(model.getEmail());
                            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        String username=(String) task.getResult().get("User_Name");
                                        String profileimage=(String)task.getResult().get("Profile_Image");
                                        holder.username.setText(username);
                                        Picasso.get().load(profileimage).into(holder.riv);
                                    }
                                }

                            });}
                    }
                }
            });
            DocumentReference drmembers = fb.collection("Post").document(model.getPost_ID());
            drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    likecountercheck= (String) task.getResult().get("Like_Count");
                    counter = Integer.parseInt(likecountercheck);
                    holder.no_of_like.setText(likecountercheck+" likes");
                }
            });


holder.like.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.like.setImageResource(R.drawable.after_like);
        DocumentReference drmembers = fb.collection("Post").document(model.getPost_ID());
        drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                likecountercheck= (String) task.getResult().get("Like_Count");
                counter = Integer.parseInt(likecountercheck);
                holder.no_of_like.setText(likecountercheck+" likes");
            }
        });
        fb.collection("Likes").document(model.getPost_ID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    holder.like.setImageResource(R.drawable.after_like);
                    String likecount=(String) task.getResult().get(fu.getEmail());
                    if(likecount==null){



                        holder.like.setImageResource(R.drawable.after_like);
                        Picasso.get().load(R.drawable.after_like).into(holder.like);
                        DocumentReference dr1 = fb.collection("Post").document(model.getPost_ID());
                        String setcounter = String.valueOf(counter + 1);
                        dr1.update("Like_Count", setcounter).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                fb.collection("Likes").document(model.getPost_ID()).update(fu.getEmail(),fu.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           holder.like.setImageResource(R.drawable.after_like);

                                   }}
                               });
                            }
                        });

                    }
                }
            }
        });
    }
});







holder.riv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DocumentReference dr1=fb.collection("Student").document(model.getEmail());
        dr1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    String username=(String) task.getResult().get("User_Name");
                    String profileimage=(String)task.getResult().get("Profile_Image");
                    String firstname=(String) task.getResult().get("First_Name");
                    String lastname=(String)task.getResult().get("Last_Name");
                    Intent i=new Intent(v.getContext(),after_clicking_profilepic_form_all_user.class);
                    i.putExtra("Full_Name",firstname+lastname);
                    i.putExtra("Profile_Image",profileimage);
                    i.putExtra("User_Name",username);
                    i.putExtra("Email",model.getEmail());
                    v.getContext().startActivity(i);
                }
            }

        });
    }
});

        }

        @NonNull
        @Override
        public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleallpostentitise,parent,false);
            return new myholder(view);
        }

        class myholder extends RecyclerView.ViewHolder {
            TextView username,description,no_of_like;
            ImageView riv;
            ImageSlider is;
            ImageView like;
            public myholder(@NonNull View itemView) {
                super(itemView);
                username=itemView.findViewById(R.id.allstudentname);
                description=itemView.findViewById(R.id.description);
                riv=itemView.findViewById(R.id.profilepicture);
                is=itemView.findViewById(R.id.postimageslider);
                like=itemView.findViewById(R.id.like_button);
                no_of_like=itemView.findViewById(R.id.no_of_like);
            }
        }

    public List<SlideModel> checkimagestatus(List <SlideModel>imagelist,String image1,String image2,String image3)
    {
        if (image1 != null & image2 !=null&image3!=null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image2));
            imagelist.add(new SlideModel(image3)); }
        else if (image1 == null & image2 !=null&image3!=null) {
            imagelist.add(new SlideModel(image2));
            imagelist.add(new SlideModel(image3));
          }
        else if (image1 == null & image2 ==null&image3!=null) {
            imagelist.add(new SlideModel(image3));
          }


        else if (image1 != null & image2 ==null&image3==null) {
            imagelist.add(new SlideModel(image1));
         }

        else if (image1 != null & image2 !=null&image3==null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image2));
        }

        else if (image1 != null & image2 ==null&image3!=null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image3));
        }
        return imagelist;
    }

}

