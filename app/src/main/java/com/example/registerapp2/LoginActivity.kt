package com.example.registerapp2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnGoRegister: MaterialButton

    // Data login
    private val validEmail = "nabil@gmail.com"
    private val validPassword = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupRealTimeValidation()
        setupButtons()
    }

    private fun initViews() {
        tilEmail    = findViewById(R.id.tilLoginEmail)
        tilPassword = findViewById(R.id.tilLoginPassword)
        etEmail     = findViewById(R.id.etLoginEmail)
        etPassword  = findViewById(R.id.etLoginPassword)
        btnLogin      = findViewById(R.id.btnLogin)
        btnGoRegister = findViewById(R.id.btnGoRegister)
    }

    private fun setupRealTimeValidation() {
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                tilEmail.error = when {
                    email.isEmpty() -> "Email tidak boleh kosong"
                    !email.contains("@") -> "Format email tidak valid"
                    else -> null
                }
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                tilPassword.error = if (s.toString().isEmpty()) "Password tidak boleh kosong" else null
            }
        })
    }

    private fun setupButtons() {
        btnLogin.setOnClickListener {
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty()) { tilEmail.error = "Email tidak boleh kosong"; return@setOnClickListener }
            if (!email.contains("@")) { tilEmail.error = "Format email tidak valid"; return@setOnClickListener }
            if (password.isEmpty()) { tilPassword.error = "Password tidak boleh kosong"; return@setOnClickListener }

            if (email == validEmail && password == validPassword) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_NAME", "Nabil Bulqini")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoRegister.setOnClickListener {
            Toast.makeText(this, "Gunakan: $validEmail / $validPassword", Toast.LENGTH_LONG).show()
        }
    }
}
