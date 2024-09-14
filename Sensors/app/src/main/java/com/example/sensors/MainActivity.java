package com.example.sensors;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context; // Make sure this import is included
import com.google.android.gms.location.LocationServices;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textLatLong, textAddress;
    private ProgressBar progressBar;
    private Button button;
    private EditText time;
    static EditText t1;
    float xg, yg, zg;
    TextView textX, textY, textZ;
    private Button buttonYes, buttonNo;
    SensorManager sensorManager;
    Sensor sensor;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    // Variables to store location coordinates
    private double latitude, longitude;
    private boolean isYesClicked = false; // Flag to track if "Yes" button is clicked

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLatLong = findViewById(R.id.textLatLong);
        progressBar = findViewById(R.id.progressBar);
        textAddress = findViewById(R.id.textAddress);
        button = findViewById(R.id.display);
        Button button1 = findViewById(R.id.sg1);
        t1 = findViewById(R.id.in_time);
        textX = findViewById(R.id.textX1);
        textY = findViewById(R.id.textY1);
        textZ = findViewById(R.id.textZ1);
        buttonYes = findViewById(R.id.yes);
        buttonNo = findViewById(R.id.no);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the flag to indicate "Yes" button is clicked
                isYesClicked = true;
                // Start continuously storing data
                continuouslyStoreData(1);
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the flag to indicate that the "Yes" button is not clicked
                isYesClicked = false;
                // Start continuously storing data with clickedValue as 0
                continuouslyStoreData(0);
            }
        });

        sensorManager = (SensorManager) MainActivity.this.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textX.setText("X : " + xg + " rad/s");
                textY.setText("Y : " + yg + " rad/s");
                textZ.setText("Z : " + zg + " rad/s");
            }
        });

        findViewById(R.id.buttonco_ordinates_tracker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                }
                else {
                    coordinatesTracker();
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String sleepTime = time.getText().toString();
                //new AsyncTaskRunner().execute("10");
            }
        });
    }


    private void continuouslyStoreData(final int initialValue) {
        // Set initial value of clickedValue based on parameter initialValue
        final int clickedValue = initialValue;

        // Construct the argument with latitude, longitude, x, y, z, and clickedValue parameters
        final String arg = latitude + "@" + longitude + "@" + xg + "@" + yg + "@" + zg + "@" + clickedValue;
        // Execute AsyncTask with the argument
        new AsyncTaskRunner().execute(arg);
        // Schedule the next execution after a delay (e.g., every 1 second)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // If the "Yes" button is clicked, continue storing data with clickedValue as 1
                if (isYesClicked) {
                    continuouslyStoreData(1);
                } else {
                    // If the "No" button is clicked, store data with clickedValue as 0
                    continuouslyStoreData(0);
                }
            }
        }, 1000); // Adjust the delay as needed
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                coordinatesTracker();
            } else {
                Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void coordinatesTracker() {
        progressBar.setVisibility(View.VISIBLE);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            for (Location location : locationResult.getLocations()) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                textLatLong.setText(
                                        String.format(
                                                "Latitude: %s\nLongitude: %s",
                                                latitude,
                                                longitude
                                        )
                                );

                                fetchAddressFromLatLong(location);
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void fetchAddressFromLatLong(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, new AddressResultReceiver(new Handler()));
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                textAddress.setText(resultData.getString(Constants.RESULT_DATA_KEY));
            } else {
                Toast.makeText(MainActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    // gyroscope code*******
    public SensorEventListener gyroListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            xg = x;
            yg = y;
            zg = z;
        }
    };

    // AsyncTask to handle sending data to the server
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            String ar[];
            ar = params[0].split("@");

            publishProgress("Reading..."); // Calls onProgressUpdate()
            try {
                // Construct the URL with latitude, longitude, x, y, z, and clickedValue parameters
                String urlString = "http://14.99.36.179/seedMoney/insert_sensor.php?latitude=" + ar[0] + "&longitude=" + ar[1] +
                        "&x=" + ar[2] + "&y=" + ar[3] + "&z=" + ar[4] + "&clickedValue=" + ar[5];
                URL url = new URL(urlString);

                // Open the connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                resp = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            t1.setText(resp);
        }

        @Override
        protected void onPreExecute() {
            // Executed before doInBackground() is started
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // Executed when publishProgress() is called
            EditText editText = findViewById(R.id.in_time);
            if (editText != null) {
                editText.setText(text[0]);
            }
        }
    }
}
