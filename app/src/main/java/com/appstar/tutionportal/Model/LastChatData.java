
package com.appstar.tutionportal.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastChatData {

    @SerializedName("data")
    @Expose
    private List<ChatUser> data = null;

    public List<ChatUser> getData() {
        return data;
    }

    public void setData(List<ChatUser> data) {
        this.data = data;
    }

}
