package com.metacodersbd.noteit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class testActivity extends AppCompatActivity {
    Button okBtn , notOKbtn  ;
     AlertDialog.Builder builder   ;
  AlertDialog dialog ;
  Context context ;
  ProgressBar progressBar ;
  CircleImageView profileView ;
    EditText nameInput ;
    String name  ;
    Uri mFilePathUri  ;
    private Bitmap compressedImageFile ;
    DatabaseReference mref  ;
    StorageReference  mStorageReference  ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mref = FirebaseDatabase.getInstance().getReference("profiledb");
        mStorageReference = FirebaseStorage.getInstance().getReference("ppPicRepo");

        okBtn = findViewById(R.id.ok);
      //  notOKbtn = findViewById(R.id.notOK);
        progressBar = findViewById(R.id.pbar) ;
        progressBar.setVisibility(View.GONE);
        profileView = findViewById(R.id.profile_imageview);
        context = getApplicationContext() ;
        nameInput = findViewById(R.id.nameInput) ;



        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
                {
      if (ContextCompat.checkSelfPermission(testActivity.this , Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED)
      {

          ActivityCompat.requestPermissions(testActivity.this , new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 1);

          BringImagePicker() ;


                            
      }
      else {
          BringImagePicker() ;
      }



                }
                else

                    {

                        BringImagePicker() ;


                    }




            }
        });



        //do magic

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult Result  = CropImage.getActivityResult(data); // URI

            if(resultCode == RESULT_OK)
            {
                mFilePathUri = Result.getUri() ;


                profileView.setImageURI(mFilePathUri);



            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception exception = Result.getError() ;
                Toast.makeText(this  , exception.getMessage(), Toast.LENGTH_SHORT)
                        .show();

            }
            else {

                Toast.makeText(this  , "ERRoR" ,  Toast.LENGTH_SHORT)
                        .show();
            }




        }

        else
        {
            //toast


        }





    }

    private  void  BringImagePicker()
    {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1 )
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(testActivity.this);




    }


    private void startloader()
    {
        progressBar.setVisibility(View.VISIBLE);

        Handler  handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);


            }
        } , 3000 ) ;



    }


    public  void triggerDialogue(){

        builder = new AlertDialog.Builder(testActivity.this);
        builder.setMessage("HEY From Here ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("NOT OK ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.setNeutralButton("CANCEL ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialog.cancel();

            }
        }) ;
        // Create the AlertDialog object and return it
        builder.setCancelable(false) ;
         dialog =   builder.create();
       dialog.show();





    }

    public  void friemissile()
    {
     new AwesomeSuccessDialog(testActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.app_name)
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
                .setCancelable(false)
                .setPositiveButtonText("CANCEL")
                .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setPositiveButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {

                        new AwesomeSuccessDialog(testActivity.this)
                                .hide() ;




                        //click
                    }
                })


                .show();

    }

    public  void customDialogue()
    {
        final Dialog  done = new Dialog(testActivity.this);
        done.requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        done.setContentView(R.layout.dialogueview) ;

        Button okBrn =done.findViewById(R.id.ok_Button) ;

        okBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                done.dismiss();



            }
        });

        done.setCancelable(false);
        done.show();









    }








}
