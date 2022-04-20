package com.example.pmaworktimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FixedTimerActivity extends AppCompatActivity {

    private TextView countDownText;
    private Button startTimerButton;
    private Button switchToFlexButton;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000*25; // 25 minutes
    private boolean timerIsRunning;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixed_timer);

        countDownText = findViewById(R.id.countdown_text);
        startTimerButton = findViewById(R.id.button_start_pause);
        switchToFlexButton = findViewById(R.id.switchToFlexButton);

        startTimerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (timerIsRunning) {
                    resetTimer();
                } else {
                    startTimer();
                }
            }
        });
    }

    public void startPauseTimer(View v) {
        if (timerIsRunning) { resetTimer(); }
        else { startTimer(); }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerIsRunning = true;
        startTimerButton.setText("Pause Work");
    }

    public void resetTimer() {
        countDownTimer.cancel();
        timerIsRunning = false;
        startTimerButton.setText("Start Work");
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = minutes + ":";
        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);
    }

    public void switchToFlexibleTimer(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}