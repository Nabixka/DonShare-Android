package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.*

class NominalActivity : AppCompatActivity() {

    private lateinit var etNominal: EditText
    private var eventId = -1
    private var userPaymentId = -1
    private var userId = -1
    private var eventName: String? = null
    private var methodName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nominal)

        userId = intent.getIntExtra("USER_ID", -1)
        eventId = intent.getIntExtra("EVENT_ID", -1)
        userPaymentId = intent.getIntExtra("PAYMENT_ID", -1)

        eventName = intent.getStringExtra("EVENT_NAME")
        methodName = intent.getStringExtra("METHOD_NAME")

        Log.d("NOMINAL_DATA", "User: $userId, Event: $eventId, PayID: $userPaymentId, Name: $eventName")

        try {
            etNominal = findViewById(R.id.etNominal)
            val btnLanjut = findViewById<Button>(R.id.btnLanjut)
            val btnBack = findViewById<ImageView>(R.id.btnBack)

            findViewById<Button>(R.id.btn50k).setOnClickListener { setNominal(50000) }
            findViewById<Button>(R.id.btn100k).setOnClickListener { setNominal(100000) }
            findViewById<Button>(R.id.btn200k).setOnClickListener { setNominal(200000) }
            findViewById<Button>(R.id.btn500k).setOnClickListener { setNominal(500000) }
            findViewById<Button>(R.id.btn1jt).setOnClickListener { setNominal(1000000) }
            findViewById<Button>(R.id.btn5jt).setOnClickListener { setNominal(5000000) }

            btnBack.setOnClickListener { finish() }

            btnLanjut.setOnClickListener {
                val cleanString = etNominal.text.toString().replace(Regex("[^0-9]"), "")

                if (cleanString.isEmpty()) {
                    Toast.makeText(this, "Masukkan nominal", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val intent = Intent(this, ConfirmDonationActivity::class.java).apply {
                    putExtra("USER_ID", userId)
                    putExtra("EVENT_ID", eventId)
                    putExtra("EVENT_NAME", eventName)
                    putExtra("USER_PAYMENT_ID", userPaymentId)
                    putExtra("METHOD_NAME", methodName)
                    putExtra("METHOD_IMAGE", intent.getStringExtra("METHOD_IMAGE"))
                    putExtra("ACCOUNT_NUMBER", intent.getStringExtra("ACCOUNT_NUMBER"))
                    putExtra("AMOUNT", cleanString.toLong())
                }
                startActivity(intent)
            }
        } catch (e: Exception) {
            Log.e("NOMINAL_ERROR", "Ada ID View yang salah di XML: ${e.message}")
            Toast.makeText(this, "Gagal memuat halaman: ID View salah", Toast.LENGTH_LONG).show()
        }
    }

    private fun setNominal(value: Long) {
        val formatter = NumberFormat.getInstance(Locale("id", "ID"))
        etNominal.setText(formatter.format(value))
        etNominal.setSelection(etNominal.text.length)
    }
}