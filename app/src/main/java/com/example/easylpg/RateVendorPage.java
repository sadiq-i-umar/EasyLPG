package com.example.easylpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RateVendorPage extends AppCompatActivity {
    RatingBar ratingBar;
    Button btSubmit;
    TextView cancelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_vendor_page);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ratingBar = findViewById(R.id.rating_bar);
        btSubmit = findViewById(R.id.bt_submit);
        cancelText = findViewById(R.id.cancelText);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        int cust_id = sp.getInt("customerId", 0);
        int vendor_id = sp.getInt("vendorId", 0);

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RateVendorPage.this, VendorPage.class);
                startActivity(intent);
                finish();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = (int) ratingBar.getRating();
                if (rating == 0) {
                    Toast.makeText(getApplicationContext(), "Cancel or Rate Vendor", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("customerRating", rating);
                    editor.commit();
                    rateVendor(cust_id, vendor_id, rating);
                    Toast.makeText(getApplicationContext(), "Vendor Rated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RateVendorPage.this, VendorPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void rateVendor(int cust_id, int vendor_id, int rating) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String SERVER_URL = sp.getString("SERVER_URL", "");
        if ((cust_id != 0) && (vendor_id != 0) && (rating != 0)) {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[3];
                    field[0] = "cust_id";
                    field[1] = "vendor_id";
                    field[2] = "rating";
                    String[] data = new String[3];
                    data[0] = String.valueOf(cust_id);
                    data[1] = String.valueOf(vendor_id);
                    data[2] = String.valueOf(rating);
                    PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/ratevendor.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            Toast.makeText(getApplicationContext(), "Vendor Rated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
        }
    }
}