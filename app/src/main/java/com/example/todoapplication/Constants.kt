package com.example.todoapplication

import com.example.todoapplication.db.Task
import com.example.todoapplication.db.TaskDatabase

object Constants {
    lateinit var taskViewModel: TaskViewModel
    lateinit var db: TaskDatabase
    val taskAdapter = TaskAdapter()
    var currentTask = Task(0,"","",0,false)
}