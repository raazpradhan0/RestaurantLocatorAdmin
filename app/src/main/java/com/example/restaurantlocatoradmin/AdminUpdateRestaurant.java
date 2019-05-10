package com.example.restaurantlocatoradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantlocatoradmin.Model.Restaurant;
import com.example.restaurantlocatoradmin.Pravelent.Prevalent;
import com.example.restaurantlocatoradmin.ViewHolder.RestaurantViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class AdminUpdateRestaurant extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_restaurant);

                recyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                toolbar =(Toolbar)findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Update Restaurant");
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

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(AdminUpdateRestaurant.this, EditRestaurant.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                                finish();
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_update, parent, false);
                        RestaurantViewHolder holder = new RestaurantViewHolder(view);
                        return holder;
                    }
                };
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }
        }



