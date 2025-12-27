package com.example.smarttaskmanager.model

data class TaskStats(
    var completedCount: Int,
    var pendingCount: Int,
    var highCount: Int,
    var mediumCount: Int,
    var lowCount: Int
)