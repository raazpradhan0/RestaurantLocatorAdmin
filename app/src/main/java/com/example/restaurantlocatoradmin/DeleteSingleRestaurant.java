package com.example.restaurantlocatoradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DeleteSingleRestaurant extends AppCompatActivity {
    private Button deleteRestaurantButn;
    private TextView editName, editCatagory, editDescription;


    private ImageView imageView;

    String restaurantId="";
    private DatabaseReference restaurantRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_single_restaurant);

        restaurantId = getIntent().getStringExtra("pid");
        restaurantRef = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(restaurantId);

        deleteRestaurantButn = (Button) findViewById(R.id.deleteRestaurantButn);
        editName = (TextView) findViewById(R.id.edit_restaurant_name);
        editCatagory = (TextView) findViewById(R.id.edit_restaurant_catagory);
        editDescription = (TextView) findViewById(R.id.edit_restaurant_description);
        imageView = (ImageView) findViewById(R.id.restaurant_image);

        displayRestaurantInfo();


        deleteRestaurantButn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteRestaurant();
                
            }
        });

    }

    private void deleteRestaurant()
    {
        restaurantRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(DeleteSingleRestaurant.this,"The Restaurant Has Been Deleted Sucessfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteSingleRestaurant.this, DeleteRestaurant.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void displayRestaurantInfo()
    {
        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String name = dataSnapshot.child("Rname").getValue().toString();
                    String category = dataSnapshot.child("Catagory").getValue().toString();
                    String details = dataSnapshot.child("Rdetails").getValue().toString();
                    String image = dataSnapshot.child("Image").getValue().toString();


                    editName.setText(name);
                    editCatagory.setText(category);;
                    editDescription.setText(details);
                    Picasso.get().load(image).into(imageView);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }


}
