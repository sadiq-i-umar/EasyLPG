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
import android.widget.ImageView;
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
import java.util.List;

public class Dashboard extends AppCompatActivity {

    String mystring;
    private RecyclerView vendorRecView;
    VendorRecViewAdapter adapter;
    ImageView account, logoutlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        vendorRecView = findViewById(R.id.vendorRecView);
        logoutlink = findViewById(R.id.img3);
        account = findViewById(R.id.left_icon);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        String SERVER_URL = sp.getString("SERVER_URL", "");

        String VENDOR_URL = "http://" + SERVER_URL + "/loginregister/selectvendor.php";

        ArrayList<Vendor> vendorArrayList = new ArrayList<>();

        Intent intent = getIntent();
        String str = intent.getStringExtra("email_key");
        mystring = str;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, VENDOR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray vendors = new JSONArray(response);

                            for (int i = 0; i < vendors.length(); i++) {
                                JSONObject vendorObject = vendors.getJSONObject(i);

                                int id = vendorObject.getInt("vendor_id");
                                String name = vendorObject.getString("name");
                                String logo = vendorObject.getString("logo");
                                Double rating = vendorObject.getDouble("rating");

                                Vendor vendor = new Vendor(id, name, logo, rating);
                                vendorArrayList.add(vendor);
                            }

                            VendorRecViewAdapter adapter = new VendorRecViewAdapter(Dashboard.this);
                            adapter.setVendors(vendorArrayList);

                            vendorRecView.setAdapter(adapter);
                            vendorRecView.setLayoutManager(new LinearLayoutManager(Dashboard.this));
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

        logoutlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Account.class);
                intent.putExtra("newemail_key", mystring);
                startActivity(intent);
                finish();
            }
        });

    }

}