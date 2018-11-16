package com.appstar.customgallery;

import android.graphics.Bitmap;

public class MediaModel {
    public String url = null;
    public boolean status = false;
    public Bitmap bitmap;

    public MediaModel(String url, boolean status) {
        this.url = url;
        this.status = status;

    }
}
