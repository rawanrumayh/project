package com.example.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coupons.owner.OwnerHome;

public class coffeeOwnerSignUp extends AppCompatActivity {
EditText coffeeName,userName,password,repassword;
Button signUp;
    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_owner_sign_up);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Input
        coffeeName = (EditText) findViewById(R.id.coffeeName);
        userName = (EditText) findViewById(R.id.coffeeUsername);
        password = (EditText) findViewById(R.id.coffeePassword);
        repassword = (EditText) findViewById(R.id.coffeePassword2);
        signUp = (Button) findViewById(R.id.COsignup);
        DB = new Database(this);

        //Listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cName = coffeeName.getText().toString();
                String cUsername = userName.getText().toString();

                String pass = password.getText().toString();
                String rePass = repassword.getText().toString();

                if(cName.isEmpty() || cUsername.isEmpty() || pass.isEmpty() || rePass.isEmpty() ){
                    Toast.makeText(coffeeOwnerSignUp.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else {
                  if(pass.equals(rePass)){
                      Boolean checkUser = DB.checkUsername(cUsername);

                      if(checkUser == false){
                          Boolean insert = DB.register( cUsername, pass, cName,"owner");

                          if(insert == true){
                              Toast.makeText(coffeeOwnerSignUp.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                              Intent i = new Intent(getApplicationContext(), OwnerHome.class);
                              startActivity(i);
                          }
                          else{
                              Toast.makeText(coffeeOwnerSignUp.this,"Registered Failed",Toast.LENGTH_SHORT).show();
                          }
                      }
                      else{
                          Toast.makeText(coffeeOwnerSignUp.this,"User already exists! Please login",Toast.LENGTH_SHORT).show();

                      }
                  }
                  else{
                      Toast.makeText(coffeeOwnerSignUp.this,"Passwords not matching",Toast.LENGTH_SHORT).show();
                  }
                }
            }
        });
    }

}