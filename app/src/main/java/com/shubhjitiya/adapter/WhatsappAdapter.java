package com.shubhjitiya.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shubhjitiya.FullImageView;
import com.shubhjitiya.Util;
import com.shubhjitiya.VideoView;
import com.shubhjitiya.model.WhatsappStatusModel;
import com.shubhjitiya.whatsinstasaver.R;
import com.shubhjitiya.whatsinstasaver.databinding.WhatsappItemLayoutBinding;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhatsappAdapter extends RecyclerView.Adapter<WhatsappAdapter.viewHolder> {

    private ArrayList<WhatsappStatusModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String saveFilePath = Util.RootDirectoryWhatsApp + "/";
    private List<String> images;

    public WhatsappAdapter(ArrayList<WhatsappStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        return new viewHolder(DataBindingUtil.inflate(inflater,
                R.layout.whatsapp_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WhatsappAdapter.viewHolder holder, int position) {
        WhatsappStatusModel item = list.get(position);

        if (item.getUri().toString().endsWith(".mp4"))
            holder.binding.playBtn.setVisibility(View.VISIBLE);
        else
            holder.binding.playBtn.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImage);

        holder.binding.viewStatusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getPath().endsWith(".png") ||
                        (item.getPath().endsWith(".jpg"))) {
                    Intent intent = new Intent(context, FullImageView.class);
                    intent.putExtra("image", item.getPath());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, VideoView.class);
                    intent.putExtra("video", item.getPath());
                    context.startActivity(intent);
                }

            }
        });

        holder.binding.download.setOnClickListener(v -> {
            Util.createFileFolder();
            final String path = item.getPath();
            final File file = new File(path);

            File destFile = new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file, destFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Saved to : " + saveFilePath, Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        WhatsappItemLayoutBinding binding;

        public viewHolder(WhatsappItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
