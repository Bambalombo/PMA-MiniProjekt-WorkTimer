package com.example.pmaworktimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class FixedTimerActivity extends AppCompatActivity {
    // FIXED WORK TIMER - POMODORO TIMER

    private TextView countDownText;
    private TextView statusText;
    private TextView timerName;
    private Button startTimerButton;
    private Button resetTimerButton;

    private CountDownTimer countDownTimer;
    private long newTimerLength = 60000 * 25 + 1000; // 1 minute * 25
    private long timeLeftInMilliseconds;
    private boolean timerIsRunning;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixed_timer);

        timeLeftInMilliseconds = newTimerLength;

        statusText = findViewById(R.id.status_text);
        timerName = findViewById(R.id.timer_name_fixed);
        timerName.setText("Classic Pomodoro Timer");

        countDownText = findViewById(R.id.countdown_text);
        countDownText.setText("25:00");

        startTimerButton = findViewById(R.id.button_start_pause);
        resetTimerButton = findViewById(R.id.button_reset_timer);

        startTimerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (timerIsRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        resetTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
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
        statusText.setText("Working");
    }

    public void pauseTimer() {
        if(countDownTimer!=null) {
            countDownTimer.cancel();
            timerIsRunning = false;
            startTimerButton.setText("Resume Work");
            statusText.setText("Paused");
        }
    }
// I was here
    public void resetTimer() {
        if(countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer.start();
            timerIsRunning = false;
            startTimerButton.setText("Start Work");
            statusText.setText("");

            new Timer().schedule(new TimerTask() { // calls cancel method after 100 ms delay
                @Override
                public void run() { countDownTimer.cancel(); }
            }, 10);
        }
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