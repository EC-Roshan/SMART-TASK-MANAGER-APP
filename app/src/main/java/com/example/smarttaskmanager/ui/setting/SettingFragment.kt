package com.example.smarttaskmanager.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smarttaskmanager.MainActivity3
import com.example.smarttaskmanager.NotificationHelper
import com.example.smarttaskmanager.NotificationPreferences
import com.example.smarttaskmanager.R
import com.example.smarttaskmanager.databinding.FragmentSettingBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        (activity as? MainActivity3)?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupNotificationToggle()

        return root
    }

    private fun setupNotificationToggle() {
        // Load saved preference
        val isEnabled = NotificationPreferences.areNotificationsEnabled(requireContext())
        binding.notificationSwitch.isChecked = isEnabled

        // Handle switch toggle
        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save preference
            NotificationPreferences.setNotificationsEnabled(requireContext(), isChecked)

            // Show feedback
            if (isChecked) {
                Toast.makeText(
                    requireContext(),
                    "Notifications enabled ✓",
                    Toast.LENGTH_SHORT
                ).show()
                // Send test notification
                NotificationHelper.sendTestNotification(requireContext())
            } else {
                Toast.makeText(
                    requireContext(),
                    "Notifications disabled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}