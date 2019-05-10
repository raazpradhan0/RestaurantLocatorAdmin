package com.example.restaurantlocatoradmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputName;
    private  EditText inputPhone;
    private  EditText inputPassword;
    private Button SignUpButton;
    private TextView textViewLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    String name, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        SignUpButton =findViewById(R.id.buttonSignUp);
        inputName = findViewById(R.id.InputName);
        inputPhone = findViewById(R.id.InputPhone);
        inputPassword = findViewById(R.id.InputPassword);
        textViewLogin =  findViewById(R.id.textViewLogin);
        progressDialog = new ProgressDialog(this);


        SignUpButton.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);



    }

    private void registerUser() {
        name = inputName.getText().toString().trim();
        phone = inputPhone.getText().toString().trim();
        password = inputPassword.getText().toString().trim();


        if (name.isEmpty())
        {
            inputName.setError("Name is required");
            inputName.requestFocus();

        }
        if (phone.isEmpty())
        {
            inputPhone.setError("Phone is required");
            inputPhone.requestFocus();

        }


        /*if ()
        {
            inputPhone.setError("Phone Number Must be exactly 10 digits");
            inputPhone.requestFocus();
        }*/


         if (password.isEmpty())
         {
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();

        }

         if (password.length() < 6) {
            inputPassword.setError("Minimum length of password should be 6");
            inputPassword.requestFocus();

        }


         else{
             progressDialog.setMessage("Registering User");
             progressDialog.show();
             ValidatephoneNumber(name, phone, password);
         }
    }






    private void ValidatephoneNumber(final String name, final String phone, final String password)
    {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (name.isEmpty())
                {
                    inputName.setError("Name is required");
                    inputName.requestFocus();
                    progressDialog.dismiss();

                }

                else if (phone.length()!=10)
                {
                    inputPhone.setError("Phone Number must be exactly 10 digits");
                    inputPhone.requestFocus();
                    progressDialog.dismiss();

                }
                else if (!(dataSnapshot.child("Admins").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    RootRef.child("Admins").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (phone.length()!=10)
                                    {
                                        inputPhone.setError("Phone Number must be exactly 10 digits");
                                        inputPhone.requestFocus();
                                        progressDialog.dismiss();

                                    }

                                    else if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignupActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignupActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






    @Override
    public void onClick(View view)
    {
        if(view == SignUpButton )
        {
            registerUser();

        }
        if(view == textViewLogin){
            finish();
            startActivity(new Intent(this,LoginActivity.class));//loginActivity

        }
    }
}
