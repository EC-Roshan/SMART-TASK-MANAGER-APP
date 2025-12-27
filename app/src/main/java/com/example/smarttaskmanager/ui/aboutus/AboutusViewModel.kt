package com.example.smarttaskmanager.ui.aboutus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutusViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Smart Task Manager is built to help you stay organized, productive, and stress‑free every day. Our app makes it simple to create, prioritize, and track tasks with a clean and engaging design.\n" +
                "We focus on combining reliability with personalization — offering offline storage, smart reminders, and customizable views so you can manage work, study, or personal goals effortlessly. Interactive dashboards and motivating visuals keep you on track and make task management enjoyable.\n" +
                "Our mission is to deliver a seamless experience that empowers users to take control of their time and achieve more. With Smart Task Manager, productivity becomes easy, intuitive, and rewarding.\n"
    }
    val text: LiveData<String> = _text
}