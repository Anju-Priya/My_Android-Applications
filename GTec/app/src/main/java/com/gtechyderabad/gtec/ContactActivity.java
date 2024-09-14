package com.gtechyderabad.gtec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ContactActivity extends AppCompatActivity {

    private TextInputEditText editTextName;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextMessage;
    private MaterialButton buttonSubmit;
    private ImageButton btnHome;
    private ImageButton btnServices;
    private ImageButton btnClients;
    private ImageButton btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        btnHome = findViewById(R.id.btn_home);
        btnServices = findViewById(R.id.btn_services);
        btnClients = findViewById(R.id.btn_clients);
        btnContact = findViewById(R.id.btn_contact);

        // Set up click listeners for bottom navigation buttons
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ContactActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnServices.setOnClickListener(v -> {
            Intent intent = new Intent(ContactActivity.this, ServicesActivity.class);
            startActivity(intent);
        });

        btnClients.setOnClickListener(v -> {
            Intent intent = new Intent(ContactActivity.this, ClientsActivity.class);
            startActivity(intent);
        });

        // btnContact click listener is redundant as it's the current activity
        // You might want to remove or modify this if needed

        // Set up click listener for the Submit button
        buttonSubmit.setOnClickListener(v -> {
            // Handle Submit button click
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String message = editTextMessage.getText().toString();

            // Validate input (optional)
            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                // Show an error message or handle incomplete input
                Toast.makeText(ContactActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Process the input (e.g., send it to a server or save it locally)
            // Example: show a Toast
            Toast.makeText(ContactActivity.this, "Submitted successfully", Toast.LENGTH_SHORT).show();

            // Clear the fields after submission (optional)
            editTextName.setText("");
            editTextEmail.setText("");
            editTextMessage.setText("");
        });
    }
}
