package com.example.coupons.owner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coupons.Database;
import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;

public class AddChallenge extends AppCompatActivity {
    String question = "gk", Answer = "kc", coupon = "fkgo";
    String percentage = "0gg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addchallenge);
        EditText questionField = (EditText) findViewById(R.id.EnterQuestionField);
        EditText answerField = (EditText) findViewById(R.id.EnterAnswerField);
        EditText couponField = (EditText) findViewById(R.id.EnterCouponField);
        EditText percentageField = (EditText) findViewById(R.id.EnterDiscountField);

        Button add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question = questionField.getText().toString();
                Answer = answerField.getText().toString();
                coupon = couponField.getText().toString();
                percentage = percentageField.getText().toString();

                if (question.length() == 0) {
                    questionField.requestFocus();
                    questionField.setError("FIELD CANNOT BE EMPTY");
                } else if (!question.trim().matches("[a-zA-Z \\, \\?]+")) {
                    questionField.requestFocus();
                    questionField.setError("ENTER ONLY ALPHABETICAL CHARACTERS");
                } else if (Answer.trim().length() == 0) {
                    answerField.requestFocus();
                    answerField.setError("FIELD CANNOT BE EMPTY");
                } else if (!Answer.trim().matches("^[a-zA-Z0-9]+$")) {
                    answerField.requestFocus();
                    answerField.setError("ENTER ONLY ALPHANUMERICAL CHARACTERS");
                } else if (Answer.trim().length() > 5) {
                    answerField.requestFocus();
                    answerField.setError("ANSWER SHOULDN'T EXCEED 5 LETTERS");
                } else if (coupon.trim().length() == 0) {
                    couponField.requestFocus();
                    couponField.setError("FIELD CANNOT BE EMPTY");
                } else if (coupon.trim().length() > 8) {
                    couponField.requestFocus();
                    couponField.setError("COUPON SHOULDN'T EXCEED 7 LETTERS");
                } else if (percentage.trim().length() == 0) {
                    percentageField.requestFocus();
                    percentageField.setError("FIELD CANNOT BE EMPTY");
                } else if (!percentage.trim().matches("^[1-9][0-9]?$|^100$")) {
                    percentageField.requestFocus();
                    percentageField.setError("ENTER ONLY NUMBERS BETWEEN 1 AND 100");
                } else {
                    Database databasehelper = new Database(AddChallenge.this);
                    SQLiteDatabase db = databasehelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
//                    Toast.makeText(AddChallenge.this, ""+BaseClass.my_lat+"22"+BaseClass.my_lng, Toast.LENGTH_SHORT).show();

                    values.put(Database.colChallengeQuestion, question);
                    values.put(Database.colChallengeAnswer, Answer);
                    values.put(Database.colChallengeCoupon, coupon);
                    values.put(Database.colChallengeCouponPercentage, percentage);
                    values.put(Database.colOwnerID, 1);
                    values.put(Database.colOwnerLng, BaseClass.my_lng);
                    values.put(Database.colOwnerLat, BaseClass.my_lat);


                    long rowID = db.insert(Database.ChallengesTable, null, values);
                    finish();
                    startActivity(new Intent(AddChallenge.this, OwnerHome.class));
                    if (rowID == -1)
                        Toast.makeText(AddChallenge.this, "WWWHHHY", Toast.LENGTH_SHORT).show();

                    if (rowID != -1)
                        Toast.makeText(AddChallenge.this, "Challenge added successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
