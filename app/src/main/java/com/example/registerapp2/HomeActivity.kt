package com.example.registerapp2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userName = intent.getStringExtra("USER_NAME") ?: "Mahasiswa"
        findViewById<TextView>(R.id.tvWelcome).text = "Selamat datang, $userName!"

        findViewById<MaterialButton>(R.id.btnDaftarSeminar).setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            startActivity(intent)
        }
    }
}
