package com.metacodersbd.noteit;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolderForNotes extends RecyclerView.ViewHolder {

        View mview  ;
        TextView title , desc , dateView ;




    public viewHolderForNotes(@NonNull View itemView) {
        super(itemView);

        mview = itemView ;

        /// item click

    }

    public  void  setDataToView(Context context , String  titlee , String description , String  color_id  , String date   )
    {

        // inti views
        title = mview.findViewById(R.id.note_titleTV) ;
        desc = mview.findViewById(R.id.descTV) ;
        dateView = mview.findViewById(R.id.dateTv);


        //set the data

        title.setText(titlee);
        desc.setText(description);
        dateView.setText(date);


    }





}
