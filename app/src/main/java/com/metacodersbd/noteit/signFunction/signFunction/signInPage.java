package com.metacodersbd.noteit.signFunction.signFunction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.metacodersbd.noteit.R;
import com.metacodersbd.noteit.noteListActivity;

public class signInPage extends AppCompatActivity {


    EditText mailEditText , passEditText  ;
    Button   signInBtn  ;
    String  mail  , pass ;
    FirebaseAuth mauth ;
    ProgressBar  pbar ;
    TextView  regPageBtn ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        //removing the action bar
        getSupportActionBar().hide();

        //init the firebase auth

        mauth = FirebaseAuth.getInstance();


        // init views

        mailEditText = findViewById(R.id.mailEditText);
        passEditText = findViewById(R.id.passEditText);
        signInBtn   = findViewById(R.id.signINBtn);
        pbar = findViewById(R.id.progress_signIn);
        regPageBtn = findViewById(R.id.RegisterBtn) ;


        //setting the pbar init state hidden

                pbar.setVisibility(View.INVISIBLE);


        //setting click listener for the  buttons

        regPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent o = new Intent(getApplicationContext()  , Registration.class);
                startActivity(o);



            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                           // do whatever you like when  sign in button click
                mail = mailEditText.getText().toString().trim() ;
                pass = passEditText.getText().toString().trim() ;


                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) // true
                {
                    Toast.makeText(signInPage.this , "Fill Up The Data Properly" , Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    //sending the mail and pass to the google server for checking
                    pbar.setVisibility(View.VISIBLE);
                    proceedWithSignIN(mail , pass) ;

                }
            }
        });




    }

    private void proceedWithSignIN(String mail  , final String pass){

        mauth.signInWithEmailAndPassword(mail , pass)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {

                      if (task.isSuccessful())
                      {
                          // he is successfully logged in

                          Intent i = new Intent(getApplicationContext()  , noteListActivity.class);
                          startActivity(i);
                          finish();




                      }
                      else {

                            pbar.setVisibility(View.INVISIBLE);
                          Toast.makeText(getApplicationContext() ,  task.getException().toString() + " "  , Toast.LENGTH_LONG)
                                  .show();

                      }


                  }
              }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pbar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext() , e.getMessage()+ " " , Toast.LENGTH_LONG)
                        .show();


            }
        });




    }

    //clt + o  for menu

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user ;

        user = mauth.getCurrentUser();

        // font  is firaFont

        if (user != null){

            // as user is not null then the user is all ready logged in

            Intent i = new Intent(getApplicationContext()  , noteListActivity.class);
            startActivity(i);
            finish();

        }



    }
}
