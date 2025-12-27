package com.example.smarttaskmanager2.ui.statisticreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smarttaskmanager2.databinding.FragmentStatisticreportBinding

class StatisticReportFragment : Fragment() {

    private var _binding: FragmentStatisticreportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val statisticReportViewModel =
            ViewModelProvider(this).get(StatisticReportViewModel::class.java)

        _binding = FragmentStatisticreportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textReport
        statisticReportViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}