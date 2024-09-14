package com.gtechyderabad.gtec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // Bottom Navigation Buttons

        LinearLayout homeButton = findViewById(R.id.nav_home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ServicesActivity.this, MainActivity.class);
            startActivity(intent);
        });

        LinearLayout servicesButton = findViewById(R.id.nav_services);
        servicesButton.setOnClickListener(v -> {
            // Current Activity, no action needed
        });

        LinearLayout clientsButton = findViewById(R.id.nav_clients);
        clientsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ServicesActivity.this, ClientsActivity.class);
            startActivity(intent);
        });

        LinearLayout contactButton = findViewById(R.id.nav_contact);
        contactButton.setOnClickListener(v -> {
            Intent intent = new Intent(ServicesActivity.this, ContactActivity.class);
            startActivity(intent);
        });
    }
}
