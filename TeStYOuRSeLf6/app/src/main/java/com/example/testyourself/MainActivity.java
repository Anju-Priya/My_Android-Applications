package com.example.testyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login,signup;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getSupportActionBar().hide();

        login = findViewById(R.id.btnlogin);
        signup = findViewById(R.id.btnreg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Login has been clicked", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,SignIn_Activity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Registration has been clicked", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(MainActivity.this,SignUp_Activity.class);
                startActivity(intent);
            }
        });

        skip = findViewById(R.id.Skip);

                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Have a good day!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,BeTheVoice.class);
                        startActivity(intent);

                    }
                });

    }
}