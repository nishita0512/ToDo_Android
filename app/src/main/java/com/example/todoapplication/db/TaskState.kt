package com.example.todoapplication.db

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val title: String = "",
    val description: String = "",
    val dateAndTime: Long = 0,
    val currentDateStart: Long
)
