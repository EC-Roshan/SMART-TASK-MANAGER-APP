package com.example.smarttaskmanager

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import android.view.View

class EditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        setSupportActionBar(findViewById(R.id.editToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val titleEditText = findViewById<EditText>(R.id.titleText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionText)
        val dueDateEditText = findViewById<EditText>(R.id.dueDateText)
        val dueTimeEditText = findViewById<EditText>(R.id.timeText)
        val categoryEditText = findViewById<EditText>(R.id.categorytext)
        val priorityGroup = findViewById<ChipGroup>(R.id.priorityGroup)
        val updateButton = findViewById<Button>(R.id.UpdateTaskButton)

        val originalTitle = intent.getStringExtra("title") ?: ""
        val originalDescription = intent.getStringExtra("description") ?: ""
        val originalDueDate = intent.getStringExtra("dueDate") ?: ""
        val originalDueTime = intent.getStringExtra("dueTime") ?: ""
        val originalPriority = intent.getStringExtra("priority") ?: ""
        val originalCategory = intent.getStringExtra("category") ?: ""


        titleEditText.setText(originalTitle)
        descriptionEditText.setText(originalDescription)
        dueDateEditText.setText(originalDueDate)
        dueTimeEditText.setText(originalDueTime)
        categoryEditText.setText(originalCategory)

        for (i in 0 until priorityGroup.childCount) {
            val chip = priorityGroup.getChildAt(i) as Chip
            if (chip.text.toString().equals(originalPriority, ignoreCase = true)) {
                chip.isChecked = true
                break
            }
        }

        updateButton.setOnClickListener {
            val newTitle = titleEditText.text.toString()
            val newDescription = descriptionEditText.text.toString()
            val newDueDate = dueDateEditText.text.toString()
            val newDueTime = dueTimeEditText.text.toString()
            val newCategory = categoryEditText.text.toString()


            val selectedPriority = if (priorityGroup.checkedChipId != View.NO_ID) {
                val chip = findViewById<Chip>(priorityGroup.checkedChipId)
                chip?.text?.toString() ?: ""
            } else {
                ""
            }


            val resultIntent = Intent().apply {
                putExtra("title", originalTitle)
                putExtra("updatedTitle", newTitle)
                putExtra("updatedDescription", newDescription)
                putExtra("updatedDueDate", newDueDate)
                putExtra("updatedDueTime", newDueTime)
                putExtra("updatedPriority", selectedPriority)
                putExtra("updatedCategory", newCategory)

            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}