package com.example.taskmasterapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.taskmasterapp.Daos.TaskDao;
import com.example.taskmasterapp.Models.TaskModule;

@Database(entities = {TaskModule.class}, version = 4, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase appDataBase;

    public abstract TaskDao taskDao();

    //Singlton
    public static AppDataBase getInstance(Context context){
        if (appDataBase == null){
            synchronized (AppDataBase.class){
                if (appDataBase == null){
                    appDataBase = Room.databaseBuilder(context, AppDataBase.class,"task-database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return appDataBase;
    }
}
