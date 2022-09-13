package com.example.jeghaderkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    showModalBottomSheet(
    isScrollControlled: true,
    context: context,
    builder: (context) {
        return Container(
            height: MediaQuery.of(context).size.height * 0.5,
        color: Colors.red,
        //height: MediaQuery.of(context).size.height,
        );
    });
}