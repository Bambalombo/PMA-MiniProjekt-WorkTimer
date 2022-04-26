package com.example.pmaworktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopupWindowActivity extends AppCompatActivity {

    private Button resumeButton;
    private Button breakButton;
    private TextView windowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.6));

        windowText = findViewById(R.id.popup_text);
        windowText.setText("You seem to have lifted your phone.\n" +
                            "Do you wish to ...");

        resumeButton = findViewById(R.id.popup_resume);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(0,intent);
                finish();
            }
        });

        breakButton = findViewById(R.id.popup_break);
        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                setResult(1,intent);
                finish();
            }
        });
    }
}