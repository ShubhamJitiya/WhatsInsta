package com.shubhjitiya.whatsinstasaver;

import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shubhjitiya.Fragments.About;
import com.shubhjitiya.Fragments.Instagram;
import com.shubhjitiya.Fragments.WhatsApp;
import com.shubhjitiya.whatsinstasaver.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Keep
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.add(new MeowBottomNavigation.Model(1, R.drawable.whatsapp));
        binding.bottomNavigationView.add(new MeowBottomNavigation.Model(2, R.drawable.instagram));
        binding.bottomNavigationView.add(new MeowBottomNavigation.Model(3, R.drawable.ic_about));
        binding.bottomNavigationView.show(1, true);

        binding.bottomNavigationView.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new WhatsApp());
                        break;

                    case 2:
                        replace(new Instagram());
                        break;

                    case 3:
                        replace(new About());
                        break;

                }
                return null;
            }
        });
        checkPermission();
//            if(!checkPermission())
//            {
//                showPermissionDialog();
//            }
    }

    //Takes permission - using dexter
    public void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (!report.areAllPermissionsGranted())
                    checkPermission();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    //Permission - Includes Android - 11
//    private boolean checkPermission() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            return Environment.isExternalStorageManager();
//        } else {
//            int write = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//            int read = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
//
//            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
//        }
//    }
//
//    //Permission dialog
//    public void showPermissionDialog() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
//                startActivityForResult(intent, 2000);
//            } catch (Exception e) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, 2000);
//            }
//        } else {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 333);
//        }
//    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    //For Android less than 11
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 333) {
//            if (grantResults.length > 0) {
//                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                if (read && write) {
//                    Toast.makeText(MainActivity.this, "Read & Write Permission Is Granted", Toast.LENGTH_SHORT).show();
//                } else if (read)
//                    Toast.makeText(MainActivity.this, "Only Read Permission Is Granted", Toast.LENGTH_SHORT).show();
//                else if(write)
//                    Toast.makeText(MainActivity.this, "Only Write Permission Is Granted", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    //For Android - 11
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 2000) {
//            if (SDK_INT >= Build.VERSION_CODES.R) {
//                if (Environment.isExternalStorageManager()) {
//                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        // Otherwise, ask user if he wants to leave :)
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        // MainActivity.super.onBackPressed();
                        finishAndRemoveTask();
                        finish();

                        moveTaskToBack(true);
                    }
                }).create().show();
    }
}
