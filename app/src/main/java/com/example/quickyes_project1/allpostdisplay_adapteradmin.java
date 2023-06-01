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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class allpostdisplay_adapteradmin extends FirestoreRecyclerAdapter<allpostretrivedataclass,allpostdisplay_adapteradmin.myholder> {
public allpostdisplay_adapteradmin(@NonNull FirestoreRecyclerOptions<allpostretrivedataclass> options) {
        super(options);
        }
    String username1,profileimage,fullname;

@Override
protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull allpostretrivedataclass model) {

    List<SlideModel> imagelist = new ArrayList<>();
        holder.is.setImageList(checkimagestatus(imagelist,model.getPost1(),model.getPost2(),model.getPost3(),"0"), false);
        holder.description.setText(model.getPost_Description());
        FirebaseFirestore fbfs=FirebaseFirestore.getInstance();
        DocumentReference dr=fbfs.collection("Student").document(model.getEmail());
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if(task.isSuccessful()) {
            fullname=(String)task.getResult().get("First_Name")+task.getResult().get("Last_Name");
        username1=(String) task.getResult().get("User_Name");
        profileimage=(String)task.getResult().get("Profile_Image");
        holder.username.setText(username1);
        Picasso.get().load(profileimage).into(holder.riv);
        }
        }

        });

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setMessage("Choose One Option!!!.")
                        .setCancelable(false)
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore fbfs=FirebaseFirestore.getInstance();
                                DocumentReference dr=fbfs.collection("Post").document(model.getPost_ID());
                                Map<String,String> putdata=new HashMap<>();
                                putdata.put("Post1",model.getPost1());
                                putdata.put("Post2",model.getPost2());
                                putdata.put("Post3",model.getPost3());
                                putdata.put("Email",model.getEmail());
                                putdata.put("Post_Description",model.getPost_Description());
                                putdata.put("Post_ID", model.getPost_ID());
                                putdata.put("Like_Count","0");
                                putdata.put("Full_Name",fullname);
                                dr.set(putdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            fbfs.collection("Temp").document(model.getPost_ID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Map<String,String> data=new HashMap<>();
                                                    data.put("Post_ID",model.getPost_ID());
                                                    DocumentReference dr=fbfs.collection("Likes").document(model.getPost_ID());
                                                    dr.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {
                                                                Toast.makeText(v.getContext(), "Post Accepted!!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            });

                                        }
                                    }
                                });
                            }
                        }).setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(v.getContext(),whytodeletepost.class);
                        i.putExtra("Email",model.getEmail());
                        i.putExtra("Post_Id",model.getPost_ID());
                        i.putExtra("Post1",model.getPost1());
                        i.putExtra("Post2",model.getPost2());
                        i.putExtra("Post3",model.getPost3());
                        i.putExtra("User_Name",username1);
                        i.putExtra("Profile_Image",profileimage);
                        i.putExtra("Post_Description",model.getPost_Description());
                        v.getContext().startActivity(i);
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });


    holder.is.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
        builder.setMessage("Choose One Option!!!.")
                .setCancelable(false)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore fbfs=FirebaseFirestore.getInstance();
                        DocumentReference dr=fbfs.collection("Post").document(model.getPost_ID());
                        Map<String,String> putdata=new HashMap<>();
                        putdata.put("Post1",model.getPost1());
                        putdata.put("Post2",model.getPost2());
                        putdata.put("Post3",model.getPost3());
                        putdata.put("Email",model.getEmail());
                        putdata.put("Post_Description",model.getPost_Description());
                        putdata.put("Post_ID", model.getPost_ID());
                        putdata.put("Like_Count","0");
                        putdata.put("Full_Name",fullname);
                        dr.set(putdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    fbfs.collection("Temp").document(model.getPost_ID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Map<String,String> data=new HashMap<>();
                                            data.put("Post_ID",model.getPost_ID());
                                            DocumentReference dr=fbfs.collection("Likes").document(model.getPost_ID());
                                            dr.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        Toast.makeText(v.getContext(), "Post Accepted!!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    });

                                }
                            }
                        });
                    }
                }).setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(v.getContext(),whytodeletepost.class);
                i.putExtra("Email",model.getEmail());
                i.putExtra("Post_Id",model.getPost_ID());
                i.putExtra("Post1",model.getPost1());
                i.putExtra("Post2",model.getPost2());
                i.putExtra("Post3",model.getPost3());
                i.putExtra("User_Name",username1);
                i.putExtra("Profile_Image",profileimage);
                i.putExtra("Post_Description",model.getPost_Description());
                v.getContext().startActivity(i);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
});



        holder.riv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
    FirebaseFirestore fbfs=FirebaseFirestore.getInstance();
        DocumentReference dr1=fbfs.collection("Student").document(model.getEmail());
        dr1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if(task.isSuccessful()) {
        String username=(String) task.getResult().get("User_Name");
        String profileimage=(String)task.getResult().get("Profile_Image");
        String firstname=(String) task.getResult().get("Full_Name");
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleallpostentitiseadmin,parent,false);
        return new myholder(view);
        }

class myholder extends RecyclerView.ViewHolder {
    TextView username,description;
    ImageView riv;
    ImageSlider is;
    CardView cd;

    public myholder(@NonNull View itemView) {
        super(itemView);
        username=itemView.findViewById(R.id.allstudentname);
        description=itemView.findViewById(R.id.description);
        riv=itemView.findViewById(R.id.profilepicture);
        is=itemView.findViewById(R.id.postimageslider);
  cd=itemView.findViewById(R.id.singleallpostentitiseadmin);
    }
}

    public List<SlideModel> checkimagestatus(List <SlideModel>imagelist, String image1, String image2, String image3, String image4)
    {
        if (image1 != null & image2 !=null&image3!=null&image4!=null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image2));
            imagelist.add(new SlideModel(image3));
            imagelist.add(new SlideModel(image4));}
        else if (image1 == null & image2 !=null&image3!=null&image4!=null) {
            imagelist.add(new SlideModel(image2));
            imagelist.add(new SlideModel(image3));
            imagelist.add(new SlideModel(image4));}
        else if (image1 == null & image2 ==null&image3!=null&image4!=null) {
            imagelist.add(new SlideModel(image3));
            imagelist.add(new SlideModel(image4));}
        else if (image1 == null & image2 ==null&image3==null&image4!=null) {
            imagelist.add(new SlideModel(image4));}

        else if (image1 != null & image2 ==null&image3!=null&image4!=null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image3));
            imagelist.add(new SlideModel(image4));}
        else if (image1 != null & image2 ==null&image3==null&image4!=null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image4));}
        else if (image1 != null & image2 ==null&image3==null&image4==null) {
            imagelist.add(new SlideModel(image1));
        }
        else if (image1 != null & image2 !=null&image3==null&image4!=null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image2));
            imagelist.add(new SlideModel(image4));}
        else if (image1 != null & image2 !=null&image3==null&image4==null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image2));
        }
        else if (image1 != null & image2 !=null&image3!=null&image4==null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image2));
            imagelist.add(new SlideModel(image3)); }

        else if (image1 != null & image2 ==null&image3!=null&image4==null) {
            imagelist.add(new SlideModel(image1));
            imagelist.add(new SlideModel(image3));
        }
        else{
        }
        return imagelist;
    }

}

