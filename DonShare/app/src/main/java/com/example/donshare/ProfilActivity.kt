package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.util.Log // Tambahkan Log untuk debugging
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilActivity : AppCompatActivity() {

    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        txtName = findViewById(R.id.txtProfileName)
        txtEmail = findViewById(R.id.txtProfileEmail)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnEdit = findViewById<Button>(R.id.btnEditProfile)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val btnManagePayment = findViewById<MaterialButton>(R.id.btnManagePayment)

        val userId = intent.getIntExtra("USER_ID", -1)

        btnManagePayment.setOnClickListener {
            val intent = Intent(this, ManagePaymentMethodActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent) 
        }

        Log.d("PROFIL_DEBUG", "User ID yang diterima: $userId")

        if (userId != -1) {
            fetchData(userId)
        } else {
            Toast.makeText(this, "ID User tidak ditemukan!", Toast.LENGTH_SHORT).show()
        }

        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("USER_ID", userId)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnEdit.setOnClickListener {
            Toast.makeText(this, "Fitur Edit segera hadir", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchData(id: Int) {
        RetrofitClient.instance.getUserProfile(id).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("PROFIL_DEBUG", "Response Success: ${body?.message}")

                    val userData = body?.data
                    if (userData != null) {
                        // UPDATE UI DI SINI
                        txtName.text = userData.name
                        txtEmail.text = userData.email
                    } else {
                        Log.e("PROFIL_DEBUG", "Data null meskipun sukses")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("PROFIL_DEBUG", "Response Error: $errorBody")
                    Toast.makeText(this@ProfilActivity, "Gagal ambil data dari server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("PROFIL_DEBUG", "Koneksi Gagal: ${t.message}")
                Toast.makeText(this@ProfilActivity, "Masalah Jaringan!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}