package com.example.quickyes_project1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class registration_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button signupbackbutton;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    int membercounter,usercounter;
    TextInputLayout first_name1, last_name1, phone_no1, emailaddress1, username1, password1, re_password1;
    String[] depts = { "Computer Dept", "Electrical Dept", "Machanical Dept", "Civil Dept", "IT Dept" };
    String[] years = { "First Year", "Second Year", "Third Year", "Fouth Year" };
    String deptselected,yearselected;
    private boolean validatefirstname()
    {
        String first = first_name1.getEditText().getText().toString().trim();
        if (first.isEmpty()) {
            first_name1.setError("Field Can't Be Empty");
            return false;
        } else if (first.length() > 25) {
            first_name1.setError("First Name Can't Be Greater Then 10");
            return false;
        } else {
            first_name1.setError(null);
            first_name1.setErrorEnabled(false);
            return true; }
    }
    private boolean validateLastName()
    {
        String last = last_name1.getEditText().getText().toString().trim();
        if (last.isEmpty()) {
            last_name1.setError("Field Can't Be Empty");
            return false;
        } else if (last.length() >25) {
            last_name1.setError("Last Name Can't Be Greater Then 10");
            return false;
        }
        else {
            last_name1.setError(null);
            last_name1.setErrorEnabled(false);
            return true; }
    }
    private boolean validatephone()
    {
        String phone= phone_no1.getEditText().getText().toString().trim();


        if (phone.isEmpty()) {
            phone_no1.setError("Field Can't Be Empty");
            return false;
        } else if (phone.length() !=10) {
            phone_no1.setError("Phone No Should Be Exactly 10Digits ");
            return false;
        }
        else {
            phone_no1.setError(null);
            phone_no1.setErrorEnabled(false);
            return true; }
    }
    private boolean validateemail()
    {
        String emailff = emailaddress1.getEditText().getText().toString().trim();
        if (emailff.isEmpty()) {
            emailaddress1.setError("Field Can't Be Empty");
            return false;
        } else if (!emailff.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            emailaddress1.setError("Wrong Email Format");
            return false;
        } else {
            emailaddress1.setError(null);
            emailaddress1.setErrorEnabled(false);
            return true; }
    }
    private boolean validateusername()
    {
        String user = username1.getEditText().getText().toString().trim();
        if (user.isEmpty()) {
            username1.setError("Field Can't Be Empty");
            return false;
        } else if (user.length() > 15) {
            username1.setError("User Name Can't Be Greater Then 15");
            return false;
        } else if(user.matches("\\A\\w\\ {4,10}\\z")){
            username1.setError("No WhiteSpace Are Allowed");
            return false;
        }
        else {
            username1.setError(null);
            username1.setErrorEnabled(false);
            return true; }
    }
    private boolean validatepassword() {
        String pass = password1.getEditText().getText().toString().trim();
        String re_pass = re_password1.getEditText().getText().toString().trim();

        if (pass.isEmpty()|re_pass.isEmpty()) {
            password1.setError("Field Can't Be Empty");
            re_password1.setError("Field Can't Be Empty");
            return false;
        } else if (!pass.equals(re_pass)) {
            re_password1.setError("Password Not Match");
            return false;
        } else if (pass.length() < 8|re_pass.length()<8) {
            re_password1.setError("Must Greater Then 7 Character");
            password1.setError("Must Greater Then 7 Character");
            return false;
        }  else {
            password1.setError(null);
            password1.setErrorEnabled(false);
            re_password1.setError(null);
            re_password1.setErrorEnabled(false);
            return true;
        }
    }
    Spinner dept,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        signupbackbutton = findViewById(R.id.signupback);
        first_name1 = findViewById(R.id.first_name);
        last_name1 = findViewById(R.id.last_name);
        emailaddress1 = findViewById(R.id.emailAddrees);
        phone_no1 = findViewById(R.id.phone_no);
        username1 = findViewById(R.id.user_name);
        password1 = findViewById(R.id.password);
        re_password1 = findViewById(R.id.re_password);
        year=findViewById(R.id.current_year);
        dept=findViewById(R.id.selectdept);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(adapter);

        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter1);
        signupbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatefirstname() | !validateLastName() | !validateemail() | !validateusername() | !validatepassword() | !validatephone()) {
                    return;
                }
                    DocumentReference drmembers=db.collection("Counter_Details").document("Students");
                    drmembers.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String counter= (String) task.getResult().get("Total_Count");
                            membercounter=Integer.parseInt(counter);
                        }
                    });
                    String email2 = emailaddress1.getEditText().getText().toString().trim();
                    String password2 = password1.getEditText().getText().toString().trim();

                    mAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String firstname2 = first_name1.getEditText().getText().toString().trim();
                                String last_name2 = last_name1.getEditText().getText().toString().trim();
                                String email2 = emailaddress1.getEditText().getText().toString().trim();
                                String phone2 = phone_no1.getEditText().getText().toString().trim();
                                String username2 = username1.getEditText().getText().toString().trim();
                                String password2 = password1.getEditText().getText().toString().trim();

                                Map<String, String> user = new HashMap<>();
                                user.put("First_Name", firstname2);
                                user.put("Last_Name", last_name2);
                                user.put("Email", email2);
                                user.put("Phone", phone2);
                                user.put("User_Name", username2);
                                user.put("Password", password2);
                                user.put("Department",dept.getSelectedItem().toString());
                                user.put("Current_Year",year.getSelectedItem().toString());

                                DocumentReference dr = db.collection("Student").document(email2);
                                dr.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            DocumentReference dr1=db.collection("Counter_Details").document("Students");
                                            String setcounter=String.valueOf(membercounter+1);
                                            dr1.update("Total_Count",setcounter).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });
                                            Intent i = new Intent(getApplicationContext(), login_page.class);
                                            Toast.makeText(getApplicationContext(), "Successfully SignUp", Toast.LENGTH_SHORT).show();
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Fail To SignUp", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Already Registered Email-Id", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*switch (parent.getId()){
            case R.id.selectdept:
                deptselected=depts[position];
                String email2 = emailaddress1.getEditText().getText().toString().trim();

                db.collection("Student").document(email2).update("Department",deptselected).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                break;
            case R.id.current_year:
                yearselected=years[position];
                String email3 = emailaddress1.getEditText().getText().toString().trim();
                db.collection("Student").document(email3).update("Current_Year",yearselected).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

