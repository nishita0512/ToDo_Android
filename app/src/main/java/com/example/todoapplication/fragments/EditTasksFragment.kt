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
import com.example.todoapplication.databinding.FragmentEditTasksBinding
import java.util.Calendar

class EditTasksFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentEditTasksBinding
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTasksBinding.inflate(layoutInflater,container,false)

        val cal = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(requireContext(), this@EditTasksFragment, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))

        binding.apply{
            edtTxtTitleEditFragment.setText(Constants.currentTask.taskTitle)
            edtTxtDescriptionEditFragment.setText(Constants.currentTask.taskDescription)
            val date = Calendar.getInstance()
            date.timeInMillis = Constants.currentTask.taskDateAndTime
            edtTxtDateEditFragment.setText("${date.get(Calendar.DAY_OF_MONTH)}-${date.get(Calendar.MONTH)}-${date.get(Calendar.YEAR)}")

            btnCancelEditTasksFragment.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.action_editTasksFragment_to_tasksFragment)
            }

            btnUpdateEditTasksFragment.setOnClickListener {
                Constants.currentTask.taskTitle = edtTxtTitleEditFragment.text.toString()
                Constants.currentTask.taskDescription = edtTxtDescriptionEditFragment.text.toString()
                Constants.taskViewModel.upsertTask()
                Navigation.findNavController(binding.root).navigate(R.id.action_editTasksFragment_to_tasksFragment)
            }

            edtTxtDateEditFragment.setOnClickListener {
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
        binding.edtTxtDateEditFragment.setText("$dayOfMonth/$month/$year")
    }

}