package com.example.smarttaskmanager

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.view.View
import android.content.Intent
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    private val scrollDelay: Long = 1700
    private var currentIndex = 0
    private lateinit var scrollRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            finish()
        }

        val scrollView = findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        scrollView.overScrollMode = View.OVER_SCROLL_NEVER
        val logoContainer = findViewById<LinearLayout>(R.id.logoContainer)
        val dotContainer = findViewById<LinearLayout>(R.id.dotContainer)

        val totalDots = logoContainer.childCount

        // Initialize dot views
        for (i in 0 until totalDots) {
            val dot = ImageView(this).apply {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.marginEnd = 8
                layoutParams = params
                setImageResource(R.drawable.inactive_dot)
            }
            dotContainer.addView(dot)
        }

        // Set first dot active
        fun setActiveDot(index: Int) {
            for (i in 0 until dotContainer.childCount) {
                val dot = dotContainer.getChildAt(i) as ImageView
                dot.setImageResource(if (i == index) R.drawable.active_dot else R.drawable.inactive_dot)
            }
        }
        setActiveDot(0)


        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollX = scrollView.scrollX
            val firstChild = logoContainer.getChildAt(0)
            val childWidth = firstChild.width + (firstChild.layoutParams as LinearLayout.LayoutParams).marginEnd

            val targetIndex = ((scrollX + childWidth / 2) / childWidth).toInt()
            if (targetIndex in 0 until logoContainer.childCount) {
                setActiveDot(targetIndex)
            }
        }

        // Snap scrolling on touch release
        scrollView.setOnTouchListener(object : View.OnTouchListener {
            var startX = 0f

            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        v?.performClick()
                    }

                    MotionEvent.ACTION_UP -> {
                        val scrollX = scrollView.scrollX
                        val firstChild = logoContainer.getChildAt(0)
                        val childWidth = firstChild.width + (firstChild.layoutParams as LinearLayout.LayoutParams).marginEnd

                        val targetIndex = ((scrollX + childWidth / 2) / childWidth).toInt()
                        val targetScroll = targetIndex * childWidth
                        scrollView.post {
                            scrollView.smoothScrollTo(targetScroll, 0)
                        }
                    }
                }
                return false
            }
        })

        // Auto-scroll runnable
        scrollRunnable = object : Runnable {
            override fun run() {
                val firstChild = logoContainer.getChildAt(0)
                val childWidth = firstChild.width + (firstChild.layoutParams as LinearLayout.LayoutParams).marginEnd
                val targetScroll = currentIndex * childWidth

                scrollView.post {
                    scrollView.smoothScrollTo(targetScroll, 0)
                }

                if (currentIndex < logoContainer.childCount - 1) {
                    currentIndex++
                    scrollView.postDelayed(this, scrollDelay)
                } else {
                    scrollView.removeCallbacks(scrollRunnable)
                }
            }
        }

        // Start auto-scroll
        scrollView.postDelayed(scrollRunnable, scrollDelay)

    }

    override fun onDestroy() {
        super.onDestroy()
        val scrollView = findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        scrollView.removeCallbacks(scrollRunnable)
    }

}