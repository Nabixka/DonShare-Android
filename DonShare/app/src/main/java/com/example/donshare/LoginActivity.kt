package com.example.donshare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtSignUp = findViewById<TextView>(R.id.txtSignUp)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val editEmail = findViewById<EditText>(R.id.editEmailLogin)
        val editPassword = findViewById<EditText>(R.id.editPasswordLogin)

        txtSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi email dan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(email, password)

            RetrofitClient.instance.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()

                        if (loginResponse != null && (loginResponse.status == 200 || loginResponse.status == 201)) {
                            Toast.makeText(this@LoginActivity, "Selamat Datang!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("USER_ID", loginResponse.data.id)
                            startActivity(intent)
                            finish()
                        } else {

                            Toast.makeText(this@LoginActivity, loginResponse?.message ?: "Gagal Login", Toast.LENGTH_SHORT).show()
                        }
                    } else {

                        try {
                            val errorRaw = response.errorBody()?.string()
                            val jsonObject = JSONObject(errorRaw)
                            val message = jsonObject.getString("message")
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@LoginActivity, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Toast.makeText(this@LoginActivity, "Koneksi ke server gagal: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}