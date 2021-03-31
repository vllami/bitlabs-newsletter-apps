package com.bitlabs.newsletter.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bitlabs.newsletter.R
import com.bitlabs.newsletter.activities.CreateActivity
import com.bitlabs.newsletter.entity.News
import com.bitlabs.newsletter.helper.NewsletterDBHelper
import com.bitlabs.newsletter.viewholder.NewsViewHolder

// Setelah membuat ViewHolder, sekarang buat class NewsAdapter
class NewsAdapter(news: ArrayList<News>, context: Context) :
    RecyclerView.Adapter<NewsViewHolder>() {

    private val mContext = context
    private val mNews = news
    private val db = NewsletterDBHelper(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.layout_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.title.text = mNews[position].title
        holder.body.text = mNews[position].body
        holder.date.text = mNews[position].date

        // Agar button dapat di-klik, tambahkan setOnClickListener di dalam method onBindViewHolder
        holder.btnUpdate.setOnClickListener {
            // Membuat variable intent untuk berpindah ke CreateNewsActivity
            val intent = Intent(mContext, CreateActivity::class.java)

            // Lalu menambahkan 4 key dalam intent-nya, yaitu id-extra, title-extra, body-extra, date-extra
            intent.putExtra("id-extra", mNews[position].id)
            intent.putExtra("title-extra", mNews[position].title)
            intent.putExtra("body-extra", mNews[position].body)
            intent.putExtra("date-extra", mNews[position].date)

            // Lalu panggil method startActivity untuk berpindah ke Activity CreateNewsActivity
            mContext.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            // Memanggil method deleteNews() dan isi argumen dengan id dari news yang ingin di delete
            db.deleteNews(mNews[position].id)

            // Lalu me-refresh Activity
            (mContext as Activity).finish()
            mContext.startActivity(mContext.intent)

            // Membuat Toast message
            Toast.makeText(mContext, "Success Delete News", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = mNews.size

}