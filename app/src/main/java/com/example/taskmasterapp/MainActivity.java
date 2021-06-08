package com.example.taskmasterapp;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.taskmasterapp.Models.TaskModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.Map;

import static com.example.taskmasterapp.AppDataBase.getInstance;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    public List<TaskModule> tasksList;
    SharedPreferences sharedPreferences = null;
    private RecyclerAdapter.RecyclerViewClickListener listener;
    private Map<String, String> cognitoUsername;
    private Button signOutButton;
    private Button signInButton;
    private TextView userNameSignedInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Task Master");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }


        /** Adding instances to the tasksList */
        tasksList = getInstance(getApplicationContext()).taskDao().getAllTasks();

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
        userNameSignedInText = (TextView) findViewById(R.id.userNameSignedInText);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("username", cognitoUsername);
//        editor.apply();
//        userNameSignedInText.setText(sharedPreferences.getString("username", "username"));

        // Sign In button
        signInButton = (Button) findViewById(R.id.signInButton);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                // Add this line, to include the Auth plugin.
                Amplify.Auth.fetchAuthSession(
                        result -> {
                            if (!result.isSignedIn()) {
                                Amplify.Auth.signInWithWebUI(
                                        this,
                                        results -> Log.i("AuthQuickStart", results.toString()),
                                        error -> Log.e("AuthQuickStart", error.toString())
                                );

                            } else {
                                userNameSignedInText.setText(AWSMobileClient.getInstance().getUsername());

                            }

                        },
                        error -> Log.e("AmplifyQuickstart", error.toString())
                );
//            }
//        });



        // Get the User attributes
        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );

        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Token ..", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FCM Token ..", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }// end of onCreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }
    private void setOnClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("title", tasksList.get(position).getTitle());
                intent.putExtra("body", tasksList.get(position).getBody());
                intent.putExtra("state", tasksList.get(position).getState());
                intent.putExtra("image", tasksList.get(position).getImage());

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
        Amplify.Auth.fetchAuthSession(
                result -> {
                    if (!result.isSignedIn()) {
                        Amplify.Auth.signInWithWebUI(
                                this,
                                results -> Log.i("AuthQuickStart", results.toString()),
                                error -> Log.e("AuthQuickStart", error.toString())
                        );
                    } else {
                        userNameSignedInText.setText(AWSMobileClient.getInstance().getUsername());
                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

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
        tasksList = getInstance(getApplicationContext()).taskDao().getAllTasks();


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
//
//
//[default]
//        aws_access_key_id=ASIA2MDALLPDFDLLJGMX
//        aws_secret_access_key=xj5ypQJsiaH7lxwf51COYy4QUDUUfxx1VZ1ktxjT
//        aws_session_token=IQoJb3JpZ2luX2VjEC4aCXVzLXdlc3QtMiJIMEYCIQD0SwPqOzFLhm4ib+gx9j0vVqs8ggMSfFKh3sLDlgU6AQIhALCfrgvQjZL08kMs5y3pO0PJd4kT4gq1UL3Dmta+XZyCKrsCCNf//////////wEQABoMNzEzMTY2NjQyMTE4IgzUCHB6lSe2OQ/zuUUqjwJyLkoaHoMEx4kczA9fkfBAwgOmkwzx8lgh+4Zeqnd1aaaxfVbM1cSBbvWD+zoc2VUfHRH0K/+QarfSUXWJ2Gz7tYyRoAT9VOLXCO8IGn676kcIzbvcmMopQrtJQZNjRM/oM+ful5j9Y5jTzO5/V4sXwf1Ac6zkVB53/cD21VBrn/P6gVHzB3urDw3NL6IQqFvFAmiVtkGRBrJiB42YqD+NO5ZuMEgCK6ZwlmsiFr0m+bjPlBrEle0F090KnK0FEN9pobBqI/ThhApI1xTN9wDWsZavTOdRDlzukZvpqJ8bvWFkTCcmOZIIPn4s8i5TG7iY/Loa76pxL2hOgMsHAt+P4LUP9djbjQpyXOnh42ajMPKR3oUGOpwBuOYfmbdeRL3WOWr3mqryEIEmODUNlGlgow3ae3/xqCISgUqvbh1+O/GGrmz9vZYEw/pwF1SdjxgmqfggQZI3M441UydLkdDcaDsizTlD6G0Xw6zRBUg/cSPjt6hfgh9yckgYZ72Rv82oG4t8tgvxVEHyEboET063K5nErZoX3d20RvAOr6yqzB49lLxjG8fdVTuTtSviLbonza1v