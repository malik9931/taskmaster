package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;

import java.io.File;

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
        TextView stateTextView = findViewById(R.id.taskStatusTextView);
        ImageView taskImageView = findViewById(R.id.taskImageView);
        TextView taskLocationTextView = findViewById(R.id.locationTextView);

        String title = "Title not set";
        String body = "Body not set";
        String state = "state not set";
        String image = "image not set";
        String address = "image not set";



        Bundle extras = getIntent().getExtras();
        if (extras != null){
            title = extras.getString("title");
            body = extras.getString("body");
            state = extras.getString("state");
            image = extras.getString("image");
            address = extras.getString("address");


        }


        titleTextView.setText(title);
        bodyTextView.setText(body);
        stateTextView.setText(state);
        taskLocationTextView.setText(getIntent().getStringExtra("address"));

        // To Download the file we use the Glide Library
        Glide.with(this)
                .load("https://taskmasterbucket12923-dev.s3.amazonaws.com/public/"+image)
                .into(taskImageView);

    }
}