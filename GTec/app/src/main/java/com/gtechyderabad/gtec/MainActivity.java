package com.gtechyderabad.gtec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button exploreServicesButton;
    private Button contactUsButton;
    private Button getStartedButton; // Add reference to the Get Started button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the buttons
        exploreServicesButton = findViewById(R.id.btn_explore_services);
        contactUsButton = findViewById(R.id.btn_contact_us);
        getStartedButton = findViewById(R.id.cta_button); // Initialize Get Started button

        // Set click listeners for buttons
        exploreServicesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExploreServicesActivity.class);
            startActivity(intent);
        });

        contactUsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);
        });

        // Set click listener for Get Started button
        getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);
        });

        // Initialize bottom navigation buttons and set click listeners
        ImageButton homeButton = findViewById(R.id.btn_home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent); // This line reloads the MainActivity; consider using finish() instead
        });

        ImageButton servicesButton = findViewById(R.id.btn_services);
        servicesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ServicesActivity.class);
            startActivity(intent);
        });

        ImageButton clientsButton = findViewById(R.id.btn_clients);
        clientsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClientsActivity.class);
            startActivity(intent);
        });

        ImageButton contactButton = findViewById(R.id.btn_contact);
        contactButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
        });
    }
}
