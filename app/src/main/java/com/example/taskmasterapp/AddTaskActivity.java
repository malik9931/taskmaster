package com.example.taskmasterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.State;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Task Master");

//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("Tutorial", "Initialized Amplify in ADD TASK Activity");
//        } catch (AmplifyException e) {
//            Log.e("Tutorial", "Could not initialize Amplify in ADD TASK Activity", e);
//        }

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

                    totalTaskTextView.setText("Total Sorted Task: "+AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks().size());

//                    Task task = new Task(titleTextView.getText().toString(),bodyTextView.getText().toString(),radioButton.getText().toString());
//                    AppDataBase.getInstance(getApplicationContext()).taskDao().addTask(task);
//                    Toast.makeText(AddTaskActivity.this,"U added a new Task",Toast.LENGTH_SHORT).show();

                Task task= Task.builder().title(titleTextView.getText().toString()).body(bodyTextView.getText().toString()).state(State.NEW).build();

                Amplify.DataStore.save(task,
                        success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle()),
                        error -> Log.e("Tutorial", "Could not save item to DataStore", error)
                );
                finish();
            }
        });
    }
}