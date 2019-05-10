package com.example.restaurantlocatoradmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantlocatoradmin.Model.Restaurant;
import com.example.restaurantlocatoradmin.Pravelent.Prevalent;
import com.example.restaurantlocatoradmin.ViewHolder.RestaurantViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ViewReviews extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    String restaurantId="";
    private DatabaseReference restaurantRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        toolbar =(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }


    @Override
    public void onStart() {
        super.onStart();
        Query query = FirebaseDatabase.getInstance().getReference().child("Restaurants")
                .orderByChild("adminID")
                .equalTo(Prevalent.currentOnlineUser.getPhone());

        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions
                .Builder<Restaurant>()
                .setQuery(query, Restaurant.class)
                .build();
        FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder> adapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
            @NonNull

            @Override
            protected void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position, @NonNull final Restaurant model) {
                holder.txtRestauranttName.setText(model.getRname());
                holder.txtRestaurantDescription.setText(model.getRdetails());
                holder.txtRestaurantCatagory.setText(model.getCatagory());
                Picasso.get().load(model.getImage()).into(holder.imageView);

               holder.commentViewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentIntent = new Intent(ViewReviews.this, CommentActivity.class);
                        commentIntent.putExtra("pid", model.getPid());
                        startActivity(commentIntent);
                    }
                });

                holder.commentViewText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentIntent = new Intent(ViewReviews.this, CommentActivity.class);
                        commentIntent.putExtra("pid", model.getPid());
                        startActivity(commentIntent);
                    }
                });


            }

            @NonNull
            @Override
            public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_viewing_comments, parent, false);
                RestaurantViewHolder holder = new RestaurantViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}