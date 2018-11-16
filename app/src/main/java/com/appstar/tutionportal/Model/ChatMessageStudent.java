package com.appstar.tutionportal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessageStudent {
    DateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
    DateFormat formatTime = new SimpleDateFormat("hh:mm:ss a");
    DateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");


    @SerializedName("refer_id")
    @Expose
    private long referId;

    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("messages")
    @Expose
    private String messages;
    @SerializedName("time")
    @Expose
    private String dateTime;
    @SerializedName("sender_id")
    @Expose
    private int senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("gid")
    @Expose
    private String gid;


    @SerializedName("received")
    @Expose
    private int received;

    @SerializedName("read")
    @Expose
    private int read;

    boolean isSendToServer=true;

    public boolean isSendToServer() {
        return isSendToServer;
    }

    public void setSendToServer(boolean sendToServer) {
        isSendToServer = sendToServer;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
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

    @SerializedName("type")
    @Expose
    private String type;

    private String date;
    private String nTime;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelected;

    //      ChatMessage chatMessage = new ChatMessage(mid, msg, sender_id, receiver_id, time);
    public ChatMessageStudent(long referId, String mid, String msg, String sender_id, String recv_id, String time, String type) {
        this.referId = referId;
        this.mID = mid;
        this.messages = msg;
        this.senderId = Integer.parseInt(sender_id);
        this.receiverId = recv_id;
        this.dateTime = time;
        this.type = type;
    }

    public ChatMessageStudent() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        Date d = new Date(date);
    }

    public long getReferId() {
        return referId;
    }

    public void setReferId(long referId) {
        this.referId = referId;
    }

    public String getTime() {
        return nTime;
    }

    public void setTime(String time) {
        this.nTime = time;
    }

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String time) {
        this.dateTime = time;

    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
