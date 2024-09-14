package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextfullName, textInputEditTextusername, textInputEditTextpassword,
            textInputEditTextcontact;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextfullName = findViewById(R.id. fullname);
        textInputEditTextusername = findViewById(R.id. Username);
        textInputEditTextpassword = findViewById(R.id. Password);
        textInputEditTextcontact  = findViewById(R.id. Contact);
        buttonSignUp = findViewById(R.id. buttonSignUp);
        textViewLogin = findViewById(R.id. loginText);
        progressBar = findViewById(R.id. progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String fullname, username, password, contact;
                 fullname = String.valueOf( textInputEditTextfullName.getText());
                 username = String.valueOf( textInputEditTextusername.getText());
                 password = String.valueOf( textInputEditTextpassword.getText());
                 contact = String.valueOf ( textInputEditTextcontact.getText());

                 if(!fullname.equals("") && !username.equals("") && !password.equals("") && !contact.equals("")){
                     progressBar.setVisibility(View.VISIBLE);
                     //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[4];
                        field[0] = "FullName";
                        field[1] = "Username";
                        field[2] = "Password";
                        field[3] = "Contact";
                        String[] data = new String[4];
                        data[0] = "fullname";
                        data[1] = "username";
                        data[2] = "password";
                        data[3] = "contact";
                        PutData putData = new PutData("http://192.168.231.3/MyDiary/SignUp.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if(result.equals("Sign Up Success")){
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    Log.d("DDDDD",result)      ;                          }
                            }
                        }
                    }
                });

            }
                 else {
                     Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                 }
                 }
        }));




    }
}