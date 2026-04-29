package com.example.registerapp2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val nama    = intent.getStringExtra("NAMA") ?: "-"
        val email   = intent.getStringExtra("EMAIL") ?: "-"
        val noHp    = intent.getStringExtra("NO_HP") ?: "-"
        val gender  = intent.getStringExtra("GENDER") ?: "-"
        val seminar = intent.getStringExtra("SEMINAR") ?: "-"

        findViewById<TextView>(R.id.tvResultNama).text    = nama
        findViewById<TextView>(R.id.tvResultEmail).text   = email
        findViewById<TextView>(R.id.tvResultNoHp).text    = noHp
        findViewById<TextView>(R.id.tvResultGender).text  = gender
        findViewById<TextView>(R.id.tvResultSeminar).text = seminar

        findViewById<MaterialButton>(R.id.btnBackHome).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}
