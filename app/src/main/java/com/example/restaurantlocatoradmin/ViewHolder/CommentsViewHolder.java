package com.example.restaurantlocatoradmin.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.restaurantlocatoradmin.Pravelent.Prevalent;
import com.example.restaurantlocatoradmin.R;


public class CommentsViewHolder extends RecyclerView.ViewHolder
    {
    public TextView txtName,txtComment, txtDate, txtTime  ;



    View mView;
    String currentUserPone;



    public CommentsViewHolder(@NonNull View itemView)
    {
        super(itemView);
        mView = itemView;

        txtName = (TextView) itemView.findViewById(R.id.comment_username);
        txtComment = (TextView) itemView.findViewById(R.id.comment_text);
        txtDate = itemView.findViewById(R.id.comment_date);
        txtTime =(TextView) itemView.findViewById(R.id.comment_time);

        currentUserPone = Prevalent.currentOnlineUser.getPhone();
    }

}
