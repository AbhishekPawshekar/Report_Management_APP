package com.example.quickyes_project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addreports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addreports extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addreports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addreports.
     */
    // TODO: Rename and change types and number of parameters
    public static addreports newInstance(String param1, String param2) {
        addreports fragment = new addreports();
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
    String moreimage1="Report1",moreimage2="Report2",downloadurl1,downloadurl2,counter;
    public static final int PICK_IMAGE_REQUEST1 = 2, PICK_IMAGE_REQUEST2 = 3;
    TextInputLayout nameofreport, acadamicyear, classanddivision, nameofprogram, nameofactivity, nameofactivitycodinator, nameofstudentcodinator, nameandaddressofguest, dateofconduction, totalbeneficiaries, co, po, pso, activityreport;
    Button selectimage1, selectimage2, submit;
    ImageView postimage1, postimage2;
    Uri imagefile1, imagefile2;
    StorageReference riversRef;
    FirebaseAuth fba = FirebaseAuth.getInstance();
    FirebaseUser fbu = fba.getCurrentUser();
    FirebaseFirestore fbfs;
    int reportcounter;
    String r="r";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addreports, container, false);
        selectimage1 = view.findViewById(R.id.selectimageaddreport1);
        selectimage2 = view.findViewById(R.id.selectimageaddreport2);
        postimage1 = view.findViewById(R.id.photo1);
        postimage2 = view.findViewById(R.id.photo2);

        nameofreport=view.findViewById(R.id.enter_report_name);
        acadamicyear=view.findViewById(R.id.enter_acadamicyear);
        classanddivision=view.findViewById(R.id.enter_classanddiv);
        nameofprogram=view.findViewById(R.id.enter_nameofprogram);
        nameofactivity=view.findViewById(R.id.enter_nameofactivity);
        nameofactivitycodinator=view.findViewById(R.id.enter_activitycodinator);
        nameofstudentcodinator=view.findViewById(R.id.enter_studentcodinator);
        nameandaddressofguest=view.findViewById(R.id.enter_nameandadressofexperts);
        dateofconduction=view.findViewById(R.id.enter_date);
        totalbeneficiaries=view.findViewById(R.id.enter_totalbeneficiaries);
        co=view.findViewById(R.id.enter_co);
        po=view.findViewById(R.id.enter_po);
        pso=view.findViewById(R.id.enter_pso);
        activityreport=view.findViewById(R.id.enter_activityshortdescription);

        submit = view.findViewById(R.id.submit_report);
        fbfs = FirebaseFirestore.getInstance();
        fba = FirebaseAuth.getInstance();
        fbu = fba.getCurrentUser();
        selectimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, PICK_IMAGE_REQUEST1);
            }
        });
        selectimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, PICK_IMAGE_REQUEST2);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference drmembers = fbfs.collection("Counter_Details").document("ReportManager");
                drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        counter = (String) task.getResult().get("Total_Count");
                        reportcounter = Integer.parseInt(counter);
                    }
                });

                getreducemoreimagesize(imagefile1, moreimage1);
                getreducemoreimagesize(imagefile2, moreimage2);
                fba = FirebaseAuth.getInstance();
                fbu = fba.getCurrentUser();


                StorageReference riverRef1 = FirebaseStorage.getInstance().getReference().child(r+counter + "/" + moreimage1);
                riverRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    public void onSuccess(Uri uri) {
                        downloadurl1 = uri.toString();
                                StorageReference riverRef3 = FirebaseStorage.getInstance().getReference().child(r+counter + "/" + moreimage2);
                                riverRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    public void onSuccess(Uri uri) {
                                        downloadurl2 = uri.toString();
                                        Map<String, String> postdetails = new HashMap<>();
                                        postdetails.put("Report1", downloadurl1);
                                        postdetails.put("Report2", downloadurl2);
                                        postdetails.put("Email", fbu.getEmail());
                                        postdetails.put("Report_Name", nameofreport.getEditText().getText().toString().toLowerCase());
                                        postdetails.put("Acadamic_Year", acadamicyear.getEditText().getText().toString());
                                        postdetails.put("Class_And_Division", classanddivision.getEditText().getText().toString());
                                        postdetails.put("Name_Of_Program", nameofprogram.getEditText().getText().toString());
                                        postdetails.put("Name_Of_Activity", nameofactivity.getEditText().getText().toString());
                                        postdetails.put("Name_Of_Activity_Codinator", nameofactivitycodinator.getEditText().getText().toString());
                                        postdetails.put("Name_Of_Student_Codinator", nameofstudentcodinator.getEditText().getText().toString());
                                        postdetails.put("Name_And_Address_Of_Guest", nameandaddressofguest.getEditText().getText().toString());
                                        postdetails.put("Date_Of_Conduction", dateofconduction.getEditText().getText().toString());
                                        postdetails.put("Total_Beneficiaries", totalbeneficiaries.getEditText().getText().toString());
                                        postdetails.put("CO", co.getEditText().getText().toString());
                                        postdetails.put("PO", po.getEditText().getText().toString());
                                        postdetails.put("PSO", pso.getEditText().getText().toString());
                                        postdetails.put("Activity_Description", activityreport.getEditText().getText().toString());
                                        postdetails.put("Report_ID",r+counter);


                                        DocumentReference dr = fbfs.collection("Report").document(r+counter);
                                        dr.set(postdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentReference dr1 = fbfs.collection("Counter_Details").document("ReportManager");
                                                    String setcounter = String.valueOf(reportcounter+ 1);
                                                    dr1.update("Total_Count", setcounter).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(getContext(),"Report Genrated!!!",Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(getContext(), "Error In Uploading", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagefile1 = data.getData();
            postimage1.setImageURI(imagefile1);
        }
        if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagefile2 = data.getData();
            postimage2.setImageURI(imagefile2);
        }
    }

    public void getreducemoreimagesize(Uri imageuri, String location) {
        fba = FirebaseAuth.getInstance();
        fbu = fba.getCurrentUser();
        riversRef = FirebaseStorage.getInstance().getReference().child(r+counter + "/" + location);
        Bitmap fullsizebitmap = null;

        try {
            fullsizebitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageuri);
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