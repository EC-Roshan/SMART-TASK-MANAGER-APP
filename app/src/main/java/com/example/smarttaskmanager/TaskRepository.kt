package com.example.smarttaskmanager

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
    val priorityDistribution: LiveData<List<PriorityCount>> = taskDao.getPriorityDistribution()
}