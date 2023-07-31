package com.example.todoapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.example.todoapplication.databinding.ActivityMainBinding
import com.example.todoapplication.db.TaskDatabase
import com.example.todoapplication.db.TaskEvent
import kotlinx.coroutines.flow.count
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Constants.taskViewModel = TaskViewModel(application)

    }

}