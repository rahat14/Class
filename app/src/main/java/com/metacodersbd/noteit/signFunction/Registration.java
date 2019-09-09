package com.metacodersbd.noteit.signFunction;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.metacodersbd.noteit.noteListActivity;
import com.metacodersbd.noteit.R;

public class Registration extends AppCompatActivity {

    EditText mailedit , passedit , verifypass ;

    Button  registerBtn ;

    String  pass , mail , verifyPass ;
    FirebaseAuth mauth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mauth = FirebaseAuth.getInstance();


        // init views

        mailedit = findViewById(R.id.emaileditTextsignup);
        passedit = findViewById(R.id.paswordeditTextsignup1);
        verifypass = findViewById(R.id.editTextsignup2);
        registerBtn = findViewById(R.id.RegisterBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail = mailedit.getText().toString().trim() ;
                pass = passedit.getText().toString().trim() ;
                verifyPass = verifypass.getText().toString().trim() ;



                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)   ||  TextUtils.isEmpty(verifyPass)     ) // true
                {
                    Toast.makeText(Registration.this , "Fill Up The Data Properly" , Toast.LENGTH_SHORT)
                            .show();
                }

                else if ( !pass.equals(verifyPass))
                {
                    Toast.makeText(Registration.this , "Password Did Not Match" , Toast.LENGTH_SHORT)
                            .show();

                }
                else {
                    //sending the mail and pass to the google server for checking

                    proceedWithSignUP(mail , pass) ;

                }


            }
        });



    }

    private void proceedWithSignUP(String mail, String pass) {


        mauth.createUserWithEmailAndPassword(mail , pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){


                            Intent intent = new Intent(getApplicationContext()  , noteListActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else{


                            Toast.makeText(getApplicationContext() , task.getException()+ " " , Toast.LENGTH_LONG)
                                    .show();
                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext() , e.getMessage()+ " " , Toast.LENGTH_LONG)
                        .show();

            }
        }) ;




    }
}
