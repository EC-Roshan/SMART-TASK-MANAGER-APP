package com.example.smarttaskmanager

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.example.smarttaskmanager.databinding.ActivityMain3Binding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Always use light mode - ignore dark mode/battery saver
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_setting, R.id.nav_report, R.id.nav_aboutus),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)


        binding.appBarMain.fab.setOnClickListener {
            val currentDestination = navController.currentDestination?.id

            if (currentDestination == R.id.nav_home) {

                binding.appBarMain.fab.isEnabled = false
                navController.navigate(R.id.action_nav_home_to_addTaskFragment)
                binding.appBarMain.fab.postDelayed({
                    binding.appBarMain.fab.isEnabled = true
                }, 500)
            }
        }

        // Request notification permission when app opens
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Notifications enabled! You'll receive task reminders.",
                    Toast.LENGTH_LONG
                ).show()
                // Send test notification to confirm it's working
                NotificationHelper.sendTestNotification(this)
            } else {
                Toast.makeText(
                    this,
                    "Notifications disabled. You won't receive task reminders.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        private const val NOTIFICATION_PERMISSION_CODE = 1001
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}