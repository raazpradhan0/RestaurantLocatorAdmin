package com.example.restaurantlocatoradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantlocatoradmin.Pravelent.Prevalent;

public class AdminHomeActivity extends AppCompatActivity {

    private Button addRestaurantBtn, updateRestaurantBtn, deleteRestaurantBtn, viewCommentsBtn ;
    TextView adminName;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addRestaurantBtn = (Button) findViewById(R.id.addRestaurantButton);
        updateRestaurantBtn = (Button) findViewById(R.id.updatedaRestaurantButton);
        deleteRestaurantBtn = (Button) findViewById(R.id.deleteRestaurantButton);
        viewCommentsBtn =(Button) findViewById(R.id.ViewComments);
        adminName = (TextView) findViewById(R.id.admin_home);

        adminName.setText("Welcome " +Prevalent.currentOnlineUser.getName());

        addRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AdminAddRestaurantActivity.class));


            }
        });

        updateRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AdminUpdateRestaurant.class));


            }
        });

        deleteRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, DeleteRestaurant.class));


            }
        });
        viewCommentsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AdminHomeActivity.this, ViewReviews.class));
            }
        });

        boolean doubleBackToExitPressedOnce = false;

    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Logout) {
            Intent Intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
            Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(Intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}

