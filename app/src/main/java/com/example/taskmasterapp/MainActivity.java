package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** Called when the user taps the Add Task button */

        Button addTaskButton = (Button) findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        /** Called when the user taps the All Tasks button */

        Button allTaskButton = (Button) findViewById(R.id.allTasksButton);
        allTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(MainActivity.this, AllTasksActivity.class);
                startActivity(intent);
            }
        });
    }



}