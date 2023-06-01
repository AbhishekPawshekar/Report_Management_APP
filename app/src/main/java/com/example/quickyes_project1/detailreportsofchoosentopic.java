package com.example.quickyes_project1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class detailreportsofchoosentopic extends AppCompatActivity {
String r1,r2,rn,email,ay,cad,nop,noa,noac,nosc,doc,tb,co,po,pso,ad,naaog;
ImageView tr1,tr2;
TextView tay,tcad,tnop,tnoa,tnoac,tnosc,tdoc,ttb,tco,tpo,tpso,tad,tnaaog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailreportsofchoosentopic);
        Intent i=getIntent();
        r1=i.getStringExtra("Report1");
        rn=i.getStringExtra("Report_Name");
        r2=i.getStringExtra("Report2");
        email=i.getStringExtra("Email");
        ay=i.getStringExtra("Acadamic_Year");
        cad=i.getStringExtra("Class_And_Division");
        nop=i.getStringExtra("Name_Of_Program");
        noa=i.getStringExtra("Name_Of_Activity");
       noac= i.getStringExtra("Name_Of_Activity_Codinator");
        nosc=i.getStringExtra("Name_Of_Student_Codinator");
        naaog=i.getStringExtra("Name_And_Address_Of_Guest");
        doc=i.getStringExtra("Date_Of_Conduction");
        tb=i.getStringExtra("Total_Beneficiaries");
        co=i.getStringExtra("CO");
        po=i.getStringExtra("PO");
        pso=i.getStringExtra("PSO");
        ad=i.getStringExtra("Activity_Description");


        tr1=findViewById(R.id.picture1);
        tr2=findViewById(R.id.picture2);
        tay=findViewById(R.id.entertextacademicyear);
        tcad=findViewById(R.id.entertextclassanddivision);
        tnop=findViewById(R.id.entertextnameofprogram);
        tnoa=findViewById(R.id.entertextnameofactivity);
        tnosc=findViewById(R.id.entertextnameofstudentcodinator);
        tnoac=findViewById(R.id.entertextnameofactivitycodinator);
        tnaaog=findViewById(R.id.entertextnameandaddressofexpert);
        tdoc=findViewById(R.id.entertextdateofconduct);
        ttb=findViewById(R.id.entertexttexttotalbeneficiaries);
        tco=findViewById(R.id.entertextco);
        tpo=findViewById(R.id.entertextpo);
        tpso=findViewById(R.id.entertextpso);
        tad=findViewById(R.id.entertextactivitydescription);

        Picasso.get().load(r1).into(tr1);
        Picasso.get().load(r2).into(tr2);
        tay.setText(ay);
        tcad.setText(cad);
        tnop.setText(nop);
        tnoa.setText(noa);
        tnosc.setText(nosc);
        tnoac.setText(noac);
       tnaaog.setText(naaog);
       tdoc.setText(doc);
       ttb.setText(tb);
       tco.setText(co);
       tpo.setText(po);
       tpso.setText(pso);
       tad.setText(ad);
    }
}