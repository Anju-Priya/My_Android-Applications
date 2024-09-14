package com.example.register;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText1, editText2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Button and EditText here, after setContentView
        button = findViewById(R.id.button);
        editText1 = findViewById(R.id.e1);
        editText2 = findViewById(R.id.e2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a1 = editText1.getText().toString();
                String b1 = editText2.getText().toString();

                if (a1.equals("") || b1.equals("")) {
                    Toast.makeText(MainActivity.this, "You have forgotten to fill", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Apka Din Mangalmay Ho", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
