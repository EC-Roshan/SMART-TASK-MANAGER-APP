package com.example.smarttaskmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class UpdateScreen : AppCompatActivity() {

    companion object {
        const val REQUEST_EDIT_TASK = 101
    }

    private lateinit var title: String
    private lateinit var description: String
    private lateinit var dueDate: String
    private lateinit var dueTime: String
    private lateinit var priority: String
    private lateinit var category: String
    private var isCompleted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_screen)

        setSupportActionBar(findViewById(R.id.updateToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Get data from intent
        title = intent.getStringExtra("title") ?: ""
        description = intent.getStringExtra("description") ?: ""
        dueDate = intent.getStringExtra("dueDate") ?: ""
        dueTime = intent.getStringExtra("dueTime") ?: ""
        priority = intent.getStringExtra("priority") ?: ""
        category = intent.getStringExtra("category") ?: ""
        isCompleted = intent.getBooleanExtra("isCompleted", false)

        val priorityName = when (priority){
            "3" -> "Essential"
            "2" -> "Important"
            "1" -> "Flexible"
            else -> "None"
        }

        // Populate views
        findViewById<TextView>(R.id.Title).text = "Title: $title"
        findViewById<TextView>(R.id.Description).text = "Description: $description"
        findViewById<TextView>(R.id.DueDate).text = "Due Date: $dueDate"
        findViewById<TextView>(R.id.DueTime).text = "Due Time: $dueTime"
        findViewById<TextView>(R.id.Priority).text = "Priority: $priorityName"
        findViewById<TextView>(R.id.Category).text = "Category: $category"
        findViewById<TextView>(R.id.Status).text = "Completed: ${if (isCompleted) "Yes" else "No"}"

        // Back button
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Update button → launch EditTaskActivity
        findViewById<Button>(R.id.Updatebutton).setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java).apply {
                putExtra("title", title)
                putExtra("description", description)
                putExtra("dueDate", dueDate)
                putExtra("dueTime", dueTime)
                putExtra("priority", priorityName)
                putExtra("category", category)
                putExtra("isCompleted", isCompleted)
            }
            startActivityForResult(intent, REQUEST_EDIT_TASK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_EDIT_TASK && resultCode == RESULT_OK && data != null) {
            val resultIntent = Intent().apply {
                putExtra("title", data.getStringExtra("title"))
                putExtra("updatedTitle", data.getStringExtra("updatedTitle"))
                putExtra("updatedDescription", data.getStringExtra("updatedDescription"))
                putExtra("updatedDueDate", data.getStringExtra("updatedDueDate"))
                putExtra("updatedDueTime", data.getStringExtra("updatedDueTime"))
                putExtra("updatedPriority", data.getStringExtra("updatedPriority"))
                putExtra("updatedCategory", data.getStringExtra("updatedCategory"))
                putExtra("updatedIsCompleted", data.getBooleanExtra("updatedIsCompleted", false))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}