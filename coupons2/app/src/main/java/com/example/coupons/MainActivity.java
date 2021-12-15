package com.example.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.coupons.owner.OwnerHome;
import com.example.coupons.user.UserHome;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent intent = new Intent(MainActivity.this, OwnerHome.class);
            intent.putExtra("percentage", "40");
            intent.putExtra("question", "How are you?");
            intent.putExtra("answer", "RAWAN");
            intent.putExtra("coupon", "ME");
            startActivity(intent);
        } catch (Exception e){
            Log.e("MAIN", "ERROR" + e.toString());
        }

    }


}