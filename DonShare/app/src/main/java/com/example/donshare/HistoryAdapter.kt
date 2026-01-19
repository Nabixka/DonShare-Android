package com.example.donshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

class HistoryAdapter(private val listHistory: List<DonationHistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEvent: ImageView = view.findViewById(R.id.imgEvent)
        val tvEventName: TextView = view.findViewById(R.id.tvEventName)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvPaymentMethod: TextView = view.findViewById(R.id.tvPaymentMethod)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_donasi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listHistory[position]

        holder.tvEventName.text = item.event.name

        val amountValue = item.amount.toDoubleOrNull() ?: 0.0
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        holder.tvAmount.text = formatRupiah.format(amountValue).replace("Rp", "Rp ")

        val paymentInfo = "${item.payment.method} • ${item.payment.number}"
        holder.tvPaymentMethod.text = paymentInfo

        holder.tvDate.text = "Berhasil • Baru saja"

        Glide.with(holder.itemView.context)
            .load(item.event.image)
            .placeholder(R.drawable.donasi1)
            .error(R.drawable.donasi1)
            .into(holder.imgEvent)
    }

    override fun getItemCount(): Int = listHistory.size
}