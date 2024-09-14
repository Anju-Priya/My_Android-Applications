package com.example.sensors;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ResultReceiver resultReceiver = intent.getParcelableExtra(Constants.RECEIVER);
            if (resultReceiver == null) {
                return;
            }

            String errorMessage = "";
            Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
            if (location == null) {
                return;
            }

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException ioException) {
                errorMessage = ioException.getMessage();
            } catch (IllegalArgumentException illegalArgumentException) {
                errorMessage = illegalArgumentException.getMessage();
            }

            if (addresses == null || addresses.isEmpty()) {
                deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage, resultReceiver);
            } else {
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<>();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressFragments.add(address.getAddressLine(i));
                }
                String addressMessage = TextUtils.join(System.getProperty("line.separator"), addressFragments);
                deliverResultToReceiver(Constants.SUCCESS_RESULT, addressMessage, resultReceiver);
            }
        }
    }

    private void deliverResultToReceiver(int resultCode, String addressMessage, ResultReceiver resultReceiver) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, addressMessage);
        resultReceiver.send(resultCode, bundle);
    }
}
