package com.example.taskmasterapp.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmasterapp.Models.TaskModule;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void addTask (TaskModule taskModule);

    @Update
    void update(TaskModule taskModule);

    @Delete
    void delete (TaskModule taskModule);

    @Query("DELETE FROM TaskModule")
    void deleteAllTasks();

    @Query("SELECT * FROM TaskModule")
    List<TaskModule> getAllTasks();

//    @Query("SELECT * FROM task WHERE id= :id")
//    Task findTaskById(Long id);


}
