package com.example.pmaworktimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // FLEXIBLE WORK TIMER

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private SensorEventListener gyroscopeEventListener;

    private Chronometer chronometer;

    public long pauseVariable = 25;
    private long timeSincePaused;
    private long timeSpentWorking;
    private long baseValue;

    private boolean isRunning;  // whether or not the timer is running

    private Button startPauseButton;
    private TextView timerName;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        chronometer = findViewById(R.id.chronometer);
        startPauseButton = findViewById(R.id.button_start_pause);
        timerName = findViewById(R.id.timer_name_flex);
        timerName.setText("Flexible Work Timer");

        statusText = findViewById(R.id.status_text_flextimer);
        statusText.setText("");

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long currentTime = chronometer.getBase()-SystemClock.elapsedRealtime();
                if(chronometer.isCountDown() && currentTime < 0) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();
                    timeSincePaused = 0;
                    isRunning = false;
                    startPauseButton.setText("Start Work");
                    statusText.setText("");
                }
            }
        });

        if (gyroscope == null) {
            Toast.makeText(this, "This device has no gyroscope!",Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0] > 0.1) {
                    if (isRunning) {
                        Log.d("Linus","Den driller");
                        Intent intent = new Intent(MainActivity.this, PopupWindowActivity.class);
                        startActivityForResult(intent,1);
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1)
            startChronometer(chronometer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startChronometer(View v) {
        if (!isRunning) {
            chronometer.setCountDown(false);
            startTimer();
        } else {
            chronometer.setCountDown(true);
            pauseChronometer();
        }
    }

    private void startTimer() {
        chronometer.setBase(SystemClock.elapsedRealtime() - timeSincePaused);
        chronometer.start();
        isRunning = true;
        startPauseButton.setText("Start Break");
        statusText.setText("Working");
    }

    public void pauseChronometer() {
        baseValue = (SystemClock.elapsedRealtime()-(SystemClock.elapsedRealtime() - chronometer.getBase())*-1);
        timeSpentWorking = SystemClock.elapsedRealtime() - chronometer.getBase();

        chronometer.setBase(baseValue-(timeSpentWorking*(100-pauseVariable))/100+1000);
        isRunning = false;
        startPauseButton.setText("Skip Break");
        timeSincePaused = 0;
        statusText.setText("Break in progress");
    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        timeSincePaused = 0;
        isRunning = false;
        startPauseButton.setText("Start Work");
        statusText.setText("");
    }

    public void switchToFixedTimer(View view) {
        Intent intent = new Intent(this, FixedTimerActivity.class);
        startActivity(intent);
    }
}