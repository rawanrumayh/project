package com.example.coupons.user;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coupons.R;
import com.example.coupons.SignUP_LogIn;

public class userSettings extends AppCompatActivity {
   Button logout;
   Switch switchMode;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        //Action bar title
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView Title = (TextView) view.findViewById(R.id.actionbar_title);
        Title.setText("Settings");
        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
        Title.setTextColor(Color.parseColor(color));

        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        logout = (Button) findViewById(R.id.uLogoutBtn);
        switchMode = (Switch) findViewById(R.id.uSwitchMode);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        //Listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(userSettings.this,"Logged out Successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), SignUP_LogIn.class);
                startActivity(i);
            }
        });

        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                   audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                   Toast.makeText(userSettings.this, "Silent Mode is on", Toast.LENGTH_SHORT).show();
               }
               else{
                   AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                   audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                   Toast.makeText(userSettings.this, "Silent Mode is off", Toast.LENGTH_SHORT).show();
               }
            }
        });


    }
}