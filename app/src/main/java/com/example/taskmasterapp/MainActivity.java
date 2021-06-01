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
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.example.taskmasterapp.Models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    public List<Task> tasksList;
    SharedPreferences sharedPreferences = null;
    private RecyclerAdapter.RecyclerViewClickListener listener;
    private Map<String, String> cognitoUsername;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Task Master");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }

        /** Adding instances to the tasksList */
        tasksList = AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks();

        /** RecyclerView Stuff */
        recyclerView = findViewById(R.id.recyclerView);
        setOnClickListener();
        recyclerAdapter = new RecyclerAdapter(tasksList, listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ////////////////////////////////
//        cognitoUsername = AWSMobileClient.getInstance().getUsername();
        TextView userNameSignedInText = (TextView) findViewById(R.id.userNameSignedInText);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("username", cognitoUsername);
//        editor.apply();
//        userNameSignedInText.setText(sharedPreferences.getString("username", "username"));

        // Add this line, to include the Auth plugin.
        Amplify.Auth.fetchAuthSession(
                result -> {
                    if (!result.isSignedIn()) {
                        // if the user signed in, show him the sign in button
                        Amplify.Auth.signInWithWebUI(
                                this,
                                results -> Log.i("AuthQuickStart", results.toString()),
                                error -> Log.e("AuthQuickStart", error.toString())
                        );

                    } else {
                        Map<String, String> welcome_msg_text= null;
                        try {
                            cognitoUsername = AWSMobileClient.getInstance().getUserAttributes();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (welcome_msg_text.get("name")!=null){
                            userNameSignedInText.setText(welcome_msg_text.get("name"));
                        }else
                        {
                            userNameSignedInText.setText(AWSMobileClient.getInstance().getUsername());

                        }


                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );


        // Get the User attributes
        Amplify.Auth.fetchUserAttributes(
                attributes -> Log.i("AuthDemo", "User attributes = " + attributes.toString()),
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );

        });

    }// end of onCreate

    private void setOnClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("title", tasksList.get(position).getTitle());
                intent.putExtra("body", tasksList.get(position).getBody());
                intent.putExtra("state", tasksList.get(position).getState());
//                intent.putExtra("title",tasksList.get(position).getTitle());
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("Tutorial", "Initialized Amplify");
//        } catch (AmplifyException e) {
//            Log.e("Tutorial", "Could not initialize Amplify", e);
//        }


        /** Adding instances to the tasksList */
//        tasksList =  AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks();
//        tasksList =  new ArrayList<>();
//        Amplify.DataStore.query(Task.class,
//                tasksList ->{
//                    while (tasksList.hasNext()){
//                        Task task = (Task) tasksList.next();
//
//                        if (task.getTitle() != null){
//                            this.tasksList.add(task);
//                        }
//                    }
//                },
//                failure -> Log.e("Tutorial", "Could not query DataStore", failure));

        // ------------- Getting SharedPreferences --------------------
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName", "Welcome");
        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        userNameTextView.setText(userName + "’s tasks");

    }

    /**
     * Called when the user taps the Add Task button
     */
    public void renderAddTaskView(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);

    }

    /**
     * Called when the user taps the All Tasks button
     */
    public void renderAllTaskView(View view) {
        Intent intent = new Intent(this, AllTasksActivity.class);
        startActivity(intent);

    }

    /**
     * Called when the user taps the Settings button
     */
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
        tasksList = AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks();


        /** RecyclerView Stuff */
        recyclerView = findViewById(R.id.recyclerView);
        setOnClickListener();
        recyclerAdapter = new RecyclerAdapter(tasksList, listener);

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