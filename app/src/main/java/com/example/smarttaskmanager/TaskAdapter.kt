package com.example.smarttaskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var onCompleteClickListener: ((Task) -> Unit)? = null


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescription)
        val taskDueDate: TextView = itemView.findViewById(R.id.taskDueDate)
        val taskPriority: TextView = itemView.findViewById(R.id.taskPriority)
        val category: TextView = itemView.findViewById(R.id.category)
        val completedLabel: TextView = itemView.findViewById(R.id.taskCompleted)
        val checkbox: CheckBox = itemView.findViewById(R.id.taskCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTitle.text = task.title
        holder.taskDescription.text = task.description
        holder.taskDueDate.text = "Due Date: ${task.dueDate}"
        holder.taskPriority.text = when (task.priority) {
            3 -> "Essential"
            2 -> "Important"
            1 -> "Flexible"
            else -> "None"
        }
        holder.category.text = task.category
        holder.completedLabel.text = if (task.isCompleted) "" else ""


        holder.checkbox.isChecked = task.isCompleted


        holder.checkbox.setOnClickListener {
            onCompleteClickListener?.invoke(task)
        }


        holder.completedLabel.setOnClickListener {
            onCompleteClickListener?.invoke(task)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(task)
        }

    }

    override fun getItemCount(): Int = taskList.size

    fun setOnCompleteClickListener(listener: (Task) -> Unit) {
        onCompleteClickListener = listener
    }
    private var onItemClickListener: ((Task) -> Unit)? = null

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }

}