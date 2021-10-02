package com.shubhjitiya;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.shubhjitiya.whatsinstasaver.R;

import cn.bluemobi.dylan.photoview.library.PhotoView;

@Keep
public class FullImageView extends AppCompatActivity {
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        photoView = findViewById(R.id.imageView);

        String image = getIntent().getStringExtra("image");
        Glide.with(this).load(image).into(photoView);
    }
}