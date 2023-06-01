package com.example.quickyes_project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class






















login_page extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button loginbackbutton;
    TextInputLayout email_entered, password_entered;

    public void request_to_signup(View v) {
        Intent i = new Intent(getApplicationContext(), registration_page.class);
        startActivity(i);
    }

    public boolean validation_email() {
        String email_enter = email_entered.getEditText().getText().toString().trim();
        if (email_enter.isEmpty()) {
            email_entered.setError("Field can't be empty");
            return false;
        } else if (!email_enter.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            email_entered.setError("wrong Email Format");
            return false;
        } else {
            email_entered.setError(null);
            email_entered.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validation_password() {
        String password_enter=password_entered.getEditText().getText().toString().trim();
        if(password_enter.isEmpty()){
            password_entered.setError("Field Can't be Empty");
            return false;
        }
        else if(password_enter.length()<8){
            password_entered.setError("Wrong Password");
            return false;
        }else{
            password_entered.setError(null);
            password_entered.setErrorEnabled(false);
            return true;
        }
    }
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure You Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginbackbutton=findViewById(R.id.loginback);
        email_entered=findViewById(R.id.editText);
        password_entered=findViewById(R.id.editText2);
        loginbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation_email() | !validation_password()) {
                    return;
                }
                String email_enter = email_entered.getEditText().getText().toString().trim();
                String password_enter = password_entered.getEditText().getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email_enter, password_enter).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        { String email_enter = email_entered.getEditText().getText().toString().trim();

                            DocumentReference dr=db.collection("admin").document(email_enter);
                            dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Intent i=new Intent(getApplicationContext(),admin_view.class);
                                        Toast.makeText(getApplicationContext(),"Welcome to admin page",Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                    }
                                    else{
                                        Intent i = new Intent(getApplicationContext(), mainpage.class);
                                        Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Email-Id or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
