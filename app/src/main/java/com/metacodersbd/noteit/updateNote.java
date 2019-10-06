package com.metacodersbd.noteit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class updateNote extends AppCompatActivity {


    EditText  titleEdit , descEIDT  ;
    ProgressBar pbar ;
    Button  update , delete ;

    String titlee , descc  , uid   , postID , colorID ;

    DatabaseReference mref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        Intent o = getIntent();
        // init view
        titleEdit = findViewById(R.id.title);
        descEIDT = findViewById(R.id.descEdit);
        pbar = findViewById(R.id.pbar);
        update = findViewById(R.id.updateBtn);
        delete = findViewById(R.id.delBtn);
        uid = FirebaseAuth.getInstance().getUid() ;

        mref = FirebaseDatabase.getInstance().getReference("notes").child(uid);


        // init the pbar to hidden state

        pbar.setVisibility(View.GONE);


        titleEdit.setText(o.getStringExtra("TITLE"));
        descEIDT.setText(o.getStringExtra("DESC"));

        postID = o.getStringExtra("ID") ;

        colorID = o.getStringExtra("COLORID") ;

        //  listening for the click

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implment Update
                pbar.setVisibility(View.VISIBLE);

                titlee = titleEdit.getText().toString();
                descc = descEIDT.getText().toString() ;



                if(TextUtils.isEmpty(titlee) || TextUtils.isEmpty(descc))
                {


                    pbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext() , "Error : Please Change the Value ", Toast.LENGTH_LONG)
                            .show();


                }
                else
                    {
                        // updating  the data

                        HashMap map =  new HashMap();

                        map.put("title" , titlee ) ;
                        map.put("desc" ,descc ) ;

                        mref.child(postID).updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        finish();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {


                          Toast.makeText(getApplicationContext() , "Error : "+ e.getMessage(), Toast.LENGTH_LONG)
                                                .show();

                                    }
                                }) ;




                }




            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implement DEL

                pbar.setVisibility(View.VISIBLE);

                mref.child(postID).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext() , "Error : "+ e.getMessage(), Toast.LENGTH_LONG)
                        .show();


                    }
                }) ;





            }
        });



    }
}
