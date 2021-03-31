package com.bitlabs.newsletter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bitlabs.newsletter.R
import com.bitlabs.newsletter.entity.News
import com.bitlabs.newsletter.helper.NewsletterDBHelper
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {
    // Buat dulu objek db-nya
    private val db = NewsletterDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        /**
         * Membuat kondisi kapan menggunakan insert dan kapan menggunakan update, karena untuk update menggunakan
         * Activity yang sama dengan insert yaitu CreateActivity
         *
         * Membaca kondisi if..
         * Ketika intent key isinya kosong, maka method yang dipanggil adalah insert()
         * Namun, jika intent key tidak kosong.. maka method yang dipanggil adalah update()
         */
        when (intent.getIntExtra("id-extra", 0)) {
            0 -> {
                // Method insert hanya akan dipanggil ketika mengklik button Insert News pada Activity ini
                btn_insert.setOnClickListener {
                    // Memasukkan function insert kedalam setOnClickListener
                    insert()

                    // Memberi Toast message
                    Toast.makeText(this, "Success Insert Data", Toast.LENGTH_SHORT).show()

                    // Memberi intent ke HomeActivity
                    val intent = Intent(this@CreateActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
            else -> {
                tv_title_create.setText(R.string.update_news)
                et_input_title.setText(intent.getStringExtra("title-extra"))
                et_input_body.setText(intent.getStringExtra("body-extra"))
                et_input_date.setText(intent.getStringExtra("date-extra"))

                btn_insert.setOnClickListener {
                    update()
                    Toast.makeText(this, "Success Update Data", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@CreateActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    // Menambahkan function insert
    private fun insert() {
        // Lalu buat objek news dengan constructor yang value-nya diambil dari editText
        val news = News(
            et_input_title.text.toString(),
            et_input_body.text.toString(),
            et_input_date.text.toString()
        )

        // Terakhir, panggil function insert yang ada pada class NewsDBHelper dengan argumen news
        db.insertNews(news)
    }

    // Membuat function update
    private fun update() {
        // Membuat objek news
        val news = News(
            et_input_title.text.toString(),
            et_input_body.text.toString(),
            et_input_date.text.toString()
        )

        // Panggil db.update dengan argument news dan id dari news yang ingin di-update berdasarkan intent key
        db.updateNews(news, intent.getIntExtra("id-extra", 0))
    }
}