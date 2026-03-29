package com.example.registerapp2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tilNama: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var tvErrorGender: TextView
    private lateinit var tvErrorHobi: TextView

    private lateinit var cbMembaca: CheckBox
    private lateinit var cbOlahraga: CheckBox
    private lateinit var cbMusik: CheckBox
    private lateinit var cbMasak: CheckBox
    private lateinit var cbGaming: CheckBox
    private lateinit var cbTravel: CheckBox

    private lateinit var spinnerKota: Spinner
    private lateinit var btnRegister: com.google.android.material.button.MaterialButton
    private lateinit var btnReset: com.google.android.material.button.MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupSpinner()
        setupRealTimeValidation()
        setupButtons()
    }

    private fun initViews() {
        tilNama            = findViewById(R.id.tilNama)
        tilEmail           = findViewById(R.id.tilEmail)
        tilPassword        = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)
        etNama             = findViewById(R.id.etNama)
        etEmail            = findViewById(R.id.etEmail)
        etPassword         = findViewById(R.id.etPassword)
        etConfirmPassword  = findViewById(R.id.etConfirmPassword)
        rgJenisKelamin     = findViewById(R.id.rgJenisKelamin)
        tvErrorGender      = findViewById(R.id.tvErrorGender)
        tvErrorHobi        = findViewById(R.id.tvErrorHobi)
        cbMembaca          = findViewById(R.id.cbMembaca)
        cbOlahraga         = findViewById(R.id.cbOlahraga)
        cbMusik            = findViewById(R.id.cbMusik)
        cbMasak            = findViewById(R.id.cbMasak)
        cbGaming           = findViewById(R.id.cbGaming)
        cbTravel           = findViewById(R.id.cbTravel)
        spinnerKota        = findViewById(R.id.spinnerKota)
        btnRegister        = findViewById(R.id.btnRegister)
        btnReset           = findViewById(R.id.btnReset)
    }

    private fun setupSpinner() {
        val kotaList = arrayOf(
            "-- Pilih Kota --", "Bandung", "Jakarta",
            "Surabaya", "Yogyakarta", "Medan",
            "Makassar", "Semarang", "Palembang",
            "Denpasar", "Malang"
        )

        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            kotaList
        ) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text = view.findViewById<TextView>(android.R.id.text1)
                text.setTextColor(android.graphics.Color.parseColor("#333333"))
                text.textSize = 14f
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val text = view.findViewById<TextView>(android.R.id.text1)
                text.setTextColor(android.graphics.Color.parseColor("#333333"))
                text.setPadding(16, 16, 16, 16)
                text.textSize = 14f
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKota.adapter = adapter
    }
    private fun setupRealTimeValidation() {
        etNama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                tilNama.error = if (s.toString().trim().isEmpty()) "Nama tidak boleh kosong" else null
            }
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                tilEmail.error = when {
                    email.isEmpty() -> "Email tidak boleh kosong"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Format email tidak valid"
                    else -> null
                }
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val pass = s.toString()
                tilPassword.error = when {
                    pass.isEmpty() -> "Password tidak boleh kosong"
                    pass.length < 8 -> "Password minimal 8 karakter"
                    else -> null
                }
                val confirm = etConfirmPassword.text.toString()
                if (confirm.isNotEmpty()) {
                    tilConfirmPassword.error = if (confirm != pass) "Password tidak cocok" else null
                }
            }
        })

        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val confirm = s.toString()
                val pass = etPassword.text.toString()
                tilConfirmPassword.error = when {
                    confirm.isEmpty() -> "Konfirmasi password tidak boleh kosong"
                    confirm != pass -> "Password tidak cocok"
                    else -> null
                }
            }
        })
    }

    private fun setupButtons() {
        btnRegister.setOnClickListener {
            if (validateForm()) showConfirmationDialog()
        }
        btnRegister.setOnLongClickListener {
            showLongPressDialog()
            true
        }
        btnReset.setOnClickListener {
            resetForm()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val nama = etNama.text.toString().trim()
        if (nama.isEmpty()) { tilNama.error = "Nama tidak boleh kosong"; isValid = false }
        else tilNama.error = null

        val email = etEmail.text.toString().trim()
        when {
            email.isEmpty() -> { tilEmail.error = "Email tidak boleh kosong"; isValid = false }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> { tilEmail.error = "Format email tidak valid"; isValid = false }
            else -> tilEmail.error = null
        }

        val password = etPassword.text.toString()
        when {
            password.isEmpty() -> { tilPassword.error = "Password tidak boleh kosong"; isValid = false }
            password.length < 8 -> { tilPassword.error = "Password minimal 8 karakter"; isValid = false }
            else -> tilPassword.error = null
        }

        val confirmPass = etConfirmPassword.text.toString()
        when {
            confirmPass.isEmpty() -> { tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"; isValid = false }
            confirmPass != password -> { tilConfirmPassword.error = "Password tidak cocok"; isValid = false }
            else -> tilConfirmPassword.error = null
        }

        if (rgJenisKelamin.checkedRadioButtonId == -1) {
            tvErrorGender.visibility = View.VISIBLE
            isValid = false
        } else {
            tvErrorGender.visibility = View.GONE
        }

        var hobiCount = 0
        if (cbMembaca.isChecked)  hobiCount++
        if (cbOlahraga.isChecked) hobiCount++
        if (cbMusik.isChecked)    hobiCount++
        if (cbMasak.isChecked)    hobiCount++
        if (cbGaming.isChecked)   hobiCount++
        if (cbTravel.isChecked)   hobiCount++

        if (hobiCount < 3) {
            tvErrorHobi.visibility = View.VISIBLE
            isValid = false
        } else {
            tvErrorHobi.visibility = View.GONE
        }

        if (spinnerKota.selectedItemPosition == 0) {
            Toast.makeText(this, "Silakan pilih kota asal", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun showConfirmationDialog() {
        val nama   = etNama.text.toString().trim()
        val email  = etEmail.text.toString().trim()
        val gender = findViewById<RadioButton>(rgJenisKelamin.checkedRadioButtonId).text.toString()
        val kota   = spinnerKota.selectedItem.toString()

        val hobiList = mutableListOf<String>()
        if (cbMembaca.isChecked)  hobiList.add("Membaca")
        if (cbOlahraga.isChecked) hobiList.add("Olahraga")
        if (cbMusik.isChecked)    hobiList.add("Musik")
        if (cbMasak.isChecked)    hobiList.add("Memasak")
        if (cbGaming.isChecked)   hobiList.add("Gaming")
        if (cbTravel.isChecked)   hobiList.add("Travelling")

        val message = """
            Nama   : $nama
            Email  : $email
            Gender : $gender
            Kota   : $kota
            Hobi   : ${hobiList.joinToString(", ")}
            
            Apakah data sudah benar?
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Pendaftaran")
            .setMessage(message)
            .setPositiveButton("Ya, Daftar") { _, _ ->
                Toast.makeText(this, "Registrasi berhasil! Selamat datang, $nama!", Toast.LENGTH_LONG).show()
                resetForm()
            }
            .setNegativeButton("Periksa Lagi", null)
            .show()
    }

    private fun showLongPressDialog() {
        AlertDialog.Builder(this)
            .setTitle("Info Pendaftaran")
            .setMessage(
                "Syarat pendaftaran:\n\n" +
                        "✓ Nama lengkap sesuai KTP\n" +
                        "✓ Email aktif yang bisa dihubungi\n" +
                        "✓ Password minimal 8 karakter\n" +
                        "✓ Pilih minimal 3 hobi\n\n" +
                        "Tekan tombol DAFTAR SEKARANG untuk melanjutkan."
            )
            .setPositiveButton("Mengerti", null)
            .show()
    }

    private fun resetForm() {
        etNama.setText("")
        etEmail.setText("")
        etPassword.setText("")
        etConfirmPassword.setText("")
        tilNama.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilConfirmPassword.error = null
        rgJenisKelamin.clearCheck()
        tvErrorGender.visibility = View.GONE
        cbMembaca.isChecked  = false
        cbOlahraga.isChecked = false
        cbMusik.isChecked    = false
        cbMasak.isChecked    = false
        cbGaming.isChecked   = false
        cbTravel.isChecked   = false
        tvErrorHobi.visibility = View.GONE
        spinnerKota.setSelection(0)
        Toast.makeText(this, "Form berhasil direset", Toast.LENGTH_SHORT).show()
    }
}