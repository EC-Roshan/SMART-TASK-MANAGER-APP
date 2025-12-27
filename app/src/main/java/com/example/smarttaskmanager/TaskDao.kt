package com.example.smarttaskmanager

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun delete(task: Task)
    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM Tasks ORDER BY id DESC")
    fun getAllTasks(): LiveData<List<Task>>
    @Query("SELECT COUNT(*) FROM Tasks WHERE isCompleted = 1")
     fun getCompletedCountLive(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM Tasks WHERE isCompleted = 0")
     fun getPendingCountLive():LiveData<Int>

    @Query("SELECT COUNT(*) FROM Tasks WHERE priority = 3")
    fun getHighPriorityCountLive(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM Tasks WHERE priority = 2")
    fun getMediumPriorityCountLive(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM Tasks WHERE priority = 1")
     fun getLowPriorityCountLive(): LiveData<Int>
    @Query("UPDATE tasks SET isCompleted = 1, completedLatitude = :lat, completedLongitude = :lng WHERE id = :taskId")
    suspend fun markTaskCompletedWithLocation(taskId: Int, lat: Double, lng: Double)
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Int): Task?
    @Query("SELECT priority AS priority, COUNT(*) AS count FROM Tasks GROUP BY priority")
    fun getPriorityDistribution(): LiveData<List<PriorityCount>>
}