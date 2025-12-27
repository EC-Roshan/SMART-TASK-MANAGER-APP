package com.example.smarttaskmanager.ui.home

import com.example.smarttaskmanager.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttaskmanager.*
import com.example.smarttaskmanager.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import androidx.work.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.core.content.ContextCompat


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    private lateinit var updateScreenLauncher: androidx.activity.result.ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as? MainActivity3)?.findViewById<TextView>(R.id.appname)?.visibility = View.VISIBLE
        (activity as? MainActivity3)?.findViewById<FloatingActionButton>(R.id.fab)?.show()

        taskAdapter = TaskAdapter(taskList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        taskViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[TaskViewModel::class.java]

        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            taskList.clear()
            taskList.addAll(tasks)
            updateChart(tasks)
            taskAdapter.notifyDataSetChanged()
            binding.emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        }

        taskAdapter.setOnCompleteClickListener { task ->
            val newStatus = !task.isCompleted
            taskViewModel.updateTask(task.copy(isCompleted = newStatus))
            val message = if (newStatus) "Task marked complete" else "Task marked incomplete"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Handle result from UpdateScreen → EditTaskActivity
        updateScreenLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data ?: return@registerForActivityResult
                val originalTitle = data.getStringExtra("title") ?: return@registerForActivityResult

                val updatedTitle = data.getStringExtra("updatedTitle") ?: ""
                val updatedDescription = data.getStringExtra("updatedDescription") ?: ""
                val updatedDueDate = data.getStringExtra("updatedDueDate") ?: ""
                val updatedDueTime = data.getStringExtra("updatedDueTime") ?: ""
                val updatedPriority = data.getStringExtra("updatedPriority") ?: ""
                val updatedCategory = data.getStringExtra("updatedCategory") ?: ""
                val updatedIsCompleted = data.getBooleanExtra("updatedIsCompleted", false)

                val originalTask = taskList.find { it.title.toString() == originalTitle }
                if (originalTask != null) {
                    val updatedTask = originalTask.copy(
                        title = updatedTitle,
                        description = updatedDescription,
                        dueDate = updatedDueDate,
                        dueTime = updatedDueTime,
                        priority = when (updatedPriority.lowercase()) {
                            "essential" -> 3
                            "important" -> 2
                            "flexible" -> 1
                            else -> 0
                        },
                        category = updatedCategory,
                        isCompleted = updatedIsCompleted
                    )
                    taskViewModel.updateTask(updatedTask)

                    // Only schedule notification if task is not completed
                    if (!updatedIsCompleted) {
                        scheduleNotification(updatedTask)
                    }

                    Toast.makeText(requireContext(), "Task updated", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Launch UpdateScreen and wait for result
        taskAdapter.setOnItemClickListener { task ->
            val intent = Intent(requireContext(), UpdateScreen::class.java).apply {
                putExtra("title", task.title.toString())
                putExtra("description", task.description)
                putExtra("dueDate", task.dueDate)
                putExtra("dueTime", task.dueTime)
                putExtra("priority", task.priority.toString())
                putExtra("category", task.category)
                putExtra("isCompleted", task.isCompleted)
            }
            updateScreenLauncher.launch(intent)
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskList[position]

                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete") { _, _ ->
                        taskViewModel.delete(task)
                        Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        taskAdapter.notifyItemChanged(position)
                    }
                    .setCancelable(false)
                    .show()
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateChart(tasks: List<Task>) {
        val completedCount = tasks.count { it.isCompleted }
        val pendingCount = tasks.size - completedCount

        val entries = listOf(
            PieEntry(completedCount.toFloat(), "Completed"),
            PieEntry(pendingCount.toFloat(), "Pending")
        )

        val dataSet = PieDataSet(entries, "Task Status").apply {
            colors = listOf(
                android.graphics.Color.parseColor("#4CAF50"),
                android.graphics.Color.parseColor("#F44336")
            )
            valueTextSize = 14f
        }


    }

    //  WorkManager notification scheduling
    private fun scheduleNotification(task: Task) {
        // Check if notifications are enabled in settings
        if (!NotificationPreferences.areNotificationsEnabled(requireContext())) {
            return // Don't schedule if notifications are disabled
        }

        try {
            // Parse the due date and time
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault())
            val dueDateTime = sdf.parse("${task.dueDate} ${task.dueTime}") ?: return

            val currentTime = System.currentTimeMillis()
            val dueTime = dueDateTime.time
            val timeDifference = dueTime - currentTime

            // If due time is in the past, don't schedule
            if (timeDifference <= 0) {
                Toast.makeText(
                    requireContext(),
                    "Due time is in the past!",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            // Determine notification time and message
            val notificationTime: Long
            val notificationMessage: String
            val reminderInfo: String

            if (timeDifference > 24 * 60 * 60 * 1000) {
                // More than 24 hours - notify 24 hours before
                notificationTime = dueTime - (24 * 60 * 60 * 1000)
                notificationMessage = "Task '${task.title}' is due tomorrow! ${task.description}"
                reminderInfo = "Reminder updated for 24 hours before due time"
            } else {
                // Less than 24 hours - notify at due time
                notificationTime = dueTime

                // Calculate human-readable time remaining
                val hours = timeDifference / (60 * 60 * 1000)
                val minutes = (timeDifference % (60 * 60 * 1000)) / (60 * 1000)

                notificationMessage = "Task '${task.title}' is due NOW! ${task.description}"

                reminderInfo = when {
                    hours > 0 && minutes > 0 -> "Reminder set for ${hours}h ${minutes}m from now"
                    hours > 0 -> "Reminder set for ${hours}h from now"
                    minutes > 0 -> "Reminder set for ${minutes} minutes from now"
                    else -> "Reminder set for less than 1 minute"
                }
            }

            val delay = notificationTime - currentTime

            if (delay > 0) {
                val data = workDataOf(
                    "title" to task.title,
                    "description" to notificationMessage
                )

                // Cancel any existing notifications for this task
                WorkManager.getInstance(requireContext()).cancelAllWorkByTag("task_${task.title}")

                val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .addTag("task_${task.title}")
                    .build()

                WorkManager.getInstance(requireContext()).enqueue(workRequest)
                Toast.makeText(requireContext(), reminderInfo, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                requireContext(),
                "Failed to schedule notification: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

