package com.example.restaurantlocatoradmin;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurantlocatoradmin.Model.Restaurant;
import com.example.restaurantlocatoradmin.Pravelent.Prevalent;
import com.example.restaurantlocatoradmin.ViewHolder.RestaurantViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.HashMap;

public class EditRestaurant extends AppCompatActivity {
    private Button applyChangesBtn;
    private EditText editName, editDescription;
    private Spinner editCatagory;

    private ImageView imageView;

    String restaurantId="";
    private DatabaseReference restaurantRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        restaurantId = getIntent().getStringExtra("pid");
        restaurantRef = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(restaurantId);

        applyChangesBtn = (Button) findViewById(R.id.apply_chnages_button);
        editName = (EditText) findViewById(R.id.edit_restaurant_name);
        editCatagory = (Spinner) findViewById(R.id.edit_restaurant_catagory);
        editDescription = (EditText) findViewById(R.id.edit_restaurant_description);
        imageView = (ImageView) findViewById(R.id.restaurant_image);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.RestaurantCatagories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editCatagory.setAdapter(adapter);

        displayRestaurantInfo();
        
        
        applyChangesBtn.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                applyChanges();
                
            }
        });

    }

    private void applyChanges()
    {
        String Rname =  editName.getText().toString();
        String RCategory =  editCatagory.getSelectedItem().toString();
        String RDescription =  editDescription.getText().toString();


        if(Rname.equals(""))
        {
            Toast.makeText(this,"Please Enter Restaurant Name.. ",Toast.LENGTH_SHORT).show();
        }

        else if(RCategory.equals(""))
        {
            Toast.makeText(this,"Please Select Restaurant Category.. ",Toast.LENGTH_SHORT).show();
        }

        else if(RDescription.equals(""))
        {
            Toast.makeText(this,"Please Enter Restaurant Description.. ",Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap<String, Object> restaurantMap = new HashMap<>();
            restaurantMap.put("pid", restaurantId);
            restaurantMap.put("Rname", Rname);
            restaurantMap.put("Rdetails", RDescription);
            restaurantMap.put("Catagory", RCategory);

            restaurantRef.updateChildren(restaurantMap).addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(EditRestaurant.this, "Changes Applied",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditRestaurant.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });

        }


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



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditRestaurant.this,R.array.RestaurantCatagories,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    editName.setText(name);
                    editCatagory.setAdapter(adapter);;
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
