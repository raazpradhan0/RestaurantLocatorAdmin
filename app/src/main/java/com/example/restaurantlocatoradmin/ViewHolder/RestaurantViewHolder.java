package com.example.restaurantlocatoradmin.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantlocatoradmin.Interface.ItemClickListner;
import com.example.restaurantlocatoradmin.Pravelent.Prevalent;
import com.example.restaurantlocatoradmin.R;
import com.example.restaurantlocatoradmin.Interface.ItemClickListner;
import com.example.restaurantlocatoradmin.Pravelent.Prevalent;
import com.example.restaurantlocatoradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public TextView txtRestauranttName, txtRestaurantDescription, txtRestaurantCatagory, commentViewText;
    public EditText editName, editCatagory, editDescription;
    public ImageView imageView;
    public ImageButton commentViewButton;

    View mView;

    String currentUserPone;



    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        imageView = (ImageView) itemView.findViewById(R.id.restaurant_image);
        txtRestauranttName = (TextView) itemView.findViewById(R.id.restaurant_name);
        txtRestaurantDescription = (TextView) itemView.findViewById(R.id.restaurant_description);
        txtRestaurantCatagory = itemView.findViewById(R.id.restaurant_catagory);

        commentViewButton =(ImageButton) itemView.findViewById(R.id.comment_btn);
        commentViewText =(TextView) itemView.findViewById(R.id.viewCommentTxt);

        editName = itemView.findViewById(R.id.edit_restaurant_name);
        editCatagory = itemView.findViewById(R.id.edit_restaurant_catagory);
        editDescription = itemView.findViewById(R.id.edit_restaurant_description);


        currentUserPone = Prevalent.currentOnlineUser.getPhone();
    }

}





