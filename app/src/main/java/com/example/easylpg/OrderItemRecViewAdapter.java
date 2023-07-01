package com.example.easylpg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderItemRecViewAdapter extends RecyclerView.Adapter<OrderItemRecViewAdapter.ViewHolder>  {
    private ArrayList<OrderItem> orderItems = new ArrayList<>();
    private Context context;

    public OrderItemRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.orderID.setText("#" + String.valueOf(orders.get(position).getId()));
        //holder.customerName.setText(orders.get(position).getCustomerName());
        holder.qty.setText("X" + String.valueOf(orderItems.get(position).getQuantity()) + "(pcs)");
        holder.name.setText(orderItems.get(position).getName());
        holder.item_cost.setText("₦" + String.valueOf(orderItems.get(position).getTotalcost()) + "0");
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private TextView qty, name, item_cost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            qty = itemView.findViewById(R.id.itemQuantity);
            name = itemView.findViewById(R.id.itemName);
            item_cost = itemView.findViewById(R.id.itemTotalCost);
        }
    }
}
