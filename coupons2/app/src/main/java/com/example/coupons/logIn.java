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
import com.example.coupons.user.UserHome;

public class logIn extends AppCompatActivity {
    EditText username,password;
    Button Login;
    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Input
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.login);
        DB = new Database(this);

        //Listeners
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = username.getText().toString();
                String pass = password.getText().toString();

                if(UserName.isEmpty() || pass.isEmpty()){
                    Toast.makeText(logIn.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean login= DB.checkUsernamePassword(UserName,pass);

                    if(login == true){
                        Toast.makeText(logIn.this,"log in successful",Toast.LENGTH_SHORT).show();

                        String type = DB.getUserType(UserName);

                        if(type.equals("owner")){
                            Intent i = new Intent(getApplicationContext(), OwnerHome.class);
                            startActivity(i);
                        }
                        else if (type.equals("user")) {
                            Intent i = new Intent(getApplicationContext(), UserHome.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(logIn.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(logIn.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}