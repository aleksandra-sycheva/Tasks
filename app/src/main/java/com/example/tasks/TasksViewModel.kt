package com.example.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""
    private val tasks: LiveData<List<Task>> = dao.getAll()
    val tasksString: MutableLiveData<String> = MutableLiveData()
    init {
        tasks.observeForever {
            taskList->tasksString.value = formatTasks(taskList)
        }
    }
    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }
}