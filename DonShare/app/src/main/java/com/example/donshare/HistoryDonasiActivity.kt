package com.example.donshare

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryDonasiActivity : AppCompatActivity() {

    private lateinit var rvHistory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_donasi)

        rvHistory = findViewById(R.id.rvHistory)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        rvHistory.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener {
            finish()
        }

        val userId = intent.getIntExtra("USER_ID", -1)

        if (userId != -1) {
            getHistoryData(userId)
        } else {
            Toast.makeText(this, "User ID tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getHistoryData(userId: Int) {
        RetrofitClient.instance.getHistoryDonasi(userId).enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                if (response.isSuccessful) {
                    val listDonasi = response.body()?.data?.donations ?: emptyList()

                    if (listDonasi.isNotEmpty()) {
                        rvHistory.adapter = HistoryAdapter(listDonasi)
                    } else {
                        Toast.makeText(this@HistoryDonasiActivity, "Riwayat donasi kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@HistoryDonasiActivity, "Gagal mengambil data: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Toast.makeText(this@HistoryDonasiActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}