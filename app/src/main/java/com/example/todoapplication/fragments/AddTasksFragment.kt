package com.example.todoapplication.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.Navigation
import com.example.todoapplication.Constants
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentAddTasksBinding
import com.example.todoapplication.db.Task
import java.util.Calendar

class AddTasksFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentAddTasksBinding
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTasksBinding.inflate(layoutInflater,container,false)

        val cal = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(requireContext(), this@AddTasksFragment, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))

        binding.apply {

            btnCancelAddTasksFragment.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.action_addTasksFragment_to_tasksFragment)
            }

            btnAddAddTasksFragment.setOnClickListener {
                Constants.currentTask.taskTitle = edtTxtTitleAddFragment.text.toString()
                Constants.currentTask.taskDescription = edtTxtDescriptionAddFragment.text.toString()
                Constants.taskViewModel.upsertTask()
                Navigation.findNavController(binding.root).navigate(R.id.action_addTasksFragment_to_tasksFragment)
            }

            edtTxtDateAddFragment.setOnClickListener {
                datePickerDialog.show()
            }

        }

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        Constants.currentTask.taskDateAndTime = cal.timeInMillis - cal.timeInMillis % 86400000
//        Constants.taskViewModel.onEvent(TaskEvent.SetDateAndTime(cal.timeInMillis))
        binding.edtTxtDateAddFragment.setText("$dayOfMonth/$month/$year")
    }

}