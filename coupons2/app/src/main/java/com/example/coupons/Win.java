package com.example.coupons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Win extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        TextView text = findViewById(R.id.couponField);
        text.setText(getIntent().getStringExtra("coupon"));

        TextView coupontitletext = findViewById(R.id.couponTitle);
        coupontitletext.setText("Your " + getIntent().getStringExtra("percentage") + "% coupon");

        Button thanks = (Button) findViewById(R.id.thanks);
        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Win.this, UserHome.class)); //change it
            }
        });


    }
}