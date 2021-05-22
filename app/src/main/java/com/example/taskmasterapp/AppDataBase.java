package com.example.taskmasterapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.taskmasterapp.Daos.TaskDao;
import com.example.taskmasterapp.Models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase appDataBase;

    public abstract TaskDao taskDao();

    //Singlton
    public static AppDataBase getInstance(Context context){
        if (appDataBase == null){
            synchronized (AppDataBase.class){
                if (appDataBase == null){
                    appDataBase = Room.databaseBuilder(context, AppDataBase.class,"task-database").allowMainThreadQueries().build();
                }
            }
        }
        return appDataBase;
    }
}
