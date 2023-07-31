package com.example.todoapplication.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todoapplication.Constants
import com.example.todoapplication.Constants.taskAdapter
import com.example.todoapplication.DayViewContainer
import com.example.todoapplication.R
import com.example.todoapplication.TaskAdapter
import com.example.todoapplication.databinding.FragmentTasksBinding
import com.example.todoapplication.db.Task
import com.example.todoapplication.db.TaskEvent
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.WeekDayBinder
import kotlinx.coroutines.flow.collect
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.time.temporal.TemporalField
import java.util.Locale

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var currentContainer: DayViewContainer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(layoutInflater,container,false)

        binding.apply{

            weekCalendarView.dayBinder = object: WeekDayBinder<DayViewContainer> {

                override fun bind(container: DayViewContainer, data: WeekDay) {
                    container.textView.text = data.date.dayOfMonth.toString()

                    val dateInMillis = data.date.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

                    if(data.date.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant().toEpochMilli() == Constants.taskViewModel.currentDateStart.value){
                        currentContainer = container
                        currentContainer.calendarDayLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.secondary))
                        currentContainer.textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryDark))
                        container.calendarDayLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary))
                        container.textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.secondary))
                    }

                    container.calendarDayLayout.setOnClickListener {
                        if(Constants.taskViewModel.currentDateStart.value != dateInMillis) {
                            Constants.taskViewModel.currentDateStart.value = dateInMillis
                            currentContainer.calendarDayLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.secondary))
                            currentContainer.textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryDark))
                            container.calendarDayLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.primary))
                            container.textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.secondary))
                            currentContainer = container
                        }
                    }

                }

                override fun create(view: View) = DayViewContainer(view)

            }

            val currentDate = LocalDate.now()
            val currentMonth = YearMonth.now()
            val startDate = currentMonth.minusMonths(100).atStartOfMonth()
            val endDate = currentMonth.plusMonths(100).atEndOfMonth()
            val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
            weekCalendarView.setup(startDate, endDate, daysOfWeek.first())
            weekCalendarView.scrollToWeek(currentDate)

            val titlesContainer = binding.root.findViewById<ViewGroup>(R.id.titlesContainer)
            titlesContainer.children
                .map { it as TextView }
                .forEachIndexed { index, textView ->
                    val dayOfWeek = daysOfWeek[index]
                    val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    textView.text = title
                }

            btnAddTask.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.action_tasksFragment_to_addTasksFragment)
            }

            taskAdapter.submitList(Constants.taskViewModel.readAllData.value)
            Constants.taskViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                taskAdapter.submitList(it)
            })
            tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            tasksRecyclerView.adapter = taskAdapter

        }

        return binding.root
    }

}