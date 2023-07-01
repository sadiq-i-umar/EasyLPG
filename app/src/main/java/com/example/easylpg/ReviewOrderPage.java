package com.example.easylpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ReviewOrderPage extends AppCompatActivity {

    private RecyclerView orderItemRecView;
    OrderItemRecViewAdapter adapter;
    ImageView back;
    Button orderBtn;
    TextView nameContent, phoneContent, addressContent;
    String SERVER_URL;
    String order_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order_page);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        orderBtn = findViewById(R.id.placeOrderButton);
        back = findViewById(R.id.back_button1);
        nameContent = findViewById(R.id.nameContent);
        phoneContent = findViewById(R.id.phoneContent);
        addressContent = findViewById(R.id.addressContent);
        orderItemRecView = findViewById(R.id.orderItemRecView);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        SERVER_URL = sp.getString("SERVER_URL", "");

        String ORDERITEMS_URL = "http://" + SERVER_URL + "/loginregister/loadorderitems.php";

        String CUSTOMERORDERCONTACTINFO_URL = "http://" + SERVER_URL + "/loginregister/loadcustomerordercontactinfo.php";

        int cust_id = sp.getInt("customerId", 0);

        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();

        ArrayList<CartTotal> cartTotalArrayList = new ArrayList<>();

        ArrayList<CustomerContactInfo> customerArrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ORDERITEMS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray orderItems = new JSONArray(response);

                            for (int i = 0; i < orderItems.length(); i++) {
                                JSONObject orderItemsJSONObject = orderItems.getJSONObject(i);

                                int customerid = orderItemsJSONObject.getInt("cust_id");
                                int qty = orderItemsJSONObject.getInt("qty");
                                String name = orderItemsJSONObject.getString("name");
                                Double totalcost = orderItemsJSONObject.getDouble("total_cost");

                                OrderItem orderItem = new OrderItem(customerid, qty, name, totalcost);
                                if (customerid == cust_id) {
                                    OrderItemRecViewAdapter adapter = new OrderItemRecViewAdapter(ReviewOrderPage.this);
                                    orderItemArrayList.add(orderItem);
                                    adapter.setOrderItems(orderItemArrayList);

                                    orderItemRecView.setAdapter(adapter);
                                    orderItemRecView.setLayoutManager(new LinearLayoutManager(ReviewOrderPage.this));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        StringRequest stringRequestTwo = new StringRequest(Request.Method.GET, CUSTOMERORDERCONTACTINFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray customers = new JSONArray(response);

                            for (int i = 0; i < customers.length(); i++) {
                                JSONObject customersJSONObject = customers.getJSONObject(i);

                                int customerid = customersJSONObject.getInt("cust_id");
                                String name = customersJSONObject.getString("name");
                                String phone = customersJSONObject.getString("phone");
                                String delivery_address = customersJSONObject.getString("delivery_address");

                                CustomerContactInfo customer = new CustomerContactInfo(customerid, name, phone, delivery_address);
                                customerArrayList.add(customer);
                            }

                            for (int i = 0; i < customerArrayList.size(); i++) {
                                if (customerArrayList.get(i).getId() == cust_id) {
                                    nameContent.setText(customerArrayList.get(i).getName());
                                    phoneContent.setText(customerArrayList.get(i).getPhone());
                                    addressContent.setText(customerArrayList.get(i).getAddress());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequestTwo);

        StringRequest orderTotal = new StringRequest(Request.Method.GET, CUSTOMERORDERCONTACTINFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray customers = new JSONArray(response);

                            for (int i = 0; i < customers.length(); i++) {
                                JSONObject customersJSONObject = customers.getJSONObject(i);

                                int customerid = customersJSONObject.getInt("cust_id");
                                String name = customersJSONObject.getString("name");
                                String phone = customersJSONObject.getString("phone");
                                String delivery_address = customersJSONObject.getString("delivery_address");

                                CustomerContactInfo customer = new CustomerContactInfo(customerid, name, phone, delivery_address);
                                customerArrayList.add(customer);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(orderTotal);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "cust_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(cust_id);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/placeOrder.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Intent intent = new Intent(ReviewOrderPage.this, OrderSent.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewOrderPage.this, DeliveryOptionsPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}