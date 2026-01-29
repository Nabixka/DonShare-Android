package com.example.donshare

import android.content.Intent
import android.os.Bundle
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

class ManagePaymentMethodActivity : AppCompatActivity() {

    private lateinit var rvMethods: RecyclerView
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_manage_payment_method)

        rvMethods = findViewById(R.id.rvPayment)
        rvMethods.layoutManager = LinearLayoutManager(this)

        userId = intent.getIntExtra("USER_ID", -1)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }


        if (userId == -1) {
            Toast.makeText(this, "User tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchMethods()
    }

    private fun fetchMethods() {
        RetrofitClient.instance.getAllPaymentMethod()
            .enqueue(object : Callback<PaymentMethodResponse> {

                override fun onResponse(
                    call: Call<PaymentMethodResponse>,
                    response: Response<PaymentMethodResponse>
                ) {
                    if (response.isSuccessful) {
                        val methods = response.body()?.data ?: emptyList()

                        rvMethods.adapter =
                            PaymentMethodListAdapter(methods) { selected ->

                                val intent = Intent(
                                    this@ManagePaymentMethodActivity,
                                    createMethodPaymentActivity::class.java
                                )
                                intent.putExtra("USER_ID", userId)
                                intent.putExtra("PAYMENT_METHOD_ID", selected.id)
                                intent.putExtra("PAYMENT_IMAGE", selected.image)
                                intent.putExtra("PAYMENT_NAME", selected.name)
                                startActivity(intent)
                            }

                    } else {
                        Toast.makeText(
                            this@ManagePaymentMethodActivity,
                            "Gagal memuat data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PaymentMethodResponse>, t: Throwable) {
                    Toast.makeText(
                        this@ManagePaymentMethodActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
