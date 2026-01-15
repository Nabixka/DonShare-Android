package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class paymentMethodActivity : AppCompatActivity() {

    private lateinit var rvPayment: RecyclerView
    private lateinit var btnBack: ImageView
    private var eventId: Int = -1
    private var userId: Int = -1
    private var eventName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        eventId = intent.getIntExtra("EVENT_ID", -1)
        userId = intent.getIntExtra("USER_ID", -1)
        eventName = intent.getStringExtra("EVENT_NAME")

        rvPayment = findViewById(R.id.rv_payment_method)
        btnBack = findViewById(R.id.back)
        btnBack.setOnClickListener { finish() }

        rvPayment.layoutManager = LinearLayoutManager(this)

        if (userId != -1) {
            fetchUserPayments(userId)
        } else {
            Toast.makeText(this, "User ID tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUserPayments(uid: Int) {
        RetrofitClient.instance.getUserPayments(uid).enqueue(object : Callback<UserPaymentResponse> {
            override fun onResponse(call: Call<UserPaymentResponse>, response: Response<UserPaymentResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data ?: emptyList()

                    val adapter = PaymentMethodAdapter(data) { selected ->
                        val intent = Intent(this@paymentMethodActivity, NominalActivity::class.java)
                        intent.putExtra("EVENT_ID", eventId)
                        intent.putExtra("EVENT_NAME", eventName)
                        intent.putExtra("USER_ID", userId)
                        intent.putExtra("PAYMENT_ID", selected.id)
                        intent.putExtra("METHOD_NAME", selected.paymentMethod.name)
                        startActivity(intent)
                    }
                    rvPayment.adapter = adapter
                }
            }

            override fun onFailure(call: Call<UserPaymentResponse>, t: Throwable) {
                Toast.makeText(this@paymentMethodActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}