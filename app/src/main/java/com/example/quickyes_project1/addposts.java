package com.example.quickyes_project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class addposts extends AppCompatActivity {
    String moreimage1="postimage1",moreimage2="postimage2",moreimage3="postimage3",counter,downloadurl1,downloadurl2,downloadurl3,fullname;
    Button selectimage1,selectimage2,selectimage3,addmoreimagebutton;
    StorageReference riversRef;
    FirebaseAuth fba;
    FirebaseUser fbu;
    TextInputLayout posttitle;
    ImageView postimage1,postimage2,postimage3;
    Uri imagefile1,imagefile2,imagefile3;
    FirebaseFirestore fbfs;
    int postcounter;
    public static final int  PICK_IMAGE_REQUEST1=2,PICK_IMAGE_REQUEST2=3,PICK_IMAGE_REQUEST3=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addposts);
        selectimage1=findViewById(R.id.selectimage1);
        selectimage2=findViewById(R.id.selectimage2);
        selectimage3=findViewById(R.id.selectimage3);
        postimage1=findViewById(R.id.postimage1);
        postimage2=findViewById(R.id.postimage2);
        postimage3=findViewById(R.id.postimage3);
        posttitle=findViewById(R.id.enterpostdetails);
        addmoreimagebutton=findViewById(R.id.addmoreimagebutton);
        fbfs=FirebaseFirestore.getInstance();
        fba=FirebaseAuth.getInstance();
        fbu=fba.getCurrentUser();
        DocumentReference drmembers = fbfs.collection("Counter_Details").document("PostManager");
        drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                counter = (String) task.getResult().get("Total_Count");
                postcounter = Integer.parseInt(counter);
            }
        });
        selectimage2.setEnabled(false);
        selectimage3.setEnabled(false);
        selectimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,PICK_IMAGE_REQUEST1);


            }
        });
        selectimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,PICK_IMAGE_REQUEST2);

            }
        });
        selectimage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,PICK_IMAGE_REQUEST3);

            }
        });





        addmoreimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbfs.collection("Student").document(fbu.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            fullname=(String)task.getResult().get("First_Name")+task.getResult().get("Last_Name");
                                        Map<String, String> putdata = new HashMap<>();
                                        putdata.put("Email", fbu.getEmail());
                                        putdata.put("Post_Description", posttitle.getEditText().getText().toString());
                                        putdata.put("Post_ID", counter);
                                        putdata.put("Full_Name", fullname.toLowerCase().trim());
                                        putdata.put("Post1",downloadurl1);
                                        putdata.put("Post2",downloadurl2);
                                        putdata.put("Post3",downloadurl3);
                                        DocumentReference dr = fbfs.collection("Temp").document(counter);
                                        dr.set(putdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                            DocumentReference dr1 = fbfs.collection("Counter_Details").document("PostManager");
                                                            String setcounter = String.valueOf(postcounter + 1);
                                                            dr1.update("Total_Count", setcounter).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(getApplicationContext(), "plz..wait for Post Verification!!!", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(getApplicationContext(),mainpage.class));
                                                                }
                                                            });

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Error In Uploading", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                        }
                    }
                });
                                    }
                                });
            }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imagefile1=data.getData();
            postimage1.setImageURI(imagefile1);
            getreducemoreimagesize(imagefile1, moreimage1);
            StorageReference riverRef1 = FirebaseStorage.getInstance().getReference().child(counter + "/" + moreimage1);
            riverRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                public void onSuccess(Uri uri) {
                    downloadurl1 = uri.toString();
                    selectimage2.setEnabled(true);
                }});
        }
        if(requestCode==PICK_IMAGE_REQUEST2&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imagefile2=data.getData();
            postimage2.setImageURI(imagefile2);
            getreducemoreimagesize(imagefile2, moreimage2);
            StorageReference riverRef2 = FirebaseStorage.getInstance().getReference().child(counter + "/" + moreimage2);
            riverRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                public void onSuccess(Uri uri) {
                    downloadurl2 = uri.toString();
                    selectimage3.setEnabled(true);
                }});

        }
        if(requestCode==PICK_IMAGE_REQUEST3&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {
            imagefile3 = data.getData();
            postimage3.setImageURI(imagefile3);
            getreducemoreimagesize(imagefile3, moreimage3);
            StorageReference riverRef3 = FirebaseStorage.getInstance().getReference().child(counter + "/" + moreimage3);
            riverRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                public void onSuccess(Uri uri) {
                    downloadurl3 = uri.toString();
                }});

        }
    }

    public void getreducemoreimagesize(Uri imageuri, String location){
        fba=FirebaseAuth.getInstance();
        fbu=fba.getCurrentUser();
        riversRef = FirebaseStorage.getInstance().getReference().child(counter+"/"+location);
        Bitmap fullsizebitmap=null;

        try {
            fullsizebitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
        }catch (IOException e)
        {e.printStackTrace(); }

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        fullsizebitmap.compress(Bitmap.CompressFormat.JPEG,25,bos);
        byte[] bitmapdata=bos.toByteArray();
        UploadTask uploadTask=riversRef.putBytes(bitmapdata);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {}
        });


    }
}