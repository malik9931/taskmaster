package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmasterapp.Models.Task;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Task Master");

        // Get the Intent that started this activity
        Intent intent = getIntent();
        TextView totalTaskTextView = (TextView) findViewById(R.id.totalTaskTextView);
        totalTaskTextView.setText("Total Sorted Task: "+AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks().size());

        Button button = (Button) findViewById(R.id.addTaskInnerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titleTextView = (TextView) findViewById(R.id.addedTitleTextView);
                TextView bodyTextView = (TextView) findViewById(R.id.addedBodyTextView);
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);


                if (titleTextView.getText() == "" ||bodyTextView.getText() == null ){
                    Toast.makeText(AddTaskActivity.this,"some thing",Toast.LENGTH_SHORT).show();
                }else {
                    totalTaskTextView.setText("Total Sorted Task: "+AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks().size());

                    Task task = new Task(titleTextView.getText().toString(),bodyTextView.getText().toString(),radioButton.getText().toString());
                    AppDataBase.getInstance(getApplicationContext()).taskDao().addTask(task);
                    Toast.makeText(AddTaskActivity.this,"U added a new Task",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}