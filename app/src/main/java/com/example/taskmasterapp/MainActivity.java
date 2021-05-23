package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.taskmasterapp.Models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<Task> tasksList ;
    private RecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Task Master");


        /** Adding instances to the tasksList */
        tasksList =  AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks();


        /** RecyclerView Stuff */
        recyclerView = findViewById(R.id.recyclerView);
        setOnClickListener();
        recyclerAdapter = new RecyclerAdapter(tasksList,listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void setOnClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("title",tasksList.get(position).getTitle());
                intent.putExtra("body",tasksList.get(position).getBody());
                intent.putExtra("state",tasksList.get(position).getState());
//                intent.putExtra("title",tasksList.get(position).getTitle());
                startActivity(intent);
            }
        };
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

    /** Called when the user taps the Add Task button */
    public void renderAddTaskView(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);

    }

    /** Called when the user taps the All Tasks button */
    public void renderAllTaskView(View view) {
        Intent intent = new Intent(this, AllTasksActivity.class);
        startActivity(intent);

    }
    /** Called when the user taps the Settings button */
    public void renderSettingView(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("---------------------", "onStart():  ------------------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("---------------------", "onPause():  ------------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("---------------------", "onDestroy():  ------------------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("---------------------", "onRestart():  ------------------");
        /** Adding instances to the tasksList */
        tasksList =  AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks();


        /** RecyclerView Stuff */
        recyclerView = findViewById(R.id.recyclerView);
        setOnClickListener();
        recyclerAdapter = new RecyclerAdapter(tasksList,listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("---------------------", "onStop():  ------------------");
    }
}