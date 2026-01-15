package com.example.donshare

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonasiListActivity : AppCompatActivity() {

    private lateinit var rvDonasi: RecyclerView
    private lateinit var btnBack: ImageView
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi_list)

        userId = intent.getIntExtra("USER_ID", 1)

        rvDonasi = findViewById(R.id.rvDonasi)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        rvDonasi.layoutManager = LinearLayoutManager(this)

        getDonasiData()
    }

    private fun getDonasiData() {
        RetrofitClient.instance.getAllDonasi().enqueue(object : Callback<DonasiResponse> {
            override fun onResponse(call: Call<DonasiResponse>, response: Response<DonasiResponse>) {
                if (response.isSuccessful) {
                    val listData = response.body()?.data ?: emptyList()

                    val adapter = DonasiAdapter(listData, userId)
                    rvDonasi.adapter = adapter
                } else {
                    Toast.makeText(this@DonasiListActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DonasiResponse>, t: Throwable) {
                Log.e("API_ERROR", t.message.toString())
                Toast.makeText(this@DonasiListActivity, "Koneksi Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}