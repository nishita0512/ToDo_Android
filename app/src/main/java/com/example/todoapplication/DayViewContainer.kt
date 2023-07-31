package com.example.todoapplication

import android.view.View
import com.example.todoapplication.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
     private val binding = CalendarDayLayoutBinding.bind(view)
     val textView = binding.calendarDayText
     val calendarDayLayout = binding.calendarDayLayout
}