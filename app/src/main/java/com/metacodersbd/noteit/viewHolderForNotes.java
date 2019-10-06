package com.metacodersbd.noteit;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolderForNotes extends RecyclerView.ViewHolder {

        View mview  ;
        TextView title , desc , dateView ;
        CardView card ;




    public viewHolderForNotes(@NonNull View itemView) {
        super(itemView);

        mview = itemView ;

        /// item click

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mClickListener.onItemClick(view , getAdapterPosition());
            }
        });

    }

    public  void  setDataToView(Context context , String  titlee , String description , String  color_id  , String date   )
    {

        // inti views
        title = mview.findViewById(R.id.note_titleTV) ;
        desc = mview.findViewById(R.id.descTV) ;
        card = mview.findViewById(R.id.cardView) ;

        dateView = mview.findViewById(R.id.dateTv);


        //set the data

        title.setText(titlee);
        desc.setText(description);
        dateView.setText(date);

        if(color_id.equals("null"))
        {
           color_id = "-10603087" ;


        }

            card.setCardBackgroundColor(Integer.parseInt(color_id) );





    }

    private  static  viewHolderForNotes.ClickListener  mClickListener;


    public  interface  ClickListener
    {
                void onItemClick( View view , int position ) ;

    }


    public  static  void setOnClickListener(viewHolderForNotes.ClickListener clickListener)
    {
                    
                mClickListener = clickListener ;
    }

}
