package com.example.coupons;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.NotificationReceiver.;

import com.example.coupons.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, OwnerHome.class);
        intent.putExtra("percentage", "40");
        intent.putExtra("question", "How are you?");
        intent.putExtra("answer", "RAWAN");
        intent.putExtra("coupon", "ME");
        startActivity(intent);

    }


}