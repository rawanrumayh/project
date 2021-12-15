package com.example.coupons;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

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

        //Center App title
//        AppCompatTextView mTitleTextView = new AppCompatTextView(getApplicationContext());
//        mTitleTextView.setSingleLine();
//        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        actionBar.setCustomView(mTitleTextView, layoutParams);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
//        mTitleTextView.setText("User Type");
//        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
//        mTitleTextView.setTextColor(Color.parseColor(color));
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