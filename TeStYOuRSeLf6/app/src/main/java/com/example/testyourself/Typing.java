package com.example.testyourself;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Typing extends AppCompatActivity {

    TextView tv_text, tv_result;
    EditText et_text;
    Button b_new;
    String fullStory;
    long startTime, endTime;

    private static final long START_TIME_IN_MILLIS = 100000;

    private  TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;


    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    boolean gameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing);

        long duration = TimeUnit.MINUTES.toMillis(5);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonStartPause = findViewById(R.id.button_start_pause);

        mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

    }
        private void startTimer(){
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    mTimerRunning=false;
                    mButtonStartPause.setText("Start");
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);

                }
            }.start();
            mTimerRunning = true;
            mButtonStartPause.setText("pause");
            mButtonReset.setVisibility(View.INVISIBLE);
        }

        private void pauseTimer()
        {
            mCountDownTimer.cancel();
            mTimerRunning = false;
            mButtonStartPause.setText("Start");
            mButtonReset.setVisibility(View.VISIBLE);
        }

        private void resetTimer()
        {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setVisibility((View.VISIBLE));

        }

        private void updateCountDownText() {
            int minutes = (int) (mTimeLeftInMillis / 2000) / 60;
            int seconds = (int) (mTimeLeftInMillis / 2000) % 60;

            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

            mTextViewCountDown.setText(timeLeftFormatted);


            tv_text = (TextView) findViewById(R.id.tv_text);
            tv_result = (TextView) findViewById(R.id.tv_result);
            et_text = (EditText) findViewById(R.id.et_text);
            b_new = findViewById(R.id.b_new);

            fullStory = tv_text.getText().toString();

            et_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String currentStory = et_text.getText().toString();
                    //Start typing
                    if (currentStory.length() == 1 && !gameStarted) {
                        startTime = System.currentTimeMillis();
                        tv_result.setText("Started");
                        gameStarted = true;

                    }
                    //Finished typing
                    if (currentStory.equals(fullStory)) {
                        endTime = System.currentTimeMillis();

                        //Calculated time
                        long currentTime = (endTime - startTime) / 1000;
                        tv_result.setText("Finished in " + currentTime + "seconds!");

                        et_text.setEnabled(false);
                        et_text.clearFocus();
                    }
                }


                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            b_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_text.setEnabled(true);
                    et_text.setText("");
                    tv_result.setText("");
                    gameStarted = false;
                }
            });



    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}