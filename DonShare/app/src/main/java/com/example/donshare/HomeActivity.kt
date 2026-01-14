package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userId = intent.getIntExtra("USER_ID", -1)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerPromo)

        val listGambar = listOf(
            R.drawable.donasi1,
            R.drawable.donasi2,
            R.drawable.donasi3
        )

        viewPager.adapter = SliderAdapter(listGambar)

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                var current = viewPager.currentItem + 1
                if (current >= listGambar.size) current = 0
                viewPager.setCurrentItem(current, true)
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
                    startActivity(
                        Intent(this, ProfilActivity::class.java)
                            .putExtra("USER_ID", userId)
                    )
                    true
                }
                else -> false
            }
        }

        val cardDonasi = findViewById<MaterialCardView>(R.id.cardDonasi)
        cardDonasi.setOnClickListener {
            startActivity(
                Intent(this, DonasiListActivity::class.java)
                    .putExtra("USER_ID", userId)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
