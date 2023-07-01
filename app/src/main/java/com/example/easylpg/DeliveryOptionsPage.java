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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class DeliveryOptionsPage extends AppCompatActivity {

    ImageView back;
    Button reviewOrderBtn;
    RadioGroup radioGroup;
    EditText customerDeliveryAddress;
    SharedPreferences sp;
    String SERVER_URL;
    int cust_id, area_id;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_options_page);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        reviewOrderBtn = findViewById(R.id.reviewOrderButton);
        back = findViewById(R.id.back_button1);
        customerDeliveryAddress = findViewById(R.id.customerDeliveryAddress);
        radioGroup = findViewById(R.id.areaRadioGroup);

        sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);

        cust_id = sp.getInt("customerId", 0);

        reviewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = customerDeliveryAddress.getText().toString();
                area_id = radioGroup.getCheckedRadioButtonId();
                if (area_id == 1) {
                    area_id = 1;
                } else if (area_id == 2) {
                    area_id = 2;
                } else if (area_id == 3) {
                    area_id = 3;
                } else if (area_id == 4) {
                    area_id = 4;
                } else if ((area_id % 4) == 1) {
                    area_id = 1;
                } else if ((area_id % 4) == 2) {
                    area_id = 2;
                } else if ((area_id % 4) == 3) {
                    area_id = 3;
                } else if ((area_id % 4) == 0) {
                    area_id = 4;
                }
                SERVER_URL = sp.getString("SERVER_URL", "");
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[3];
                        field[0] = "cust_id";
                        field[1] = "area_id";
                        field[2] = "address";
                        String[] data = new String[3];
                        data[0] = String.valueOf(cust_id);
                        data[1] = String.valueOf(area_id);
                        data[2] = address;
                        PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/insertDeliveryOptions.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                System.out.println("Customer ID here is:" + cust_id);
                                System.out.println("Area ID here is:" + area_id);
                                System.out.println("Address here is:" + address);
                            }
                        }
                    }
                });
                Intent intent = new Intent(DeliveryOptionsPage.this, ReviewOrderPage.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryOptionsPage.this, CartPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

}