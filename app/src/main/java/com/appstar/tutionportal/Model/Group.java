package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Group implements Serializable {
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("group_code")
    @Expose
    private String groupCode;
    @SerializedName("group_admin")
    @Expose
    private String groupAdmin;
    @SerializedName("group_admin_name")
    @Expose
    private String groupAdminName;
    @SerializedName("group_icon")
    @Expose
    private String groupIcon;
    @SerializedName("virtual_name")
    @Expose
    private String virtualName;
    @SerializedName("block_ids")
    @Expose
    private String blockIds;
    @SerializedName("group_member")
    @Expose
    private String groupMember;
    @SerializedName("time")
    @Expose
    private String time;

    String myVirtualName;

    public String getMyVirtualName() {
        return myVirtualName;
    }

    public void setMyVirtualName(String myVirtualName) {
        this.myVirtualName = myVirtualName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(String groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public String getGroupAdminName() {
        return groupAdminName;
    }

    public void setGroupAdminName(String groupAdminName) {
        this.groupAdminName = groupAdminName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getVirtualName() {
        return virtualName;
    }

    public void setVirtualName(String virtualName) {
        this.virtualName = virtualName;
    }

    public String getBlockIds() {
        return blockIds;
    }

    public void setBlockIds(String blockIds) {
        this.blockIds = blockIds;
    }

    public String getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(String groupMember) {
        this.groupMember = groupMember;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
