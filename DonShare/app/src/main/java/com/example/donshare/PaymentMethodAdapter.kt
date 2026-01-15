package com.example.donshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PaymentMethodAdapter(
    private val list: List<UserPaymentItem>,
    private val onClick: (UserPaymentItem) -> Unit
) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPaymentName: TextView = view.findViewById(R.id.tvPaymentName)
        val tvPaymentNumber: TextView = view.findViewById(R.id.tvPaymentNumber)
        val imgPayment: ImageView = view.findViewById(R.id.imgPayment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment_method, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvPaymentName.text = item.paymentMethod.name

        holder.tvPaymentNumber.text = item.nomor

        Glide.with(holder.itemView.context)
            .load(item.paymentMethod.image)
            .placeholder(R.drawable.donasi2)
            .error(R.drawable.donasi1)
            .into(holder.imgPayment)

        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = list.size
}