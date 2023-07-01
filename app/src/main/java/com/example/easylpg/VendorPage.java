package com.example.easylpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VendorPage extends AppCompatActivity {

    ImageView back, cart, vendorLogo;
    Button buttonOne, buttonTwo, rateButton;
    String nameExtra;
    TextView vendorNameOnToolbar;

    int cust_id, vendor_id, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_page);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        String SERVER_URL = sp.getString("SERVER_URL", "");

        String CUSTOMER_URL = "http://" + SERVER_URL + "/loginregister/loadcustomeraccount.php";

        String VENDOR_URL = "http://" + SERVER_URL + "/loginregister/loadvendoraccount.php";

        vendor_id = sp.getInt("vendorId", 0);

        back = findViewById(R.id.backIcon);
        cart = findViewById(R.id.cartIcon);
        buttonOne = findViewById(R.id.buttonOne);
        buttonTwo = findViewById(R.id.buttonTwo);
        vendorNameOnToolbar = findViewById(R.id.vendorNameOnToolbar);
        vendorLogo = findViewById(R.id.vendorLogo);
        rateButton = findViewById(R.id.rateButton);

        String vendorName = sp.getString("vendorName", "");
        String logo = sp.getString("vendorLogo", "");
        String userEmail = sp.getString("userEmail", "");
        int customerating = sp.getInt("customerRating", 0);

        ArrayList<Customer> customerArrayList = new ArrayList<>();
        ArrayList<Vendor> vendorArrayList = new ArrayList<>();

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorPage.this, RateVendorPage.class);
                startActivity(intent);
                finish();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CUSTOMER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray customers = new JSONArray(response);

                            for (int i = 0; i < customers.length(); i++) {
                                JSONObject customerObject = customers.getJSONObject(i);

                                int id = customerObject.getInt("cust_id");
                                String name = customerObject.getString("name");
                                String email = customerObject.getString("email");
                                String phone = customerObject.getString("phone");

                                Customer customer = new Customer(id, name, email, phone);
                                customerArrayList.add(customer);
                            }

                            for (int i = 0; i < customerArrayList.size(); i++) {
                                if (customerArrayList.get(i).getEmail().equals(userEmail)) {
                                    cust_id = customerArrayList.get(i).getId();
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putInt("customerId", cust_id);
                                    editor.commit();
                                    autoCreateOrder();
                                    break;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VendorPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        cust_id = sp.getInt("customerId", 0);
        vendor_id = sp.getInt("vendorId", 0);

        System.out.println("Customer ID here is:" + String.valueOf(cust_id));
        System.out.println("Vendor ID here is:" + String.valueOf(vendor_id));

        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("imageurl");
        Glide.with(this)
                .asBitmap()
                .load(logo)
                .into(vendorLogo);

        vendorNameOnToolbar.setText(vendorName);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "cust_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(cust_id);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/addItemOneToCart.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Toast.makeText(getApplicationContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "cust_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(cust_id);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/addItemTwoToCart.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Toast.makeText(getApplicationContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "cust_id";
                        String[] data = new String[1];
                        data[0] = String.valueOf(cust_id);
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/deleteNotPlacedOrder.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Intent intent = new Intent(VendorPage.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorPage.this, CartPage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void autoCreateOrder() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String SERVER_URL = sp.getString("SERVER_URL", "");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[2];
                field[0] = "cust_id";
                field[1] = "vendor_id";
                String[] data = new String[2];
                data[0] = String.valueOf(cust_id);
                data[1] = String.valueOf(vendor_id);
                PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/autoCreateOrder.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                    }
                }
            }
        });
    }
}