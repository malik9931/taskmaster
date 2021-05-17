package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Get the Intent that started this activity and extract the string
        String message = getIntent().getStringExtra("titleButton");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.taskTitleTextView);
        textView.setText(message);

    }
}