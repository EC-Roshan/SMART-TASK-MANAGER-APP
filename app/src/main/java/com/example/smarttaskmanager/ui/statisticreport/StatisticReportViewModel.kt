package com.example.smarttaskmanager.ui.statisticreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttaskmanager.TaskDao
import com.example.smarttaskmanager.model.TaskStats
import com.example.smarttaskmanager.PriorityCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticReportViewModel(private val taskDao: TaskDao) : ViewModel() {

    private val _taskStats = MediatorLiveData<TaskStats>()
    val taskStats: LiveData<TaskStats> = _taskStats

    val priorityDistribution: LiveData<List<PriorityCount>> = taskDao.getPriorityDistribution()

    init {
        val initialStats = TaskStats(0, 0, 0, 0, 0)
        _taskStats.value = initialStats

        _taskStats.addSource(taskDao.getCompletedCountLive()) { count ->
            updateStats { it.copy(completedCount = count) }
        }

        _taskStats.addSource(taskDao.getPendingCountLive()) { count ->
            updateStats { it.copy(pendingCount = count) }
        }

        _taskStats.addSource(taskDao.getHighPriorityCountLive()) { count ->
            updateStats { it.copy(highCount = count) }
        }

        _taskStats.addSource(taskDao.getMediumPriorityCountLive()) { count ->
            updateStats { it.copy(mediumCount = count) }
        }

        _taskStats.addSource(taskDao.getLowPriorityCountLive()) { count ->
            updateStats { it.copy(lowCount = count) }
        }
    }

    private fun updateStats(update: (TaskStats) -> TaskStats) {
        val current = _taskStats.value ?: TaskStats(0, 0, 0, 0, 0)
        _taskStats.value = update(current)
    }

    fun completeTask(taskId: Int, lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.markTaskCompletedWithLocation(taskId, lat, lng)
        }
    }
}