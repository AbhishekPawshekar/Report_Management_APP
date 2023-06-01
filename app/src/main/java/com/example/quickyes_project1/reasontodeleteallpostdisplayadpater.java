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
import java.util.List;

public class reasontodeleteallpostdisplayadpater extends FirestoreRecyclerAdapter<allpostretrivedataclass,reasontodeleteallpostdisplayadpater.myholder> {
    public reasontodeleteallpostdisplayadpater(@NonNull FirestoreRecyclerOptions<allpostretrivedataclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull allpostretrivedataclass model) {
        List<SlideModel> imagelist = new ArrayList<>();
        holder.is.setImageList(checkimagestatus(imagelist,model.getPost1(),model.getPost2(),model.getPost3(),"0"), false);
        holder.description.setText(model.getPost_Description());
        holder.username.setText(model.getUser_Name());
        Picasso.get().load(model.getProfile_Image()).into(holder.riv);
      holder.cd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
              builder.setMessage(model.getReason())
                      .setCancelable(false)
                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      });
              AlertDialog alertDialog = builder.create();
              alertDialog.show();
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
            cd=itemView.findViewById(R.id.singleallpostentitise);
        }
    }

    public List<SlideModel> checkimagestatus(List <SlideModel>imagelist,String image1,String image2,String image3,String image4)
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


