package com.example.coupons.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;

public class Win extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        //Action bar title
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView Title = (TextView) view.findViewById(R.id.actionbar_title);
        Title.setText("Coupon");
        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
        Title.setTextColor(Color.parseColor(color));

        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title

        TextView text = findViewById(R.id.couponField);
        text.setText(getIntent().getStringExtra("coupon"));

        TextView coupontitletext = findViewById(R.id.couponTitle);
        coupontitletext.setText("Your " + getIntent().getStringExtra("percentage") + "% coupon by "+ getIntent().getStringExtra("owner") );

        Button thanks = (Button) findViewById(R.id.thanks);
        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseClass.played.add(getIntent().getStringExtra("id"));
                finish();
                startActivity(new Intent(Win.this, UserHome.class)); //change it
            }
        });


    }
}