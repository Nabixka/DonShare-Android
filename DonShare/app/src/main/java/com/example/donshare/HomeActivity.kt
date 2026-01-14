package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val userId = intent.getIntExtra("USER_ID", -1)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerPromo)
        val listGambar = listOf(
            R.drawable.donasi1,
            R.drawable.donasi2,
            R.drawable.donasi3
        )

        val adapter = SliderAdapter(listGambar)
        viewPager.adapter = adapter

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                var currentItem = viewPager.currentItem
                currentItem++
                if (currentItem >= listGambar.size) {
                    currentItem = 0
                }
                viewPager.setCurrentItem(currentItem, true)
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfilActivity::class.java)

                    intent.putExtra("USER_ID", userId)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}