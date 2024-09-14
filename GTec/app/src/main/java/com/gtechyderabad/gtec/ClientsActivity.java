package com.gtechyderabad.gtec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewClients;
    private List<Client> clientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        // Initialize the client list
        clientList = new ArrayList<>();
        clientList.add(new Client("Adrelina Brinchett", "Lorem ipsum dolor sit amet, feugiat lorem non, ultrices justo...", R.drawable.profile));
        clientList.add(new Client("Jane Doe", "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore...", R.drawable.jane));
        clientList.add(new Client("John Smith", "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...", R.drawable.john3));
        // Add more clients as needed

        // Set up RecyclerView
        recyclerViewClients = findViewById(R.id.recyclerViewClients);
        recyclerViewClients.setLayoutManager(new LinearLayoutManager(this));
        ClientAdapter adapter = new ClientAdapter(clientList);
        recyclerViewClients.setAdapter(adapter);

        // Set up bottom navigation
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        findViewById(R.id.btn_home).setOnClickListener(v -> navigateTo(MainActivity.class));
        findViewById(R.id.btn_services).setOnClickListener(v -> navigateTo(ServicesActivity.class));
        findViewById(R.id.btn_clients).setOnClickListener(v -> navigateTo(ClientsActivity.class));
        findViewById(R.id.btn_contact).setOnClickListener(v -> navigateTo(ContactActivity.class));
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(ClientsActivity.this, activityClass);
        startActivity(intent);
        finish(); // Optionally finish the current activity to remove it from the back stack
    }
}
