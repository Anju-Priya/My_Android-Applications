package com.gtechyderabad.gtec;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video); // Ensure this matches the XML filename

        VideoView videoView = findViewById(R.id.video_view);
        ImageButton backButton = findViewById(R.id.back_button);

        // Set the path of your video file (ensure the video file is in res/raw)
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.your_video_file);
        videoView.setVideoURI(videoUri);
        videoView.start();

        // Set up back button listener
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
