package com.example.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class Challenge extends AppCompatActivity {

    private int numberPressed = 0;
    private String question;
    private String answer;
    private String[] letters;
    private int maxPresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        question = getIntent().getStringExtra("question");
        answer = getIntent().getStringExtra("answer");
        maxPresses = answer.length();
        letters = new String[answer.length()];

        for (int i = 0; i < answer.length(); i++)
            letters[i] = answer.charAt(i) + "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        letters = shuffleArray(letters);

        for (String letter : letters) {
            addView((LinearLayout) findViewById(R.id.letters), letter, ((EditText) findViewById(R.id.answerField)));
        }

        TextView questionView = findViewById(R.id.question);
        questionView.setText(question);// correct place?
    }

    private void addView(LinearLayout view, final String letter, final EditText field) {
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.rightMargin = 20;

        final TextView text = new TextView(this);
        text.setLayoutParams(layout);
        text.setBackground(this.getResources().getDrawable(R.drawable.pink));
        text.setTextColor(this.getResources().getColor(R.color.purple_700)); // purple
        text.setGravity(Gravity.CENTER);
        text.setText(letter);
        text.setFocusable(true);
        text.setClickable(true);
        text.setTextSize(32);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberPressed < maxPresses) {
                    if (numberPressed == 0)
                        field.setText("");


                    field.setText(field.getText().toString() + letter);
                    //   text.startAnimation(bigsmallforth);
                    text.animate().alpha(0).setDuration(300);
                    numberPressed++;

                    if (numberPressed == maxPresses)
                        checkAnswer();
                }


            }
        });
        view.addView(text);

    }

    private void checkAnswer() {
        numberPressed = 0;
        EditText field = findViewById(R.id.answerField);
        LinearLayout layout = findViewById(R.id.letters);

        if (field.getText().toString().equals(answer)) {
            Intent intent = new Intent(Challenge.this, Win.class);
            intent.putExtra("coupon", getIntent().getStringExtra("coupon"));
            intent.putExtra("percentage", getIntent().getStringExtra("percentage"));
            startActivity(intent);
            finish();
            // field.setText("");
        } else {
            Toast.makeText(Challenge.this, "Wrong answer, try again", Toast.LENGTH_SHORT).show();
            field.setText("");

        }
        letters = shuffleArray(letters);
        layout.removeAllViews();
        for (String letter : letters) {
            addView(layout, letter, field);
        }
    }

    private String[] shuffleArray(String[] letters) {
        Random random = new Random();
        for (int i = letters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String a = letters[index];
            letters[index] = letters[i];
            letters[i] = a;
        }


        return letters;
    }


}