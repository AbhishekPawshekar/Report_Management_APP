package com.example.quickyes_project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountclick#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountclick extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public accountclick() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountclick.
     */
    // TODO: Rename and change types and number of parameters
    public static accountclick newInstance(String param1, String param2) {
        accountclick fragment = new accountclick();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
private static final int PICK_IMAGE_REQUEST1=1;
    FirebaseAuth fba;
    FirebaseUser fbu;
    String downloadurl1,email;
    FirebaseFirestore fbfs;
    TextView studentname,seeuserdetails;
    ImageView studentprofileimage,addpost;
    RecyclerView rv;
    Uri imagefile1;
    StorageReference riversRef;
    postofeachstudent_adapter myadpter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accountclick, container, false);
        rv=view.findViewById(R.id.studentpersonalpostrecycleview);
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        studentname=view.findViewById(R.id.studentnamedisplayonprofile);
        seeuserdetails=view.findViewById(R.id.profiledetails);
        studentprofileimage=view.findViewById(R.id.studentprofileimage);
        addpost=view.findViewById(R.id.adding_post);

        fba= FirebaseAuth.getInstance();
        fbu=fba.getCurrentUser();
        fbfs=FirebaseFirestore.getInstance();
        email=fbu.getEmail();
studentprofileimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST1);
        }});
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent i=new Intent(getContext(),addposts.class);
startActivity(i);
            }
        });
        seeuserdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), seeprofile.class);
                startActivity(i);

            }
        });

//setting already profile image
        DocumentReference dr = fbfs.collection("Student").document(fbu.getEmail());
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                String i=(String)task.getResult().get("Profile_Image");
                String name=(String) task.getResult().get("First_Name")+task.getResult().get("Last_Name");
                studentname.setText(name);
                if(i!=null){
                Picasso.get().load(i).into(studentprofileimage);
            }}}
        });
        FirestoreRecyclerOptions<retrivepost_of_each_student_retriveclass> options = new FirestoreRecyclerOptions.Builder<retrivepost_of_each_student_retriveclass>()
                .setQuery(FirebaseFirestore.getInstance().collection("Post").whereEqualTo("Email",fbu.getEmail()), retrivepost_of_each_student_retriveclass.class)
                .build();
        myadpter = new postofeachstudent_adapter(options);
        rv.setAdapter(myadpter);
        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagefile1 = data.getData();
            studentprofileimage.setImageURI(imagefile1);



            fba= FirebaseAuth.getInstance();
            fbu=fba.getCurrentUser();
            fbfs=FirebaseFirestore.getInstance();
            email=fbu.getEmail();
            getreducemoreimagesize(imagefile1, "Profile_Image");

            StorageReference riverRef1 = FirebaseStorage.getInstance().getReference().child(email+ "/" + "Profile_Image");
            riverRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                public void onSuccess(Uri uri) {
                    downloadurl1 = uri.toString();

                    DocumentReference dr = fbfs.collection("Student").document(email);
                    dr.update("Profile_Image", downloadurl1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Profile Image Changed Successfully!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error In Uploading", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }

    }

    public void getreducemoreimagesize(Uri imageuri, String location) {
        fba = FirebaseAuth.getInstance();
        fbu = fba.getCurrentUser();
        riversRef = FirebaseStorage.getInstance().getReference().child(fbu.getEmail() + "/" + location);
        Bitmap fullsizebitmap = null;
        try {
            fullsizebitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageuri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        fullsizebitmap.compress(Bitmap.CompressFormat.JPEG, 25, bos);
        byte[] bitmapdata = bos.toByteArray();
        UploadTask uploadTask = riversRef.putBytes(bitmapdata);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });

    }

}