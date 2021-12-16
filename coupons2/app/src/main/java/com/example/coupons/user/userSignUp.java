package com.example.coupons.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coupons.Database;
import com.example.coupons.R;

public class userSignUp extends AppCompatActivity {
    EditText name,userName,password,repassword;
    Button signUp;
    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Action bar title
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView Title = (TextView) view.findViewById(R.id.actionbar_title);
        Title.setText("Sign Up");
        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
        Title.setTextColor(Color.parseColor(color));

        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title



        //Input
        name = (EditText) findViewById(R.id.userFullName);
        userName = (EditText) findViewById(R.id.userUsername);
        password = (EditText) findViewById(R.id.userPassword);
        repassword = (EditText) findViewById(R.id.userPassword2);
        signUp = (Button) findViewById(R.id.userSignUp);
        DB = new Database(this);

        //Listeners
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = name.getText().toString();
                String uUsername = userName.getText().toString();

                String pass = password.getText().toString();
                String rePass = repassword.getText().toString();

                if(uName.isEmpty() || uUsername.isEmpty() || pass.isEmpty() || rePass.isEmpty() ){
                    Toast.makeText(userSignUp.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(pass.equals(rePass)){
                        Boolean checkUser = false;

                        if(checkUser == false){
                            Boolean insert = DB.register( uUsername, pass, uName,"user");

                            if(insert == true){
                                Toast.makeText(userSignUp.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), UserHome.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(userSignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(userSignUp.this,"User already exists! Please login",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(userSignUp.this,"Passwords not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}