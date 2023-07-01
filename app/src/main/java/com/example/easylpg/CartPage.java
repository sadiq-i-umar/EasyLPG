package com.example.easylpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CartPage extends AppCompatActivity {

    private RecyclerView cartRecView;
    CartRecViewAdapter adapter;
    ImageView back, cartImageinCartPage;
    Button deliveryBtn, proceedToDelivery;
    TextView cartTotalTextView, emptyText;
    CardView subTotalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        cartRecView = findViewById(R.id.cartPageRecView);
        back = findViewById(R.id.backIcon);
        deliveryBtn = findViewById(R.id.proceedToDelivery);
        cartTotalTextView = findViewById(R.id.cart_total);
        cartImageinCartPage = findViewById(R.id.cartImageinCartPage);
        emptyText = findViewById(R.id.emptyText);
        proceedToDelivery = findViewById(R.id.proceedToDelivery);
        subTotalCard = findViewById(R.id.subTotalCard);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        String SERVER_URL = sp.getString("SERVER_URL", "");

        String CARTITEM_URL = "http://" + SERVER_URL + "/loginregister/loadcartitems.php";

        String CARTTOTAL_URL = "http://" + SERVER_URL + "/loginregister/loadcarttotal.php";

        ArrayList<CartItem> cartItemArrayList = new ArrayList<>();

        ArrayList<CartTotal> cartTotalArrayList = new ArrayList<>();

        int cust_id = sp.getInt("customerId", 0);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CARTITEM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        CartRecViewAdapter adapter = new CartRecViewAdapter(CartPage.this);

                        try {
                            JSONArray cartItems = new JSONArray(response);

                            for (int i = 0; i < cartItems.length(); i++) {
                                JSONObject cartItemsJSONObject = cartItems.getJSONObject(i);

                                int itemid = cartItemsJSONObject.getInt("item_id");
                                int customerid = cartItemsJSONObject.getInt("cust_id");
                                String name = cartItemsJSONObject.getString("name");
                                Double price = cartItemsJSONObject.getDouble("price");
                                int qty = cartItemsJSONObject.getInt("qty");
                                double total_cost = cartItemsJSONObject.getDouble("total_cost");

                                CartItem cartItem = new CartItem(itemid, customerid, name, price, qty, total_cost);
                                if ((customerid == cust_id)) {
                                    proceedToDelivery.setVisibility(View.VISIBLE);
                                    cartItemArrayList.add(cartItem);

                                    adapter.setCartItems(cartItemArrayList);

                                    cartRecView.setAdapter(adapter);
                                    cartRecView.setLayoutManager(new LinearLayoutManager(CartPage.this));

                                    subTotalCard.setVisibility(View.VISIBLE);
                                }
                            }

                            if (adapter.getItemCount() == 0) {
                                cartImageinCartPage.setVisibility(View.VISIBLE);
                                emptyText.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        StringRequest stringRequestTwo = new StringRequest(Request.Method.GET, CARTTOTAL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cartTotal = new JSONArray(response);

                            for (int i = 0; i < cartTotal.length(); i++) {
                                JSONObject cartTotalJSONObject = cartTotal.getJSONObject(i);

                                int customerid = cartTotalJSONObject.getInt("cust_id");
                                String order_status = cartTotalJSONObject.getString("order_status");
                                double total_cost = cartTotalJSONObject.getDouble("total_cost");

                                CartTotal cartTotalObj = new CartTotal(customerid, order_status, total_cost);
                                cartTotalArrayList.add(cartTotalObj);
                            }

                            for (int i = 0; i < cartTotalArrayList.size(); i++) {
                                if (cartTotalArrayList.get(i).getCust_id() == cust_id) {
                                    cartTotalTextView.setText("₦" + String.valueOf(cartTotalArrayList.get(i).getTotal_cost()) + "0");
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(CartPage.this).add(stringRequestTwo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartPage.this, VendorPage.class);
                startActivity(intent);
                finish();
            }
        });

        deliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartPage.this, DeliveryOptionsPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

}