package com.appstar.image;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appstar.customgallery.SelectedImage;
import com.appstar.tutionportal.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder> {

    AllImageViewActivity obj;
    private Activity context;
    private ArrayList<SelectedImage> paths = new ArrayList<>();

    public SelectedImageAdapter(Activity context,
                                ArrayList<SelectedImage> paths) {
        this.context = context;
        this.paths = paths;

    }

    public void setObject(AllImageViewActivity object) {
        this.obj = object;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seletced_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SelectedImage selectedImage = paths.get(position);
        Glide.with(context).load(new File(selectedImage.getPath())).into(holder.imageView);

        if (!selectedImage.isSelected()) {
            holder.imageView.setAlpha(1f);
        }
        else{
            holder.imageView.setAlpha(.4f);
        }

        holder.llData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj.onClick(position);

            }
        });
        holder.llData.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                obj.onLongClick(position);
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return paths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        LinearLayout llData;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            llData = itemView.findViewById(R.id.llData);

        }
    }
}

