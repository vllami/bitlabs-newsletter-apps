package com.bitlabs.newsletter.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitlabs.newsletter.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    // Membuat variable SharedPreferences..
    // Agar ketika melakukan register, datanya akan dimasukkan dalam SharedPreferences
    private var privateMode = 0
    private val prefName = "bitlabs"
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Memanggil function validate()
        btn_submit.setOnClickListener {
            // setOnClickListener -> Fungsi event listener yang ketika di-klik akan melakukan sesuatu
            // Fungsi ini dapat ditambahkan ke semua komponen, tidak hanya komponen Button saja

            // Ketika button Submit di-klik, maka akan melakukan pemanggilan terhadap function validate.. lalu
            // memvalidasi terhadap field yang ada di form
            validate()
        }

        // Assign value variable SharedPreferences
        sharedPref = this.getSharedPreferences(prefName, privateMode)
    }

    // Membuat function validasi name
    private fun validateName(): Boolean {
        return when {
            // et_name -> id dari komponen EditText field name
            et_name.text.trim().isEmpty() -> {
                /**
                 * Kondisi ini menghitung panjang karakter dari id et_name
                 * Properti .text untuk mendapatkan text
                 * Fungsi isEmpty() untuk mengecek apakah isi dari komponen et_name kosong atau tidak
                 */

                /**
                 * Jika kosong..
                 * Maka fungsi setText pada id tv_error_name dan berikan message-nya
                 */
                tv_error_name.setText(R.string.name_must_be_filled)

                // Lalu return false
                false
            }
            else -> {
                // Apabila validasi sudah terlewati semua, maka properti .text dengan null
                tv_error_name.text = null

                // Lalu return true
                true
            }
        }
    }

    // Membuat function validasi email
    private fun validateEmail(): Boolean {
        return when {
            et_email.text.trim().isEmpty() -> {
                tv_error_email.setText(R.string.email_must_be_filled)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(et_email.text).matches() -> {
                tv_error_email.setText(R.string.must_match_email_pattern)
                false
            }
            else -> {
                tv_error_email.text = null
                true
            }
        }
    }

    // Membuat function validasi password
    private fun validatePassword(): Boolean {
        return when {
            et_password.text.trim().isEmpty() -> {
                tv_error_password.setText(R.string.password_must_be_filled)
                false
            }
            et_password.text.length < 8 -> {
                tv_error_password.setText(R.string.password_must_be_7_characters_or_longer)
                false
            }
            else -> {
                tv_error_password.text = null
                true
            }
        }
    }

    // Membuat function validasi gender bahwa harus memilih salah satu
    private fun validateGender(): Boolean {
        return when (radio_group.checkedRadioButtonId) {
            /**
             * checkedRadioButtonId -> Properti untuk mengecek apakah didalam RadioGroup terdapat RadioButton
             * yang di checked/pilih atau tidak, value -1 artinya belum ada yang di checked/pilih
             */
            -1 -> {
                tv_error_gender.setText(R.string.gender_must_be_selected)
                false
            }
            else -> {
                tv_error_gender.text = null
                true
            }
        }
    }

    // Membuat function validasi semua jika semua validasi sudah dilewati, maka muncul Toast "Success"
    private fun validate() {
        if (validateName() && validateEmail() && validatePassword() && validateGender()) {
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            // Membuat SharedPreferences editor di dalam function validate
            val editor = sharedPref!!.edit()

            // Memberi nama key user-name dan user-email, serta value yang diambil dari masing-masing field
            editor.putString("user-name", et_name.text.toString())
            editor.putString("user-email", et_email.text.toString())
            editor.apply()
        }
    }
}