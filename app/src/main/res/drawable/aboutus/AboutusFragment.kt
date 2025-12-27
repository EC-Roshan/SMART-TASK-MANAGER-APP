package com.example.smarttaskmanager2.ui.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smarttaskmanager2.databinding.FragmentAboutusBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentAboutusBinding? = null
    private val binding get() = _binding!!

    private lateinit var aboutusViewModel: AboutusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aboutusViewModel = ViewModelProvider(this)[AboutusViewModel::class.java]

        _binding = FragmentAboutusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAboutus
        aboutusViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}