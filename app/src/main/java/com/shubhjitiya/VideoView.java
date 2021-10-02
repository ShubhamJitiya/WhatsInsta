package com.shubhjitiya;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;

import com.shubhjitiya.whatsinstasaver.R;

@Keep
public class VideoView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        String file = getIntent().getStringExtra("video");

        android.widget.VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(file);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}