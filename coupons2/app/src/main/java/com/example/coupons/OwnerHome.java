package com.example.coupons;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OwnerHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerhome);



       Database dbHelper = new Database(OwnerHome.this);
       SQLiteDatabase db = dbHelper.getReadableDatabase();
       Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeQuestion, Database.colChallengeAnswer, Database.colChallengeCoupon,Database.colChallengeCouponPercentage},
               "Owner=?", new String[] {"1"}, null, null, null); //selection


// intent id
if (cursor !=null)
        {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex(Database.colChallengeQuestion));
                @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex(Database.colChallengeAnswer));
                @SuppressLint("Range") String coupon = cursor.getString(cursor.getColumnIndex(Database.colChallengeCoupon));
                @SuppressLint("Range") String percentage = cursor.getString(cursor.getColumnIndex(Database.colChallengeCouponPercentage));
                AddView(question, answer, coupon, percentage);
            }
        }
        cursor.close();



        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addFloating);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerHome.this,AddChallenge.class));
            }
        });
    }



    private void AddView(String question, String answer, String coupon, String percentage) {
        LinearLayout Challengeslayout = (LinearLayout) findViewById(R.id.ChallengesLayout);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.rightMargin=10; layout.leftMargin=10; layout.topMargin=10; layout.bottomMargin=20;
        Challengeslayout.setOrientation(LinearLayout.HORIZONTAL);




        TextView questionTV = new TextView(this);
        questionTV.setText("Question:");
        questionTV.setPadding(10,10,10,10);
        questionTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView questionV = new TextView(this);
        questionV.setText(question);
        questionV.setPadding(10,60,10,10);

        TextView answerTV = new TextView(this);
        answerTV.setText("Answer:");
        answerTV.setPadding(10,160,10,10);
        answerTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView answerV = new TextView(this);
        answerV.setText(answer);
        answerV.setPadding(10,210,10,10);

        TextView couponTV = new TextView(this);
        couponTV.setText("Coupon:");
        couponTV.setPadding(10,300,10,10);
        couponTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView couponV = new TextView(this);
        couponV.setText(coupon);
        couponV.setPadding(10,360,10,10);

        TextView PercTV = new TextView(this);
        PercTV.setText("Percentage:");
        PercTV.setPadding(10,430,10,10);
        PercTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView percV = new TextView(this);
        percV.setText(percentage+"%");
        percV.setPadding(300,430,10,10);


        CardView card = new CardView(this);
        card.setCardBackgroundColor(getResources().getColor(R.color.white));
        card.setRadius(10);
        card.setLayoutParams(layout);
        card.setCardElevation(20);
        card.setContentPadding(20,30,20,30);
        card.addView(questionTV);
        card.addView(questionV);
        card.addView(answerTV);
        card.addView(answerV);
        card.addView(couponTV);
        card.addView(couponV);
        card.addView(PercTV);
        card.addView(percV);
        Challengeslayout.addView(card);

       /* LinearLayout ChallengeLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.rightMargin=20;


        final TextView text = new TextView(this);
        text.setLayoutParams(layout);
        text.setBackground(this.getResources().getDrawable(R.drawable.pink));
        text.setTextColor(this.getResources().getColor(R.color.purple_700)); // purple
        text.setGravity(Gravity.CENTER);
        text.setText(question);
        text.setTextSize(22);
        ChallengeLayout.addView(text);

       layout.setBackground(getDrawable(R.drawable.blue));
        TextView questionView = new TextView(this);
        questionView.setText(question);


        TextView answerView = new TextView(this);
        answerView.setText(answer);

        TextView couponView = new TextView(this);
        couponView.setText(coupon);

        TextView percentageView = new TextView(this);
        percentageView.setText(percentage);

        ChallengeLayout.addView(questionView);
        ChallengeLayout.addView(answerView);
        ChallengeLayout.addView(couponView);
        ChallengeLayout.addView(percentageView);
        Challengeslayout.addView(ChallengeLayout);*/


    }

}
