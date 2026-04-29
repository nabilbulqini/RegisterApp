package com.example.registerapp2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormActivity : AppCompatActivity() {

    private lateinit var tilNama: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilNoHp: TextInputLayout
    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etNoHp: TextInputEditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var tvErrorGender: TextView
    private lateinit var spinnerSeminar: Spinner
    private lateinit var cbSetuju: MaterialCheckBox
    private lateinit var tvErrorCheckbox: TextView
    private lateinit var btnSubmit: MaterialButton
    private lateinit var btnReset: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initViews()
        setupSpinner()
        setupRealTimeValidation()
        setupButtons()
    }

    private fun initViews() {
        tilNama          = findViewById(R.id.tilNama)
        tilEmail         = findViewById(R.id.tilEmail)
        tilNoHp          = findViewById(R.id.tilNoHp)
        etNama           = findViewById(R.id.etNama)
        etEmail          = findViewById(R.id.etEmail)
        etNoHp           = findViewById(R.id.etNoHp)
        rgJenisKelamin   = findViewById(R.id.rgJenisKelamin)
        tvErrorGender    = findViewById(R.id.tvErrorGender)
        spinnerSeminar   = findViewById(R.id.spinnerSeminar)
        cbSetuju         = findViewById(R.id.cbSetuju)
        tvErrorCheckbox  = findViewById(R.id.tvErrorCheckbox)
        btnSubmit        = findViewById(R.id.btnSubmit)
        btnReset         = findViewById(R.id.btnReset)
    }

    private fun setupSpinner() {
        val seminarList = arrayOf(
            "-- Pilih Seminar --",
            "Seminar AI & Machine Learning",
            "Seminar Keamanan Siber",
            "Seminar Mobile Development",
            "Seminar Cloud Computing",
            "Seminar UI/UX Design",
            "Seminar Big Data & Analytics",
            "Seminar Internet of Things"
        )

        val adapter = object : ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, seminarList
        ) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                view.findViewById<TextView>(android.R.id.text1).apply {
                    setTextColor(android.graphics.Color.parseColor("#333333"))
                    textSize = 14f
                }
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                view.findViewById<TextView>(android.R.id.text1).apply {
                    setTextColor(android.graphics.Color.parseColor("#333333"))
                    setPadding(16, 16, 16, 16)
                    textSize = 14f
                }
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeminar.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        // Validasi Nama
        etNama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                tilNama.error = if (s.toString().trim().isEmpty()) "Nama tidak boleh kosong" else null
            }
        })

        // Validasi Email
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                tilEmail.error = when {
                    email.isEmpty() -> "Email tidak boleh kosong"
                    !email.contains("@") -> "Email harus mengandung @"
                    else -> null
                }
            }
        })

        // Validasi Nomor HP
        etNoHp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val hp = s.toString().trim()
                tilNoHp.error = when {
                    hp.isEmpty() -> "Nomor HP tidak boleh kosong"
                    !hp.all { it.isDigit() } -> "Nomor HP hanya boleh angka"
                    !hp.startsWith("08") -> "Nomor HP harus diawali 08"
                    hp.length < 10 || hp.length > 13 -> "Nomor HP harus 10-13 digit"
                    else -> null
                }
            }
        })
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val nama = etNama.text.toString().trim()
        if (nama.isEmpty()) { tilNama.error = "Nama tidak boleh kosong"; isValid = false }
        else tilNama.error = null

        val email = etEmail.text.toString().trim()
        when {
            email.isEmpty() -> { tilEmail.error = "Email tidak boleh kosong"; isValid = false }
            !email.contains("@") -> { tilEmail.error = "Email harus mengandung @"; isValid = false }
            else -> tilEmail.error = null
        }

        val hp = etNoHp.text.toString().trim()
        when {
            hp.isEmpty() -> { tilNoHp.error = "Nomor HP tidak boleh kosong"; isValid = false }
            !hp.all { it.isDigit() } -> { tilNoHp.error = "Nomor HP hanya boleh angka"; isValid = false }
            !hp.startsWith("08") -> { tilNoHp.error = "Nomor HP harus diawali 08"; isValid = false }
            hp.length < 10 || hp.length > 13 -> { tilNoHp.error = "Nomor HP harus 10-13 digit"; isValid = false }
            else -> tilNoHp.error = null
        }

        if (rgJenisKelamin.checkedRadioButtonId == -1) {
            tvErrorGender.visibility = View.VISIBLE; isValid = false
        } else {
            tvErrorGender.visibility = View.GONE
        }

        if (spinnerSeminar.selectedItemPosition == 0) {
            Toast.makeText(this, "Pilih seminar terlebih dahulu", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (!cbSetuju.isChecked) {
            tvErrorCheckbox.visibility = View.VISIBLE; isValid = false
        } else {
            tvErrorCheckbox.visibility = View.GONE
        }

        return isValid
    }

    private fun setupButtons() {
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                showConfirmDialog()
            }
        }

        btnReset.setOnClickListener { resetForm() }
    }

    private fun showConfirmDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage("Apakah data yang Anda isi sudah benar?")
            .setPositiveButton("Ya") { _, _ -> goToResult() }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun goToResult() {
        val gender = findViewById<RadioButton>(rgJenisKelamin.checkedRadioButtonId).text.toString()
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("NAMA", etNama.text.toString().trim())
            putExtra("EMAIL", etEmail.text.toString().trim())
            putExtra("NO_HP", etNoHp.text.toString().trim())
            putExtra("GENDER", gender)
            putExtra("SEMINAR", spinnerSeminar.selectedItem.toString())
        }
        startActivity(intent)
    }

    private fun resetForm() {
        etNama.setText("")
        etEmail.setText("")
        etNoHp.setText("")
        tilNama.error = null
        tilEmail.error = null
        tilNoHp.error = null
        rgJenisKelamin.clearCheck()
        tvErrorGender.visibility = View.GONE
        spinnerSeminar.setSelection(0)
        cbSetuju.isChecked = false
        tvErrorCheckbox.visibility = View.GONE
        Toast.makeText(this, "Form berhasil direset", Toast.LENGTH_SHORT).show()
    }
}
