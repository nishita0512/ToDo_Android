package com.example.todoapplication.db

sealed interface TaskEvent{
    object InsertTask: TaskEvent
    data class SetTitle(val title: String): TaskEvent
    data class SetDescription(val description: String): TaskEvent
    data class SetDateAndTime(val dateAndTime: Long): TaskEvent
    data class ToggleTask(val taskId: Int): TaskEvent
    data class UpdateTask(val task: Task): TaskEvent
    data class DeleteTask(val task: Task): TaskEvent
    data class DateChanged(val date: Long): TaskEvent
}