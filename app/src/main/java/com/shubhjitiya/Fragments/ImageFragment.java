package com.shubhjitiya.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;

import com.shubhjitiya.adapter.WhatsappAdapter;
import com.shubhjitiya.model.WhatsappStatusModel;
import com.shubhjitiya.whatsinstasaver.databinding.FragmentImageBinding;
import com.shubhjitiya.whatsinstasaver.databinding.FragmentWhatsAppBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Keep
public class ImageFragment extends Fragment {
    private FragmentImageBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        list = new ArrayList<>();
        getData();

        binding.refresh.setOnRefreshListener(() -> {
            list = new ArrayList<>();
            getData();
            binding.refresh.setRefreshing(false);
        });

        return view;
    }
    private void getData() {
        WhatsappStatusModel model;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/WhatsApp/Media/.Statuses";
        File targetDirector = new File(targetPath);
        File[] allFiles = targetDirector.listFiles();

        String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/WhatsApp Business/Media/.Statuses";
        File targetDirectorBusiness = new File(targetPathBusiness);
        File[] allFilesBusiness = targetDirectorBusiness.listFiles();

        try {
            Arrays.sort(allFiles, ((o1, o2) ->
            {
                if (o1.lastModified() > o2.lastModified()) return -1;
                else if (o1.lastModified() < o2.lastModified()) return +1;
                else return 0;
            }));

            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];
                if (Uri.fromFile(file).toString().endsWith(".png") ||
                        Uri.fromFile(file).toString().endsWith(".jpg")) {
                    model = new WhatsappStatusModel("whats " + i,
                            Uri.fromFile(file),
                            allFiles[i].getAbsolutePath(),
                            file.getName());
                    list.add(model);
                }
            }

            Arrays.sort(allFilesBusiness, ((o1, o2) ->
            {
                if (o1.lastModified() > o2.lastModified()) return -1;
                else if (o1.lastModified() < o2.lastModified()) return +1;
                else return 0;
            }));

            for (int i = 0; i < allFilesBusiness.length; i++) {
                File file = allFilesBusiness[i];
                if (Uri.fromFile(file).toString().endsWith(".png") ||
                        Uri.fromFile(file).toString().endsWith(".jpg")) {
                    model = new WhatsappStatusModel("whatsBusiness " + i,
                            Uri.fromFile(file),
                            allFilesBusiness[i].getAbsolutePath(),
                            file.getName());
                    list.add(model);
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Please see some statuses", Toast.LENGTH_SHORT).show();
        }

        adapter = new WhatsappAdapter(list, getActivity());
        binding.whatsappRecycler.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}