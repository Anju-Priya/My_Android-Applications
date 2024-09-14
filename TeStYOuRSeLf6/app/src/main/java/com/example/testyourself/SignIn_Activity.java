package com.example.testyourself;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testyourself.SignUp_Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignIn_Activity extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.btnlogin);
        signUpText = findViewById(R.id.Signup);

        // Handle login button click
        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve entered email and password
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                // Check for empty fields
                if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(SignIn_Activity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Hash the password before sending it to the server
                    String hashedPassword = hashPassword(userPassword);
                    // Perform login logic here
                    new LoginTask().execute(userEmail, hashedPassword);
                }
            }
        });


        // Handle sign up text click
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the sign-up activity
                Intent intent = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // Method to hash the password using SHA-256 algorithm
    // Method to hash the password using SHA-256 algorithm
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder builder = new StringBuilder();
            for (byte b : hashBytes) {
                builder.append(String.format("%02x", b));
            }
            String hashedPassword = builder.toString();

            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ""; // or return null;
        }
    }



    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Extract parameters (email and password) from params array
            String email = params[0];
            String password = params[1];

            // Initialize variables to store the result and error message
            String result = "";
            String errorMessage = "";

            // Server URL for login endpoint
            String serverAddress = "http://192.168.79.3"; // Replace "your_server" with your actual server address
            String loginEndpoint = "/Test_Your_Self/signin.php"; // Replace "/login.php" with the actual path to your login PHP file
            String loginUrl = serverAddress + loginEndpoint;

            try {
                // Create a HttpURLConnection to communicate with the server
                URL url = new URL(loginUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Prepare the POST data
                String postData = "email=" + URLEncoder.encode(email, "UTF-8")
                        + "&password=" + URLEncoder.encode(password, "UTF-8");

                // Send the POST data to the server
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData.getBytes());
                outputStream.flush();
                outputStream.close();

                // Check the response code from the server
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response from the server
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the response and set the result
                    result = response.toString();
                } else {
                    // If the response code is not HTTP_OK, set an error message
                    errorMessage = "Server returned non-OK status: " + responseCode;
                }

                // Disconnect the connection
                connection.disconnect();
            } catch (IOException e) {
                // If an IOException occurs, set the error message
                errorMessage = "IOException: " + e.getMessage();
                e.printStackTrace();
            }

            // Return the result or error message
            return errorMessage.isEmpty() ? result : errorMessage;
        }

        @Override
        protected void onPostExecute(String result) {
            // This method is called after doInBackground finishes
            // You can update the UI based on the result here

            // For example:
            if (result != null) {
                if (result.equals("Login successful")) {
                    // Navigate to the home screen
                    Intent intent = new Intent(SignIn_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: close the current activity
                } else {
                    // Display an error message to the user
                    Toast.makeText(SignIn_Activity.this, result, Toast.LENGTH_SHORT).show();
                }
            } else {
                // Display an error message to the user
                Toast.makeText(SignIn_Activity.this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
