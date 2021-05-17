package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button taskOneButt = (Button) findViewById(R.id.task1Button);
    Button taskTwoButt = (Button) findViewById(R.id.task2Button);
    Button taskThirdButt = (Button) findViewById(R.id.task3Button);




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

        /** Called when the user taps the Task1 button */

        taskOneButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                intent.putExtra("titleButton",taskOneButt.getText().toString());
                startActivity(intent);
            }
        });


        /** Called when the user taps the Task2 button */

        taskTwoButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                intent.putExtra("titleButton",taskTwoButt.getText().toString());
                startActivity(intent);
            }
        });

        /** Called when the user taps the Task3 button */

        taskThirdButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                intent.putExtra("titleButton",taskThirdButt.getText().toString());
                startActivity(intent);
            }
        });


        /** Called when the user taps the Settings button */
        Button settingButt = (Button) findViewById(R.id.settingsButton);
        settingButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // ------------- Getting SharedPreferences --------------------
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName", "Welcome");
        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        userNameTextView.setText(userName+"’s tasks");

    }
}