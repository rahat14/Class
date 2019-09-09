package com.metacodersbd.noteit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacodersbd.noteit.models.noteModel;
import com.metacodersbd.noteit.signFunction.signInPage;

public class noteListActivity extends AppCompatActivity {

    Button logOutBtn  ;
    FirebaseAuth auth ;
    FloatingActionButton fab ;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref ;
    String uid ;

    FirebaseRecyclerAdapter<noteModel, viewHolderForNotes> firebaseRecyclerAdapter ;
    FirebaseRecyclerOptions<noteModel> options ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth  = FirebaseAuth.getInstance() ;
        uid = auth.getUid() ;

        //initing ref





        //init views

        logOutBtn = findViewById(R.id.logoutBtn);
        fab = findViewById(R.id.fab) ;
        recyclerView = findViewById(R.id.recyclerViewForNoteList);


        //init recyelrview ;


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager) ;
        recyclerView.setHasFixedSize(true);

       // initWelcomeBox();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  picetoDekho = new Intent(getApplicationContext() , TakeNote.class);
                startActivity(picetoDekho);

            }
        });


        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           auth.signOut();

                Intent  picetoDekho = new Intent(getApplicationContext() , signInPage.class);
                    startActivity(picetoDekho);
                    finish();




            }
        });


        loadDataFromFireBase() ;


    }

    private void loadDataFromFireBase() {

        mref = FirebaseDatabase.getInstance().getReference("notes").child(uid);

        options = new FirebaseRecyclerOptions.Builder<noteModel>().setQuery(mref, noteModel.class).build() ;

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<noteModel, viewHolderForNotes>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolderForNotes viewHolderForNotes, int i, @NonNull noteModel noteModel) {


                viewHolderForNotes.setDataToView(getApplicationContext() , noteModel.getTitle() , noteModel.getDesc() , noteModel.getColor_id() ,
                        noteModel.getDate());



            }

            @NonNull
            @Override
            public viewHolderForNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
                final  viewHolderForNotes viewHolderForNotes = new viewHolderForNotes(view);



                return viewHolderForNotes;
            }
        };

       recyclerView.setLayoutManager(linearLayoutManager) ;

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter) ;










    }

    private void initWelcomeBox() {




                        AlertDialog dialog = new AlertDialog.Builder(this)
                                .setIcon(R.drawable.alerticon)
                                .setTitle("Welcome To The Jungle")
                                .setMessage("Hey , Noob !! Whassup ")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();


                                    }
                                }).setCancelable(false)
                                .show() ;






    }
}
