package com.example.pmaworktimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Linus";
    private Chronometer chronometer;

    public long pauseVariable = 25;
    private long timeSincePaused;
    private long timerStart;
    private long timeSpentWorking;
    private long baseValue;

    private boolean isRunning;  // whether or not the timer is running

    Button startPauseButton;
    Button switchToFixedTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        startPauseButton = findViewById(R.id.button_start_pause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startChronometer(View v) {
        if (!isRunning) {
            chronometer.setCountDown(false);
            chronometer.setBase(SystemClock.elapsedRealtime() - timeSincePaused);
            chronometer.start();
            timerStart = chronometer.getBase();
            Log.d(TAG, "startChronometer: "+timerStart);
            isRunning = true;
            startPauseButton.setText("Start Break");
        } else {
            Log.d(TAG, "startChronometer: "+ (SystemClock.elapsedRealtime() - chronometer.getBase()));
            chronometer.setCountDown(true);

            baseValue = (SystemClock.elapsedRealtime()-(SystemClock.elapsedRealtime() - chronometer.getBase())*-1);
            timeSpentWorking = SystemClock.elapsedRealtime() - chronometer.getBase();

            chronometer.setBase(baseValue-(timeSpentWorking*(100-pauseVariable))/100);
            isRunning = false;
            startPauseButton.setText("Start Work");
            timeSincePaused = 0;
        }
    }

    public void pauseChronometer(View v) {
        if (isRunning) {
            timeSincePaused = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();
            isRunning = false;
            startPauseButton.setText("Resume Work");
        }
    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        timeSincePaused = 0;
        isRunning = false;
        startPauseButton.setText("Start Work");
    }

    public void switchToFixedTimer(View view) {
        Intent intent = new Intent(this, FixedTimerActivity.class);
        startActivity(intent);
    }
}