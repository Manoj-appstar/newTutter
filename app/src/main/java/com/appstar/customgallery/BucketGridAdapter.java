/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package com.appstar.customgallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;


public class BucketGridAdapter extends ArrayAdapter<BucketEntry> {
    private Context mContext;
    private ArrayList<BucketEntry> mBucketEntryList;
    private boolean mIsFromVideo;
    private int mWidth;
    LayoutInflater viewInflater;


    public BucketGridAdapter(Context context, int resource, ArrayList<BucketEntry> categories, boolean isFromVideo) {
        super(context, resource, categories);
        mBucketEntryList = categories;
        mContext = context;
        mIsFromVideo = isFromVideo;
        viewInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return mBucketEntryList.size();
    }

    @Override
    public BucketEntry getItem(int position) {
        return mBucketEntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            mWidth = mContext.getResources().getDisplayMetrics().widthPixels;

            convertView = viewInflater.inflate(R.layout.gallery_view_grid_bucket_item_media_chooser, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewFromMediaChooserBucketRowView);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextViewFromMediaChooserBucketRowView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FrameLayout.LayoutParams imageParams = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
        imageParams.width = mWidth / 2;
        imageParams.height = mWidth / 2;

        holder.imageView.setLayoutParams(imageParams);
        //ImageLoadAsync loadAsync = new ImageLoadAsync(mContext, holder.imageView, mWidth / 2);
        //loadAsync.executeOnExecutor(MediaAsync.THREAD_POOL_EXECUTOR, mBucketEntryList.get(position).bucketUrl);
        Glide.with(mContext).load(new File(mBucketEntryList.get(position).bucketUrl)).into(holder.imageView);


        holder.nameTextView.setText(mBucketEntryList.get(position).bucketName);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
    }
}


