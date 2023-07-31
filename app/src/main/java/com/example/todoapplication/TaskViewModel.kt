package com.example.todoapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.db.Task
import com.example.todoapplication.db.TaskDatabase
import com.example.todoapplication.db.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application){

    val currentDateStart = MutableLiveData(System.currentTimeMillis() - (System.currentTimeMillis() % 86400000))
    var readAllData = MutableLiveData<List<Task>>(emptyList())
    private val repository: TaskRepository

    init{
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)

        currentDateStart.observeForever {
            Log.d("Date: ",it.toString())
            viewModelScope.launch {
                readAllData.value = repository.getTasksByDate(it)
            }
        }

    }

    fun upsertTask(){
        viewModelScope.async{
            repository.upsertTask(Constants.currentTask)
        }
        currentDateStart.value = currentDateStart.value?.plus(1)
        currentDateStart.value = currentDateStart.value?.minus(1)
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(task)
        }
        currentDateStart.value = task.taskDateAndTime
    }

    fun toggleTask(taskId: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.toggleTask(taskId)
        }
    }

}
//class TaskViewModel(
//    private val dao: TaskDao
//): ViewModel() {
//
//
//    private val _tasks = _currentDateStart.flatMapLatest {
////        dao.getTasksOfDate(it,it+86399999.toLong())
//        dao.getAllTasks()
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//    private val _state = MutableStateFlow(TaskState(currentDateStart = _currentDateStart.value))
//
//    val state = combine(_state,_tasks,_currentDateStart) { state, tasks, currentDateStart ->
//        state.copy(
//            tasks = tasks,
//            currentDateStart = currentDateStart
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TaskState(currentDateStart = _currentDateStart.value))
//
//    fun onEvent(event: TaskEvent){
//        when(event){
//            TaskEvent.InsertTask -> {
//                val title = _state.value.title
//                val description = _state.value.description
//                val dateAndTime = _state.value.dateAndTime
//
//                if(title.isBlank() || description.isBlank()){
//                    Log.d("Insert: ","Task Insert Failed")
//                    return
//                }
//
//                val task = Task(
//                    taskTitle = title,
//                    taskDescription = description,
//                    taskDateAndTime = dateAndTime,
//                    isCompleted = false
//                )
//
//                viewModelScope.launch {
//                    dao.insertTask(task)
//                    Log.d("Insert: ","Task Inserted")
//                }
//
//                _state.update{ it.copy(
//                    title = "",
//                    description = "",
//                    dateAndTime = 0
//                )}
//
//            }
//            is TaskEvent.UpdateTask -> {
//
//            }
//            is TaskEvent.DeleteTask -> {
//                viewModelScope.launch {
//                    dao.deleteTask(event.task)
//                }
//            }
//            is TaskEvent.ToggleTask -> {
//                viewModelScope.launch {
//                    dao.toggleTask(event.taskId)
//                }
//            }
//            is TaskEvent.SetDateAndTime -> {
//                _state.update {it.copy(
//                    dateAndTime = event.dateAndTime
//                ) }
//            }
//            is TaskEvent.SetDescription -> {
//                _state.update {it.copy(
//                    description = event.description
//                ) }
//            }
//            is TaskEvent.SetTitle -> {
//                _state.update {it.copy(
//                    title = event.title
//                ) }
//                Log.d("Title: ",_state.value.title)
//            }
//            is TaskEvent.DateChanged ->{
//                _currentDateStart.value = event.date
//            }
//        }
//    }
//
//}