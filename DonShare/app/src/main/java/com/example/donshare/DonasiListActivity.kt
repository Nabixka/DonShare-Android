package com.example.donshare

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonasiListActivity : AppCompatActivity() {

    private lateinit var rvDonasi: RecyclerView
    private lateinit var adapter: DonasiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi_list)

        rvDonasi = findViewById(R.id.rvDonasi)
        rvDonasi.layoutManager = LinearLayoutManager(this)

        fetchDataDonasi()
    }

    private fun fetchDataDonasi() {
        RetrofitClient.instance.getAllDonasi().enqueue(object : Callback<DonasiResponse> {
            override fun onResponse(call: Call<DonasiResponse>, response: Response<DonasiResponse>) {
                if (response.isSuccessful) {
                    val listData = response.body()?.data ?: emptyList()
                    adapter = DonasiAdapter(listData)
                    rvDonasi.adapter = adapter
                } else {
                    Toast.makeText(this@DonasiListActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DonasiResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error: ${t.message}")
                Toast.makeText(this@DonasiListActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }
}