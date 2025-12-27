package com.example.smarttaskmanager.ui.statisticreport

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smarttaskmanager.MainActivity3
import com.example.smarttaskmanager.TaskDatabase
import com.example.smarttaskmanager.databinding.FragmentStatisticreportBinding
import com.example.smarttaskmanager.PriorityCount
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.github.mikephil.charting.components.Legend
import com.example.smarttaskmanager.R

class StatisticReportFragment : Fragment() {

    private var _binding: FragmentStatisticreportBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: StatisticReportViewModel
    private val priorityMap: Map<Int, String> = mapOf(
        1 to "Flexible",
        2 to "Important",
        3 to "Essential"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticreportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Hide FAB from MainActivity3
        (activity as? MainActivity3)?.findViewById<FloatingActionButton>(R.id.fab)?.hide()

        // Inject TaskDao into ViewModel
        val taskDao = TaskDatabase.getDatabase(requireContext()).taskDao()
        val factory = StatisticReportViewModelFactory(taskDao)
        viewModel = ViewModelProvider(this, factory)[StatisticReportViewModel::class.java]

        observeChartData()

        return root
    }

    private fun observeChartData() {
        viewModel.taskStats.observe(viewLifecycleOwner) { stats ->
            setupStatusChart(stats.completedCount.toFloat(), stats.pendingCount.toFloat())
        }

        viewModel.priorityDistribution.observe(viewLifecycleOwner) { priorityList ->
            if (priorityList.isNullOrEmpty()) {
                binding.pieChartPriority.clear()
                binding.pieChartPriority.centerText = "No Data"
            } else {
                setupPriorityChart(priorityList)
            }
        }
    }

    private fun setupStatusChart(completedCount: Float, pendingCount: Float) {
        val entries = listOf(
            PieEntry(completedCount, "Completed"),
            PieEntry(pendingCount, "Pending")
        )

        val dataSet = PieDataSet(entries, "").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 14f
            valueTextColor = Color.WHITE
        }

        binding.pieChartStatus.apply {
            data = PieData(dataSet)
            description.isEnabled = false
            centerText = "Status"
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            animateY(1000)
            legend.apply {
                isEnabled = true
                textSize = 16f
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            }
            invalidate()
        }
    }

    private fun setupPriorityChart(priorityList: List<PriorityCount>) {
        val entries = priorityList.map { item ->
            val label = priorityMap[item.priority] ?: "Unknown"
            PieEntry(item.count.toFloat(), label)
        }

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                Color.parseColor("#43A047"), // Flexible
                Color.parseColor("#FB8C00"), // Important
                Color.parseColor("#E53935"), // Essential
                Color.parseColor("#9E9E9E")  // Unknown
            )
            sliceSpace = 6f
            valueTextSize = 14f
            valueTextColor = Color.WHITE
        }

        binding.pieChartPriority.apply {
            data = PieData(dataSet)
            setUsePercentValues(false)
            setDrawEntryLabels(false)
            description.isEnabled = false
            centerText = "Priority"
            animateY(1000)
            legend.apply {
                isEnabled = true
                textSize = 16f
                formSize = 10f
                form = Legend.LegendForm.SQUARE
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                xEntrySpace = 40f // Adds space between items
                yEntrySpace = 15f
            }
            invalidate()
        }

        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}