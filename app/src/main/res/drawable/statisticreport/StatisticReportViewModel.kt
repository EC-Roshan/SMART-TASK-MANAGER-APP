package com.example.smarttaskmanager2.ui.statisticreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticReportViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is statistic report Fragment"
    }
    val text: LiveData<String> = _text
}