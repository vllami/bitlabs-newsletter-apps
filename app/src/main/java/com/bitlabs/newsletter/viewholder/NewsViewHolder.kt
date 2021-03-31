package com.bitlabs.newsletter.viewholder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_news.view.*

// Membuat class NewsViewHolder
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.title_list
    val body: TextView = itemView.body_list
    val date: TextView = itemView.date_list
    val btnUpdate: Button = itemView.btn_update
    val btnDelete: TextView = itemView.btn_delete
}