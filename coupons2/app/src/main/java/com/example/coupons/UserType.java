package com.example.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coupons.owner.coffeeOwnerSignUp;
import com.example.coupons.user.userSignUp;

public class UserType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void toCoffeeOwner(View view){
        Intent i = new Intent(this, coffeeOwnerSignUp.class);
        startActivity(i);
    }

    public void toUser(View view){
        Intent i = new Intent(this, userSignUp.class);
        startActivity(i);
    }
}