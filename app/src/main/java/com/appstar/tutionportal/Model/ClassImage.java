package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassImage implements Serializable{
    @SerializedName("image_id")
    @Expose
    String imageId;
    @SerializedName("image_name")
    @Expose
    String imageUrl;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
