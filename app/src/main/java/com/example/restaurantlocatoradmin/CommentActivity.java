package com.example.restaurantlocatoradmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.restaurantlocatoradmin.Model.Comments;
import com.example.restaurantlocatoradmin.ViewHolder.CommentsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CommentActivity extends AppCompatActivity
{
    private EditText commentInputText;
    private RecyclerView commentsList;
    Toolbar toolbar;

    private DatabaseReference UsersRef, resrurantDatabaseRef;

    private  String Restaurant_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Restaurant_ID = getIntent().getExtras().get("pid").toString();
        resrurantDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(Restaurant_ID).child("Comments");


        commentsList = (RecyclerView) findViewById(R.id.comment_list);
        commentsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentsList.setLayoutManager(linearLayoutManager);
        toolbar =(Toolbar)findViewById(R.id.toolbar_comment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Comments> options =  new FirebaseRecyclerOptions
                .Builder<Comments>()
                .setQuery(resrurantDatabaseRef,Comments.class)
                .build();
        FirebaseRecyclerAdapter<Comments, CommentsViewHolder> adapter = new FirebaseRecyclerAdapter<Comments, CommentsViewHolder>(options)
        {


            @Override
            protected void onBindViewHolder(@NonNull CommentsViewHolder holder, int position, @NonNull Comments model)
            {
                holder.txtName.setText(model.getName());
                holder.txtComment.setText(model.getComment());
                holder.txtDate.setText(model.getDate());
                holder.txtTime.setText(model.getTime());

            }


            @NonNull
            @Override
            public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_layout,viewGroup,false);
                CommentsViewHolder holder = new CommentsViewHolder(view);
                return holder;
            }
        };

        commentsList.setAdapter(adapter);
        adapter.startListening();
    }
}
