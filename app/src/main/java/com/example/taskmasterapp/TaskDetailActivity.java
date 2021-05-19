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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Task Master");

//        // Get the Intent that started this activity and extract the string
//        String message = getIntent().getStringExtra("titleButton");

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.taskTitleTextView);
        TextView bodyTextView = findViewById(R.id.taskDescriptionTextView);

        String title = "Title not set";
        String body = "Body not set";

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            title = extras.getString("title");
            body = extras.getString("body");

        }

        titleTextView.setText(title);
        bodyTextView.setText(body);

    }
}