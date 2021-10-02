package com.shubhjitiya.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;

import com.shubhjitiya.whatsinstasaver.databinding.FragmentAboutBinding;

@Keep
public class About extends Fragment {
    private FragmentAboutBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.instaLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.instagram.com/techtalker_shubh/");
            }
        });
        binding.gmailLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail();
            }
        });
        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void composeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "techtalkershubh@gmail.com");
//        intent.putExtra(Intent.REc, "techtalkershubh@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android App");
        try {
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}