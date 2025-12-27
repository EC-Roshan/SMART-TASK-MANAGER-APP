package com.example.smarttaskmanager.ui.statisticreport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smarttaskmanager.TaskDao

class StatisticReportViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatisticReportViewModel(taskDao) as T
    }
}