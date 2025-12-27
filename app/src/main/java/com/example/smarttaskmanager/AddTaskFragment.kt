package com.example.smarttaskmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.smarttaskmanager.databinding.FragmentAddTaskBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    private val handler = Handler(Looper.getMainLooper())
    private var isClockRunning = true

    private val taskViewModel: TaskViewModel by viewModels()

    private val updateClockRunnable = object : Runnable {
        override fun run() {
            if (isClockRunning) {
                val currentTime = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
                binding.TimeText.setText(currentTime)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            title = "Add Task"
        }

        (activity as? MainActivity3)?.findViewById<TextView>(R.id.appname)?.visibility = View.GONE
        (activity as? MainActivity3)?.findViewById<FloatingActionButton>(R.id.fab)?.hide()

        // Start live clock
        isClockRunning = true
        handler.post(updateClockRunnable)

        // Date Picker
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = sdf.format(calendar.time)
            binding.dueDateText.setText(formattedDate)

        }

        val datePicker = {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        // Date Picker
        val dateSetListener2 = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = sdf.format(calendar.time)
            binding.datetext.setText(formattedDate)
        }

        val datePicker2 = {
            DatePickerDialog(
                requireContext(),
                dateSetListener2,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val todayDate = sdf.format(Calendar.getInstance().time)
        binding.datetext.setText(todayDate)


        // Manual Time Picker (optional, for timeText field)
        binding.timeText.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    // Convert to 12-hour format with AM/PM
                    val timeCalendar = Calendar.getInstance()
                    timeCalendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                    timeCalendar.set(Calendar.MINUTE, selectedMinute)
                    timeCalendar.set(Calendar.SECOND, 0)

                    val sdfTime = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
                    val formattedTime = sdfTime.format(timeCalendar.time)
                    binding.timeText.setText(formattedTime)
                },
                hour,
                minute,
                false  // false = 12-hour format with AM/PM
            ).show()
        }

        binding.dueDateText.setOnClickListener { datePicker() }
        binding.datetext.setOnClickListener { datePicker() }

        val validateDateInput: (View, Boolean) -> Unit = { _, hasFocus ->
            if (!hasFocus) {
                val input = binding.dueDateText.text.toString()
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                sdf.isLenient = false
                try {
                    sdf.parse(input)
                } catch (e: ParseException) {
                    binding.dueDateText.error = "Invalid date. Use dd/MM/yyyy"
                }
            }
        }

        binding.dueDateText.setOnFocusChangeListener(validateDateInput)
        binding.datetext.setOnFocusChangeListener(validateDateInput)

        binding.Addbutton.setOnClickListener {
            // Stop the clock
            isClockRunning = false
            handler.removeCallbacks(updateClockRunnable)

            val title = binding.titleText.text.toString().trim()
            val description = binding.descriptionText.text.toString().trim()
            val dueDate = binding.dueDateText.text.toString().trim()
            val category = binding.categorytext.text.toString().trim().ifEmpty { "Uncategorized" }
            val dueTime =
                binding.timeText.text.toString().trim() // Use due time picker, not live clock!

            if (title.isEmpty()) {
                binding.titleText.error = "Title is required"
                return@setOnClickListener
            }

            if (dueDate.isEmpty()) {
                binding.dueDateText.error = "Due date is required"
                return@setOnClickListener
            }

            if (dueTime.isEmpty()) {
                binding.timeText.error = "Due time is required"
                return@setOnClickListener
            }

            val priority = when {
                binding.chipEssential.isChecked -> 3
                binding.chipImportant.isChecked -> 2
                binding.chipFlexible.isChecked -> 1
                else -> 0
            }

            val priorityText = when (priority) {
                3 -> "Essential"
                2 -> "Important"
                1 -> "Flexible"
                else -> "None"
            }
            binding.priority.text = priorityText

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val createdDate = sdf.format(Calendar.getInstance().time)

            val task = Task(
                title = title,
                description = description,
                dueDate = dueDate,
                priority = priority,
                category = category,
                createdDate = createdDate,
                dueTime = dueTime
            )

            taskViewModel.insert(task)

            // Schedule notification for the task
            scheduleNotification(task)

            Toast.makeText(requireContext(), "Task saved! Due at $dueTime", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun scheduleNotification(task: Task) {
        // Check if notifications are enabled in settings
        if (!NotificationPreferences.areNotificationsEnabled(requireContext())) {
            return // Don't schedule if notifications are disabled
        }

        try {
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
                notificationMessage = "Task '${task.title}' is due tomorrow!"
                reminderInfo = "Reminder set for 24 hours before due time"
            } else {
                // Less than 24 hours - notify at due time
                notificationTime = dueTime

                // Calculate human-readable time remaining
                val hours = timeDifference / (60 * 60 * 1000)
                val minutes = (timeDifference % (60 * 60 * 1000)) / (60 * 1000)

                notificationMessage = "Task '${task.title}' is due NOW!"

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

                val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .addTag("task_${task.title}_${System.currentTimeMillis()}")
                    .build()

                WorkManager.getInstance(requireContext()).enqueue(workRequest)
                Toast.makeText(
                    requireContext(),
                    reminderInfo,
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to schedule notification", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isClockRunning = false
        handler.removeCallbacks(updateClockRunnable)
        _binding = null
    }
}