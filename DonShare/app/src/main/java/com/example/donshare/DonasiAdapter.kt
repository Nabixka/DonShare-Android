package com.example.donshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DonasiAdapter(private val listDonasi: List<DonasiItem>) :
    RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {

    class DonasiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDonasi: ImageView = view.findViewById(R.id.imgDonasi)
        val tvNamaBencana: TextView = view.findViewById(R.id.tvNamaBencana)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonasiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_donasi, parent, false)
        return DonasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonasiViewHolder, position: Int) {
        val item = listDonasi[position]

        holder.tvNamaBencana.text = item.name

        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.donasi2)
            .error(R.drawable.donasi1)
            .into(holder.imgDonasi)
    }

    override fun getItemCount(): Int = listDonasi.size
}
