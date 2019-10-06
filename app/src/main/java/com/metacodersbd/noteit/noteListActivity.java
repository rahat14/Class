package com.metacodersbd.noteit;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView ;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.metacodersbd.noteit.models.noteModel;
import com.metacodersbd.noteit.signFunction.signInPage;

public class noteListActivity extends AppCompatActivity {

    Button logOutBtn  ;
    FirebaseAuth auth ;
    FloatingActionButton fab ;
    RecyclerView recyclerView ;
    GridLayoutManager gridLayoutManager ;
    ProgressBar progressBar ;
    TextView textView ;

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
        textView = findViewById(R.id.novalueText) ;
        logOutBtn = findViewById(R.id.logoutBtn);
        fab = findViewById(R.id.fab) ;
        recyclerView = findViewById(R.id.recyclerViewForNoteList);
        progressBar = findViewById(R.id.pbar);


        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        //TODO 4. Finalizing / testing



        //init recyelrview ;


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager);
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(gridLayoutManager.getItemCount()== 0)
                {


                    textView.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);

            }
        }, 3000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


       // return super.onCreateOptionsMenu(menu);

        // inflate the menu bar  ; this adds items to the action bar
        getMenuInflater().inflate(R.menu.serach_bar , menu );

        MenuItem item = menu.findItem(R.id.action_search);

        SearchView  searchView = (SearchView) MenuItemCompat.getActionView(item) ;


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                    firebaseSearch(s) ;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                firebaseSearch(s) ;
                return false;
            }
        });






            return  super.onCreateOptionsMenu(menu);
    }

    private void loadDataFromFireBase() {

        mref = FirebaseDatabase.getInstance().getReference("notes").child(uid);

        options = new FirebaseRecyclerOptions.Builder<noteModel>().setQuery(mref, noteModel.class).build() ;

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<noteModel, viewHolderForNotes>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolderForNotes viewHolderForNotes, final int i, @NonNull noteModel noteModel) {


                viewHolderForNotes.setDataToView(getApplicationContext() , noteModel.getTitle() , noteModel.getDesc() , noteModel.getColor_id() ,
                        noteModel.getDate());




            }

            @NonNull
            @Override
            public viewHolderForNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {

                View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
                final  viewHolderForNotes viewHolderForNotes = new viewHolderForNotes(view);




                viewHolderForNotes.setOnClickListener(new viewHolderForNotes.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {



        // sending user to the edit activity

                        Intent i =  new Intent(getApplicationContext()  , updateNote.class);

                        i.putExtra("TITLE" , getItem(position).getTitle() ) ;
                        i.putExtra("DESC" , getItem( position).getDesc() ) ;
                        i.putExtra("COLORID" , getItem(position).getColor_id() ) ;
                        i.putExtra("ID" , getItem(position).getId() ) ;


                        startActivity(i);




                    }
                });




                return viewHolderForNotes;
            }
        };

       recyclerView.setLayoutManager(gridLayoutManager) ;

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter) ;










    }

    private  void firebaseSearch(String searchText)

    {

        mref = FirebaseDatabase.getInstance().getReference("notes").child(uid);

        Query firbaseQuery = mref.orderByChild("title").startAt(searchText
                .toLowerCase())
                .endAt(searchText.toLowerCase() + "\uf8ff") ;

        options = new FirebaseRecyclerOptions.Builder<noteModel>().setQuery(firbaseQuery, noteModel.class).build() ;

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

        recyclerView.setLayoutManager(new GridLayoutManager(this , 2 )) ;

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

