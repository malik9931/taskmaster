package com.example.taskmasterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.State;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.taskmasterapp.Models.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class AddTaskActivity extends AppCompatActivity {
    private Button pickImageButton;
    private TextView titleTextView;
    private String uploadedFileName= "ss";

    @SuppressLint("SetTextI18n")
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

        pickImageButton =(Button) findViewById(R.id.pickImageButton);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileFromMobileStorage();
            }
        });
        titleTextView = (TextView) findViewById(R.id.addedTitleTextView);
        Button button = (Button) findViewById(R.id.addTaskInnerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                TextView bodyTextView = (TextView) findViewById(R.id.addedBodyTextView);
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);

                    totalTaskTextView.setText("Total Sorted Task: "+AppDataBase.getInstance(getApplicationContext()).taskDao().getAllTasks().size());

//                    Toast.makeText(AddTaskActivity.this,"U added a new Task",Toast.LENGTH_SHORT).show();

//                Task task= Task.builder().title(titleTextView.getText().toString()).body(bodyTextView.getText().toString()).state(State.NEW).build();
                com.example.taskmasterapp.Models.Task task = new Task(titleTextView.getText().toString(),bodyTextView.getText().toString(),radioButton.getText().toString(),uploadedFileName);
                AppDataBase.getInstance(getApplicationContext()).taskDao().addTask(task);
                // upload an image to the S3 AWS
//                uploadFile(titleTextView.getText().toString());
//                Amplify.DataStore.save(task,
//                        success -> Log.i("Tutorial", "Saved item: " + success.item().getTitle()),
//                        error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//                );
                finish();
            }
        });
    }
//    private void uploadInputStream() {
//        InputStream exampleInputStream = getContentResolver().openInputStream(uri);
//
//        Amplify.Storage.uploadInputStream(
//                "ExampleKey",
//                exampleInputStream,
//                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
//                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
//        );
//    }

    public void getFileFromMobileStorage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,9999);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
//            Amplify.Auth.handleWebUISignInResponse(data);
//        }

        // To get the file Name
        Uri returnUri = data.getData();
        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameIndex);
        uploadedFileName = fileName;
        if (requestCode == 9999){
            File file = new File(getApplicationContext().getFilesDir(),fileName);

                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileUtils.copy(inputStream,new FileOutputStream(file));
                    uploadFile(file, fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
    private void uploadFile(File file,String fileName) {
        Log.d("TAG--", "uploadFile: "+ fileName);
//        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey");
        Amplify.Storage.uploadFile(
                fileName.toString(),
                file,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }
}