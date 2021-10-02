package com.shubhjitiya.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Keep;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.shubhjitiya.InstaVideo;
import com.shubhjitiya.adapter.SliderAdapter;
import com.shubhjitiya.whatsinstasaver.R;
import com.shubhjitiya.whatsinstasaver.databinding.FragmentImageBinding;
import com.shubhjitiya.whatsinstasaver.databinding.FragmentInstagramBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

@Keep
public class Instagram extends Fragment {
    private FragmentInstagramBinding binding;
    private int images[];
    private String text[];
    private SliderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInstagramBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstaVideo.downloadVideo(getContext(), binding.instaVideoUrl.getText().toString());
            }
        });
        try {
            binding.pasteBtn.setOnClickListener(new View.OnClickListener() {
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData pasteData = manager.getPrimaryClip();
                ClipData.Item item = pasteData.getItemAt(0);
                String paste = item.getText().toString();

                @Override
                public void onClick(View v) {
                    binding.instaVideoUrl.setText(paste);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        images = new int[]{R.drawable.step_1, R.drawable.step_2, R.drawable.step_3, R.drawable.step_4};
        text = new String[]{"Step - 01", "Step - 02", "Step - 03", "Step - 04"};

        adapter = new SliderAdapter(images, text);
        binding.sliderView.setSliderAdapter(adapter);
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        binding.sliderView.startAutoCycle();
        return view;
    }

}