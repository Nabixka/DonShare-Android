package com.example.donshare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class createMethodPaymentActivity : AppCompatActivity() {

    private var userId = -1
    private var paymentMethodId = -1
    private var paymentImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_method_payment)

        val imgHeader = findViewById<ImageView>(R.id.imgHeader)
        val etNumber = findViewById<EditText>(R.id.etAccountNumber)
        val btnSave = findViewById<Button>(R.id.btnSavePayment)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        userId = intent.getIntExtra("USER_ID", -1)
        paymentMethodId = intent.getIntExtra("PAYMENT_METHOD_ID", -1)
        paymentImage = intent.getStringExtra("PAYMENT_IMAGE")

        if (userId == -1 || paymentMethodId == -1) {
            Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Glide.with(this)
            .load(paymentImage)
            .placeholder(R.drawable.coin)
            .error(R.drawable.coin)
            .into(imgHeader)

        btnBack.setOnClickListener { finish() }

        btnSave.setOnClickListener {
            val number = etNumber.text.toString().trim()

            if (number.isEmpty()) {
                etNumber.error = "Nomor wajib diisi"
                return@setOnClickListener
            }

            createUserPayment(number)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createUserPayment(number: String) {
        val request = CreateUserPaymentRequest(
            user_id = userId,
            payment_method_id = paymentMethodId,
            nomor = number
        )

        RetrofitClient.instance.createUserPayment(request)
            .enqueue(object : Callback<CreateUserPaymentResponse> {

                override fun onResponse(
                    call: Call<CreateUserPaymentResponse>,
                    response: Response<CreateUserPaymentResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@createMethodPaymentActivity,
                            "Payment berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@createMethodPaymentActivity,
                            "Gagal menyimpan payment",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CreateUserPaymentResponse>, t: Throwable) {
                    Toast.makeText(
                        this@createMethodPaymentActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
