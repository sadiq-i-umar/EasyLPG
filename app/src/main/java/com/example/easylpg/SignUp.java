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
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText nameInput, emailInput, phoneInput, passwordInput;
    Button signUpButton;
    TextView loginlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //This code makes the status bar translucent
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        signUpButton = findViewById(R.id.btn_signup);
        loginlink = findViewById(R.id.tv_already_registered);

        nameInput = findViewById(R.id.edt_name);
        emailInput = findViewById(R.id.edt_email);
        phoneInput = findViewById(R.id.edt_phonenumber);
        passwordInput = findViewById(R.id.edt_password);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                String SERVER_URL = sp.getString("SERVER_URL", "");
                String name, email, phone, password;

                name = String.valueOf(nameInput.getText());
                email = String.valueOf(emailInput.getText());
                phone = String.valueOf(phoneInput.getText());
                password = String.valueOf(passwordInput.getText());

                if (!name.equals("") && !email.equals("") && !phone.equals("") && !password.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "name";
                            field[1] = "email";
                            field[2] = "phone";
                            field[3] = "password";
                            String[] data = new String[4];
                            data[0] = name;
                            data[1] = email;
                            data[2] = phone;
                            data[3] = password;
                            PutData putData = new PutData("http://" + SERVER_URL + "/loginregister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}