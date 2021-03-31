package com.bitlabs.newsletter.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitlabs.newsletter.R
import com.bitlabs.newsletter.adapter.NewsAdapter
import com.bitlabs.newsletter.helper.NewsletterDBHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    // Membuat method onCreate() agar activity dapat ditampilkan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        // menuInflater.inflate() -> Menghubungkan menu_home.xml kedalam HomeActivity

        return true
    }

    // Membuat method onOptionsItemSelected() agar saat memilih menu, menu tersebut akan melakukan aksi yang sesuai
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Saat memilih menu Create News, maka akan melakukan Intent ke CreateActivity
            R.id.menu_create -> {
                val intent = Intent(this, CreateActivity::class.java)
                startActivity(intent)
                true
            }
            // Saat memilih menu Profile, maka akan melakukan Intent ke ProfileActivity
            R.id.menu_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            // Saat memilih menu Logout, maka akan melakukan Intent ke RegisterActivity
            R.id.menu_logout -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Membuat function initRecycler untuk menampung data dari database ke dalam RecyclerView
    private fun initRecycler() {
        // Membuat objek db dulu agar bisa mengakses method getAllNews
        val db = NewsletterDBHelper(this)

        // Membuat variable adapter dengan argumen db.getAllNews dan this (context)
        val adapter = NewsAdapter(db.getAllNews(), this)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}