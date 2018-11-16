package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatUserStudent implements Serializable {

    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("sender_id")

    // contain recever id and group code
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("messages")
    @Expose
    private String messages;
    @SerializedName("virtual_name")
    // contains user real name and group name
    @Expose
    private String virtualName;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("show_sendername")
    @Expose
    private String showSendername;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("profilepic")
    @Expose
    private String profilepic;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("gid")
    @Expose
    private String gid;

    @SerializedName("group_admin")
    @Expose
    private String group_admin;
    boolean isSelected;

    @SerializedName("unread_count")
    @Expose
    private int unreadCount;

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGroup_admin() {
        return group_admin;
    }

    public void setGroup_admin(String group_admin) {
        this.group_admin = group_admin;
    }


    public ChatUserStudent() {
    }

    public ChatUserStudent(String msgId, String sendId, String recvId, String msg, String realName,
                    String showSenderName, String time, String profilePic, String type) {
        this.mID = msgId;
        this.senderId = sendId;
        this.receiverId = recvId;
        this.messages = msg;
        this.virtualName = virtualName;
        this.realName = realName;
        this.showSendername = showSenderName;
        this.time = time;
        this.profilepic = profilePic;
        this.type = type;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getVirtualName() {
        return virtualName;
    }

    public void setVirtualName(String virtualName) {
        this.virtualName = virtualName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getShowSendername() {
        return showSendername;
    }

    public void setShowSendername(String showSendername) {
        this.showSendername = showSendername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
