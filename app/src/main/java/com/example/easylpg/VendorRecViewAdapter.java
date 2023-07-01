package com.example.easylpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class VendorRecViewAdapter extends RecyclerView.Adapter<VendorRecViewAdapter.ViewHolder> {
    private ArrayList<Vendor> vendors = new ArrayList<>();
    private Context context;
    SharedPreferences sp;

    public VendorRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sp = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        holder.vendorName.setText(vendors.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(vendors.get(position).getVendorLogo())
                .into(holder.vendorLogo);
        //Math.round(double*100.0)/100.0
        holder.vendorRating.setText(String.valueOf(vendors.get(position).getRating()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("vendorId", vendors.get(position).getId());
                editor.putString("vendorName", vendors.get(position).getName());
                editor.putString("vendorLogo", vendors.get(position).getVendorLogo());
                editor.commit();
                Intent intent = new Intent(context, VendorPage.class);
                intent.putExtra("nameExtra", vendors.get(position).getName());
                intent.putExtra("imageurl", vendors.get(position).getVendorLogo());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendors.size();
    }

    public void setVendors(ArrayList<Vendor> vendors) {
        this.vendors = vendors;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private TextView vendorName, vendorRating;
        private ImageView vendorLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            vendorName = itemView.findViewById(R.id.vendorName);
            vendorRating = itemView.findViewById(R.id.vendorRating);
            vendorLogo = itemView.findViewById(R.id.vendorLogo);
        }
    }
}
