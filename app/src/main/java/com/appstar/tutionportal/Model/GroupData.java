
package com.appstar.tutionportal.Model;

import java.security.acl.Group;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupData {

    @SerializedName("data")
    @Expose
    private List<Group> data = null;

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }

}
