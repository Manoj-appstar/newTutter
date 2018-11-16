package com.appstar.customgallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.appstar.tutionportal.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ImageGalleryAdapter extends ArrayAdapter<SelectedImage> {
    LayoutInflater viewInflater;
    private Context mContext;
    private ArrayList<SelectedImage> imageList;
    private boolean mIsFromVideo;
    private int mWidth;


    public ImageGalleryAdapter(Context context, int resource, ArrayList<SelectedImage> categories) {
        super(context, resource, categories);
        imageList = categories;
        mContext = context;
        viewInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return imageList.size();
    }

    @Override
    public SelectedImage getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        SelectedImage selectedImage = imageList.get(position);
        if (convertView == null) {

            mWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            convertView = viewInflater.inflate(R.layout.gallery_selected_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.imgSuccess = convertView.findViewById(R.id.imgSuccess);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FrameLayout.LayoutParams imageParams = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
        imageParams.width = mWidth / 2;
        imageParams.height = mWidth / 2;

        holder.imageView.setLayoutParams(imageParams);
        Glide.with(mContext).load(new File(selectedImage.getPath())).into(holder.imageView);
        if (selectedImage.isSelected()) {
            holder.imgSuccess.setVisibility(View.VISIBLE);
            holder.imageView.setAlpha(.2f);
        } else {
            holder.imgSuccess.setVisibility(View.GONE);
            holder.imageView.setAlpha(1f);
        }


        //   holder.nameTextView.setText(mBucketEntryList.get(position).bucketName);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        ImageView imgSuccess;

    }
}


