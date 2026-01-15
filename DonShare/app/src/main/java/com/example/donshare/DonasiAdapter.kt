package com.example.donshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

class DonasiAdapter(private val listDonasi: List<DonasiItem>) :
    RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {

    class DonasiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDonasi: ImageView = view.findViewById(R.id.imgDonasi)
        val tvNamaBencana: TextView = view.findViewById(R.id.tvNamaBencana)
        val tvDonationAmount: TextView = view.findViewById(R.id.tvDonationAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonasiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_donasi, parent, false)
        return DonasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonasiViewHolder, position: Int) {
        val item = listDonasi[position]
        holder.tvNamaBencana.text = item.name

        val amountValue = item.total_amount?.toDoubleOrNull() ?: 0.0

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        formatter.maximumFractionDigits = 0

        holder.tvDonationAmount.text = formatter.format(amountValue)

        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.donasi2)
            .error(R.drawable.donasi1)
            .centerCrop()
            .into(holder.imgDonasi)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, paymentMethodActivity::class.java)
            intent.putExtra("EVENT_ID", item.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listDonasi.size
}