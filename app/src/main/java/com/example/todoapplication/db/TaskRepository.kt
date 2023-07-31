package com.example.todoapplication.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun upsertTask(task: Task){
        taskDao.upsertTask(task)
    }

    suspend fun deleteTask(task: Task): Int{
        return taskDao.deleteTask(task)
    }

    suspend fun toggleTask(taskId: Int): Int{
        return taskDao.toggleTask(taskId)
    }

    suspend fun getTasksByDate(date: Long): List<Task> {
        return taskDao.getTasksOfDate(date)
    }

}