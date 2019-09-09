package com.metacodersbd.noteit;


import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacodersbd.noteit.signFunction.signInPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TakeNote extends AppCompatActivity {

    FirebaseAuth mauth  ;
    DatabaseReference  mref ;

    EditText titleEdit , descEIDT ;
    Button uploadBTn ;
    String  uid ,  title , desc , date ;
    ProgressBar pbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_note);

        mauth = FirebaseAuth.getInstance();
        uid = mauth.getUid() ;
        mref = FirebaseDatabase.getInstance().getReference("notes").child(uid);

        //init views
        titleEdit = findViewById(R.id.title);
        descEIDT = findViewById(R.id.descEdit);
        uploadBTn = findViewById(R.id.uplaodBtn);
        pbar = findViewById(R.id.pbar);


        pbar.setVisibility(View.GONE);


        uploadBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                // taking the date from the andorid Clock
                String delegate = "hh:mm aaa";

                String  Time = String.valueOf(DateFormat.format(delegate, Calendar.getInstance().getTime()));


                 String  DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                 date = Time + " "+DATE;


                // getting the data from the views
                title = titleEdit.getText().toString();
                desc = descEIDT.getText().toString();


                //database Ref


                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(desc)){

                    Toast.makeText(getApplicationContext() , "Fill Up The Data Properly" , Toast.LENGTH_SHORT)
                            .show();

                }
                else {


                    pbar.setVisibility(View.VISIBLE);
                    uploadDataToFireBase(title , desc , date);



                }






            }
        });







    }

    private void uploadDataToFireBase(String Title, String Desc , String Date) {



        String push_id = mref.push().getKey() ;

        HashMap map = new HashMap();

        map.put("title" , Title) ;
        map.put("desc" , Desc) ;
        map.put("date" ,date ) ;
        map.put("color_id", "null") ;
        map.put("id",push_id );

        mref.child(push_id).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        pbar.setVisibility(View.GONE);
                        finish();


                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                pbar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "Error : "+ e.getMessage() , Toast.LENGTH_LONG)
                        .show();


            }
        })  ;






    }
}
