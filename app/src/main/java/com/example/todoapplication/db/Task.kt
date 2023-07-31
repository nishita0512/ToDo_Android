package com.example.todoapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Int = 0,
    var taskTitle: String,
    var taskDescription: String,
    var taskDateAndTime: Long,
    var isCompleted: Boolean
)
