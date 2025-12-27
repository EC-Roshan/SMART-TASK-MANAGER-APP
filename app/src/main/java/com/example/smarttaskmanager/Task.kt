package com.example.smarttaskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: String,
    val dueTime: String,
    val priority: Int,
    val category: String,
    val createdDate: String,

    var isCompleted: Boolean = false,
    val completedLatitude: Double? = null,

    val completedLongitude: Double? = null

)