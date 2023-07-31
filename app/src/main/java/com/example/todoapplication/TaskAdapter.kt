package com.example.todoapplication

import com.example.todoapplication.db.Task
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.databinding.TaskSingleRowBinding
import com.example.todoapplication.db.TaskEvent
import java.io.ByteArrayOutputStream
import java.util.zip.Inflater

class TaskAdapter
constructor() : ListAdapter<Task, TaskAdapter.ViewHolder>(Diff){

    class ViewHolder(private val binding: TaskSingleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                txtTitleSingleRow.text = task.taskTitle
                txtDescriptionSingleRow.text = task.taskDescription
                checkBoxSingleRow.isChecked = task.isCompleted
                checkBoxSingleRow.setOnClickListener {
                    Constants.taskViewModel.toggleTask(task.taskId)
                }
                btnDeleteSingleRow.setOnClickListener {
                    Constants.taskViewModel.deleteTask(task)
                }
                root.setOnClickListener {
                    Constants.currentTask = task
                    Navigation.findNavController(binding.root).navigate(R.id.action_tasksFragment_to_editTasksFragment)
                }
            }
        }
    }

    private object Diff : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem:Task, newItem:Task): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskSingleRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}
