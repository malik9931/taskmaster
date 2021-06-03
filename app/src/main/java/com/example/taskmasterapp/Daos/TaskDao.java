package com.example.taskmasterapp.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmasterapp.Models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void addTask (Task task);

    @Update
    void update(Task task);

    @Delete
    void delete (Task task);

    @Query("DELETE FROM task")
    void deleteAllTasks();

    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

//    @Query("SELECT * FROM task WHERE id= :id")
//    Task findTaskById(Long id);


}
