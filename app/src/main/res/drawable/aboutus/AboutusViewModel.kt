package com.example.smarttaskmanager2.ui.aboutus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutusViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is about us Fragment"
    }
    val text: LiveData<String> = _text
}