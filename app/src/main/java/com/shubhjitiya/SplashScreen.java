package com.shubhjitiya;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shubhjitiya.whatsinstasaver.MainActivity;
import com.shubhjitiya.whatsinstasaver.R;

@Keep
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageView loaderImage = findViewById(R.id.loaderImage);
        TextView appTextName = findViewById(R.id.txtAppName);
        TextView developer = findViewById(R.id.developer);

        loaderImage.animate().scaleY(0.5f).scaleX(0.5f).setDuration(500);
        appTextName.animate().scaleY(1.2f).scaleX(1.2f).setDuration(500);
        developer.animate().scaleY(1.2f).scaleX(1.2f).setDuration(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}