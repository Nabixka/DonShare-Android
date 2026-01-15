package com.example.donshare

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DonasiAdapter(
    private val listDonasi: List<DonasiItem>,
    private val userId: Int
) : RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {

    class DonasiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaBencana: TextView = view.findViewById(R.id.tvNamaBencana)
        val imgBencana: ImageView = view.findViewById(R.id.imgDonasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonasiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_donasi, parent, false)
        return DonasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonasiViewHolder, position: Int) {
        val item = listDonasi[position]
        holder.tvNamaBencana.text = item.name
        Glide.with(holder.itemView.context).load(item.image).into(holder.imgBencana)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, paymentMethodActivity::class.java)
            intent.putExtra("EVENT_ID", item.id)
            intent.putExtra("EVENT_NAME", item.name)
            intent.putExtra("USER_ID", userId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listDonasi.size
}