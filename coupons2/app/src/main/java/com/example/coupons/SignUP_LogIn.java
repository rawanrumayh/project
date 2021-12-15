package com.example.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SignUP_LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_uptype);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
    public void toLogIn(View view){
        Intent i = new Intent(this,logIn.class);
        startActivity(i);
    }
    public void toUserType(View view){
        Intent i = new Intent(this,UserType.class);
        startActivity(i);
    }

}