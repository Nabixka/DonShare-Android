package com.example.donshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PaymentMethodListAdapter extends RecyclerView.Adapter<PaymentMethodListAdapter.ViewHolder> {

    private List<PaymentMethodItem> paymentMethodList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PaymentMethodItem item);
    }

    public PaymentMethodListAdapter(List<PaymentMethodItem> paymentMethodList, OnItemClickListener listener) {
        this.paymentMethodList = paymentMethodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_payment_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentMethodItem item = paymentMethodList.get(position);

        holder.tvPaymentName.setText(item.getName());

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .placeholder(R.drawable.coin)
                .error(R.drawable.coin)
                .into(holder.imgPayment);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentMethodList != null ? paymentMethodList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPayment;
        TextView tvPaymentName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPayment = itemView.findViewById(R.id.imgPayment);
            tvPaymentName = itemView.findViewById(R.id.tvPaymentName);
        }
    }
}