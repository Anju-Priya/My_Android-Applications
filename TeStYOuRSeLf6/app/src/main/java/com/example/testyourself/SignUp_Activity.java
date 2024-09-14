package com.example.testyourself;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUp_Activity extends AppCompatActivity {
    EditText username, phone, email, password;
    Button signupButton;
    ImageView profileImage;
    Bitmap selectedBitmap;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        username = findViewById(R.id.Username);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        signupButton = findViewById(R.id.btnreg);
        profileImage = findViewById(R.id.profile_image);

        // Handle image selection
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        // Handle sign up button click
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = username.getText().toString();
                final String phoneNumber = phone.getText().toString();
                final String mail = email.getText().toString();
                final String pass = password.getText().toString();

                if (user.isEmpty() || phoneNumber.isEmpty() || mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignUp_Activity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if (selectedBitmap == null) {
                    Toast.makeText(SignUp_Activity.this, "Please select an image", Toast.LENGTH_LONG).show();
                } else {
                    // Perform sign up operation in a background thread
                    new SignUpTask().execute(user, phoneNumber, mail, pass);
                }
            }
        });
    }

    // AsyncTask to perform sign up operation in the background
    private class SignUpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            String phoneNumber = params[1];
            String mail = params[2];
            String pass = params[3];

            try {
                URL url = new URL("http://192.168.79.3/Test_Your_Self/insertion.php"); // Replace <your_local_ip>
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****"); // Set boundary directly

                // Construct the multipart/form-data request body
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();

                String boundary = "*****";
                String lineEnd = "\r\n";

                OutputStream os = httpURLConnection.getOutputStream();
                os.write(("--" + boundary + lineEnd).getBytes());
                os.write(("Content-Disposition: form-data; name=\"username\"" + lineEnd).getBytes());
                os.write((lineEnd + user + lineEnd).getBytes());

                os.write(("--" + boundary + lineEnd).getBytes());
                os.write(("Content-Disposition: form-data; name=\"phone\"" + lineEnd).getBytes());
                os.write((lineEnd + phoneNumber + lineEnd).getBytes());

                os.write(("--" + boundary + lineEnd).getBytes());
                os.write(("Content-Disposition: form-data; name=\"email\"" + lineEnd).getBytes());
                os.write((lineEnd + mail + lineEnd).getBytes());

                os.write(("--" + boundary + lineEnd).getBytes());
                os.write(("Content-Disposition: form-data; name=\"password\"" + lineEnd).getBytes());
                os.write((lineEnd + pass + lineEnd).getBytes());

                os.write(("--" + boundary + lineEnd).getBytes());
                os.write(("Content-Disposition: form-data; name=\"image\"; filename=\"" + user + ".jpg\"" + lineEnd).getBytes());
                os.write(("Content-Type: image/jpeg" + lineEnd).getBytes());
                os.write((lineEnd).getBytes());
                os.write(imageData);
                os.write((lineEnd + "--" + boundary + "--" + lineEnd).getBytes());
                os.flush();
                os.close();

                // Read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                return response.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the result of sign up operation
            Toast.makeText(SignUp_Activity.this, result, Toast.LENGTH_LONG).show();

            // Check if registration was successful
            if (result != null && result.equals("Registration successful!")) {
                // Start SignIn_Activity
                Intent intent = new Intent(SignUp_Activity.this, SignIn_Activity.class);
                startActivity(intent);
                // Finish current activity
                finish();
            }
        }
    }

    // Handle image selection result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();
            try {
                // Convert URI to Bitmap
                selectedBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                // Set the selected image to ImageView
                profileImage.setImageBitmap(selectedBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", e.getMessage());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
