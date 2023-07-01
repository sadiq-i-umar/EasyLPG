package com.example.easylpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private Context context;

    public CartRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecViewAdapter.ViewHolder holder, int position) {
        holder.cart_item_name.setText(cartItems.get(position).getName());
        holder.cart_item_price.setText(String.valueOf("₦" + cartItems.get(position).getPrice() + "0"));
        holder.qtyinput.setText(String.valueOf(cartItems.get(position).getQty()));
        holder.totalCost.setText(String.valueOf("₦" + cartItems.get(position).getTotal_cost() + "0"));
        /*holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemid = cartItems.get(position).getItem_id();
                SharedPreferences sp = context.getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "item_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(itemid);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/addItemQuantity.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Intent intent = new Intent(context.getApplicationContext(), CartPage.class);
                                context.startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
        holder.reduceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemid = cartItems.get(position).getItem_id();
                SharedPreferences sp = context.getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "item_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(itemid);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/reduceItemQuantity.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Intent intent = new Intent(context.getApplicationContext(), CartPage.class);
                                context.startActivity(intent);
                            }
                        }
                    }
                });
            }
        });*/
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemid = cartItems.get(position).getItem_id();
                SharedPreferences sp = context.getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "item_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(itemid);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/removeItemFromCart.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                holder.cart_item_card.setVisibility(View.GONE);
                                Toast.makeText(context.getApplicationContext(), "Item Removed From Cart", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context.getApplicationContext(), CartPage.class);
                                context.startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent, cart_item_card;
        private TextView cart_item_name, cart_item_price, qtyinput, totalCost, removeItem, cart_total;
        private ImageView addBtn, reduceBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_card = itemView.findViewById(R.id.cart_item_card);
            cart_item_name = itemView.findViewById(R.id.cart_item_name);
            cart_item_price = itemView.findViewById(R.id.cart_item_price);
            qtyinput = itemView.findViewById(R.id.qty);
            addBtn = itemView.findViewById(R.id.addbutton);
            reduceBtn = itemView.findViewById(R.id.remove_button);
            totalCost = itemView.findViewById(R.id.totalpricecartitem);
            removeItem = itemView.findViewById(R.id.removeItem);
        }
    }
}
