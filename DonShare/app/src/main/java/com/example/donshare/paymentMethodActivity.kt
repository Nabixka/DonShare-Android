package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class paymentMethodActivity : AppCompatActivity() {

    private lateinit var rvPayment: RecyclerView
    private lateinit var btnBack: ImageView
    private var eventId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment_method)

        eventId = intent.getIntExtra("EVENT_ID", -1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPayment = findViewById(R.id.rv_payment_method)
        btnBack = findViewById(R.id.back)

        btnBack.setOnClickListener { finish() }

        rvPayment.layoutManager = LinearLayoutManager(this)

        fetchUserPayments()
    }

    private fun fetchUserPayments() {
        val userId = 1

        RetrofitClient.instance.getUserPayments(userId).enqueue(object : Callback<UserPaymentResponse> {
            override fun onResponse(
                call: Call<UserPaymentResponse>,
                response: Response<UserPaymentResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data ?: emptyList()

                    if (data.isNotEmpty()) {
                        val adapter = PaymentMethodAdapter(data) { selectedItem ->
                            val intent = Intent(this@paymentMethodActivity, NominalActivity::class.java)
                            intent.putExtra("EVENT_ID", eventId)
                            intent.putExtra("USER_PAYMENT_ID", selectedItem.id) // ID User Payment
                            intent.putExtra("METHOD_NAME", selectedItem.paymentMethod.name)
                            startActivity(intent)
                        }
                        rvPayment.adapter = adapter
                    } else {
                        Toast.makeText(this@paymentMethodActivity, "Anda belum memiliki metode pembayaran", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@paymentMethodActivity, "Gagal mengambil data: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserPaymentResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error: ${t.message}")
                Toast.makeText(this@paymentMethodActivity, "Koneksi bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }
}