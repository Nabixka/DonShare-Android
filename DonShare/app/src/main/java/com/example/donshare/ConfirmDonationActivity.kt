package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale


class ConfirmDonationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_donation)

        val userId = intent.getIntExtra("USER_ID", -1)
        val eventId = intent.getIntExtra("EVENT_ID", -1)
        val userPaymentId = intent.getIntExtra("USER_PAYMENT_ID", -1)
        val amount = intent.getLongExtra("AMOUNT", 0L)
        val methodName = intent.getStringExtra("METHOD_NAME") ?: "Metode Pembayaran"
        val eventName = intent.getStringExtra("EVENT_NAME") ?: "Tujuan Donasi"

        val tvEventName = findViewById<TextView>(R.id.tvEventName)
        val tvNominal = findViewById<TextView>(R.id.tvNominal)
        val tvPaymentMethod = findViewById<TextView>(R.id.tvPaymentMethod)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnDonate = findViewById<MaterialButton>(R.id.btnDonate)
        val btnBatal = findViewById<TextView>(R.id.btnBatal)

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        val formattedAmount = formatRupiah.format(amount).replace("Rp", "Rp ")

        tvEventName.text = eventName
        tvNominal.text = formattedAmount
        tvPaymentMethod.text = methodName
        tvTotal.text = formattedAmount

        btnDonate.setOnClickListener {

            if (userId == -1 || eventId == -1 || userPaymentId == -1 || amount <= 0) {
                Toast.makeText(this, "Data transaksi tidak lengkap!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            simpanDonasiKeServer(userId, eventId, userPaymentId, amount)
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }

    private fun simpanDonasiKeServer(uId: Int, eId: Int, pId: Int, amt: Long) {
        Toast.makeText(this, "Sedang memproses donasi...", Toast.LENGTH_SHORT).show()

        val request = DonasiRequest(
            user_id = uId,
            event_id = eId,
            user_payment_id = pId,
            amount = amt
        )

        RetrofitClient.instance.createDonasi(request).enqueue(object : Callback<CreateDonasiResponse> {
            override fun onResponse(call: Call<CreateDonasiResponse>, response: Response<CreateDonasiResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status == 201) {
                        Toast.makeText(this@ConfirmDonationActivity, "Berhasil Berdonasi!", Toast.LENGTH_LONG).show()

                        val intent = Intent(this@ConfirmDonationActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@ConfirmDonationActivity, "Gagal: ${body?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ConfirmDonationActivity, "Error Server: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CreateDonasiResponse>, t: Throwable) {
                Toast.makeText(this@ConfirmDonationActivity, "Koneksi Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}