package com.bitlabs.newsletter.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bitlabs.newsletter.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    // Membuat variable SharedPreferences..
    // Agar ketika melakukan register, datanya akan dimasukkan dalam SharedPreferences
    private var privateMode = 0
    private val prefName = "bitlabs"
    private var sharedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Glide.with(this)
            .load("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png")
            .into(img_profile)

        sharedPrefs = this.getSharedPreferences(prefName, privateMode)
        user_profile_name.text = sharedPrefs!!.getString("user-name", "")
        user_profile_email.text = sharedPrefs!!.getString("user-email", "")
    }
}