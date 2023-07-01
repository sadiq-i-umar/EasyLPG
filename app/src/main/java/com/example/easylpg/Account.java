package com.example.easylpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Account extends AppCompatActivity {

    TextView nameContent, emailContent, phoneContent;

    ImageView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        closeBtn = findViewById(R.id.closeBtn);
        nameContent = findViewById(R.id.nameContent);
        emailContent = findViewById(R.id.emailContent);
        phoneContent = findViewById(R.id.phoneContent);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        String SERVER_URL = sp.getString("SERVER_URL", "");

        String CUSTOMER_URL = "http://" + SERVER_URL + "/loginregister/loadcustomeraccount.php";

        String userEmail = sp.getString("userEmail", "");

        ArrayList<Customer> customerArrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CUSTOMER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray customers = new JSONArray(response);

                            for (int i = 0; i < customers.length(); i++) {
                                JSONObject customerObject = customers.getJSONObject(i);

                                String name = customerObject.getString("name");
                                String email = customerObject.getString("email");
                                String phone = customerObject.getString("phone");

                                Customer customer = new Customer(name, email, phone);
                                customerArrayList.add(customer);

                            }

                            for (int i = 0; i < customerArrayList.size(); i++) {
                                if (customerArrayList.get(i).getEmail().equals(userEmail)) {
                                    nameContent.setText(customerArrayList.get(i).getName());
                                    emailContent.setText(customerArrayList.get(i).getEmail());
                                    phoneContent.setText(customerArrayList.get(i).getPhone());
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Account.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}