package com.example.todoapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: Task)

    @Query("UPDATE Task SET isCompleted = NOT isCompleted WHERE taskId = :taskId")
    suspend fun toggleTask(taskId: Int): Int

    @Delete
    suspend fun deleteTask(task: Task): Int

    @Query("SELECT * FROM Task WHERE taskDateAndTime = :date ORDER BY taskDateAndTime ASC")
    suspend fun getTasksOfDate(date: Long): List<Task>

    @Query("SELECT * FROM Task")
    fun getAllTasks(): LiveData<List<Task>>

}