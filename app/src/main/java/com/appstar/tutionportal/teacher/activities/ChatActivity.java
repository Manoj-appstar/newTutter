package com.appstar.tutionportal.teacher.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Model.ChatMessage;
import com.appstar.tutionportal.Model.ChatUser;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.adapter.NewChatMessageAdapter;
import com.appstar.tutionportal.teacher.fragments.FragmentStudentProfile;
import com.appstar.tutionportal.teacher.fragments.FragmentTeacherChatHistory;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.markushi.ui.CircleButton;

public class ChatActivity extends AppCompatActivity implements OnResponseListener {
    private final int GET_NUMBER_RECORD = 60;
    int GET_MESSAGE = 899, SET_MESSAGE = 900, REQ_POST_GET_NAME_DETAIL = 901, REQ_ENABLE_REAL_NAME, REQ_READ_NOTIFICATION;
    BroadcastReceiver receiver;
    TextView tvUserName, tvVisibleDate, tvConversation;
    RecyclerView recyclerView;
    LinearLayout llDate;
    EditText etMessage;
    NewChatMessageAdapter adapter;
    List<ChatMessage> chatList = new ArrayList<>();
    ChatUser chatUser;
    RequestServer requestServer;
    ProgressBar progress;
    LinearLayoutManager layoutManager;
    android.text.format.DateFormat df = new android.text.format.DateFormat();
    DateFormat formatDate = new SimpleDateFormat("MMMM dd, yyyy");
    DateFormat formatTime = new SimpleDateFormat("hh:mm a");
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CheckBox chkboxNameChange;
    String virtualName;
    String uid;
    boolean isMyShowNameEnable;
    boolean isDataLoading, isLoading;
    boolean noData;
    List<ChatMessage> sChatList;
    ImageView imgGroup;
    int receiver_id = 123;
    int sendeer_id = 1234;
    int TYPE_CLEAR_CHAT = 100, TYPE_SHOW_HIDE_NAME = 101, TYPE_DELETE_MESSAGE = 102;
    Menu liveMenu;
    boolean isMultiSelected;
    ActionMode.Callback mActionModeCallback;
    List<ChatMessage> selectedList = new ArrayList<>();
    boolean isAddedOnChatHistory;
    Activity mActivity;
    CircleButton btnSend;
    ImageView ivBack;
    private Utils utils;
    private SharePreferenceData sharePreferenceData;
    private int MAX_DATA_SHOWS = 50;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Data.checkInternetConnection(this);
        sharePreferenceData = new SharePreferenceData();
        mActivity = ChatActivity.this;
        utils = new Utils();
        chatUser = new ChatUser();
        initView();
        profileClickListener();
        initMode();
        getUserData();

        /* setBroadCast();
        getUserData();
        getMyData();
        onRecycleScrollListener();*/
        Data.cancelNotification(getApplicationContext());
        onRecycleScrollListener();
        //  getMyData();

      /*  profileClickListener();
        initMode();
        onMultiSelection();*/
    }

    private void initView() {

        imgGroup = findViewById(R.id.imgGroup);
        Glide.with(mActivity).load(R.drawable.temp_profile).apply(RequestOptions.circleCropTransform()).into(imgGroup);
        tvUserName = findViewById(R.id.tvUserName);
        btnSend = findViewById(R.id.btnSend);
        ivBack = findViewById(R.id.ivBack);
        //  findViewById(R.id.btnSend).setVisibility(View.GONE);
        chkboxNameChange = findViewById(R.id.chkBoxShowName);
        tvVisibleDate = findViewById(R.id.tvVisibleDate);
        recyclerView = findViewById(R.id.recyclerViewChatWindow);
        etMessage = findViewById(R.id.etMessage);
        tvConversation = findViewById(R.id.tvConversation);
        etMessage.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        llDate = findViewById(R.id.llDate);
        chatList = new ArrayList<>();
        requestServer = new RequestServer(this, this);
        layoutManager = new LinearLayoutManager(getApplicationContext());

        //  layoutManager.setReverseLayout(true);
        // layoutManager.setReverseLayout(true);
        //  layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setStackFromEnd(true);
        btnSendListener();

    }

    private void btnSendListener() {

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etMessage.getText().toString().length() > 0)
                    sendMessageToUser(etMessage.getText().toString().trim());
            }
                /*if (!isDataLoading) {
            Data.disabledView(view);
            if (etMessage.getText().toString().length() > 0)
                sendMessageToUser(etMessage.getText().toString().trim());
        }
    }*/
        });
    }

    private void profileClickListener() {
        imgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickProfile(view);
            }
        });
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickProfile(view);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void playSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //   Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/notify_sound");
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickProfile(View view) {
        Data.disabledView(view);
        Bundle bundle = new Bundle();

        /*bundle.putString("name", tvUserName.getText().toString().trim());
        bundle.putInt("uid", Integer.valueOf(uid));
        bundle.putBoolean("isShowRealName", chatUser.getShowSendername().equals("1"));*/

        Intent intent = new Intent(ChatActivity.this, FragmentStudentProfile.class);
        intent.putExtras(bundle);
        startActivity(intent);

        //    overridePendingTransition(R.anim.exit_to_left,R.anim.exit_to_right);

    }

    private void sendMessageToUser(String message) {
        try {
            String refer = sharePreferenceData.getUserId(mActivity) + "" + String.valueOf(System.currentTimeMillis());
            long referId = Long.parseLong(refer);
            chatUser.setMessages(message);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMID("");
            chatMessage.setMessages(message);
            //  chatMessage.setSenderId(Integer.parseInt(sharePreferenceData.getUserId(mActivity)));
            chatMessage.setSenderId(sendeer_id);
            chatMessage.setReferId(referId);
            chatMessage.setReceiverId(String.valueOf(receiver_id));
            chatMessage.setType("chat");
            chatMessage.setSendToServer(false);

          /*  JSONObject jsonObject = new JSONObject();
            jsonObject.put("sender_id", sharePreferenceData.getUserId(mActivity));
            jsonObject.put("receiver_id", uid);
            jsonObject.put("sendervirtual_name", virtualName);
            jsonObject.put("senderreal_name", sharePreferenceData.getFirstName(mActivity) + sharePreferenceData.getLastName(mActivity));
            jsonObject.put("show_sendername", isMyShowNameEnable ? "1" : "0");
            jsonObject.put("msg", message);
            jsonObject.put("refer_id", referId);
            requestServer.sendStringPost(Data.SEND_USER_MESSAGE, jsonObject, SET_MESSAGE, false);*/

            String date = df.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()).toString();
            chatMessage.setDateTime(date);
            chatUser.setMessage(message);
            chatMessage.setMessages(message);
            chatUser.setTime(date);
            chatUser.setUnreadCount(0);
            etMessage.setText("");
            addData(chatMessage);
        } catch (Exception ex) {
        }
    }

    private void addData(ChatMessage chatMessage) {
        chatList.add(chatMessage);
        if (adapter == null || chatList.size() <= 1) {
            adapter = new NewChatMessageAdapter(getApplicationContext(), chatList);
            recyclerView.setAdapter(adapter);
            tvConversation.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(chatList.size() - 1);
        addOnChatHistory();
    }

    private void getChatData() {
        isLoading = true;
        progress.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //2018-05-29 13:06:27
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("sender_id", sharePreferenceData.getUserId(mActivity));
                    jsonObject.put("receiver_id", uid);
                    jsonObject.put("offset", chatList.size());
                    requestServer.sendStringPost(Data.GET_USER_MESSAGE, jsonObject, GET_MESSAGE, false);
                } catch (Exception ex) {
                    progress.setVisibility(View.GONE);
                }

            }
        }, 500);
    }

    private void setBroadCast() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String type = intent.getStringExtra("type");
                String sender_id = intent.getStringExtra("sender_id");
                String receiver_id = intent.getStringExtra("receiver_id");

                if (type.equalsIgnoreCase("chat") && sender_id.equals(uid)) {
                    String time = intent.getStringExtra("time");
                    String real_name = intent.getStringExtra("real_name");
                    String icon = intent.getStringExtra("icon");
                    String msg = intent.getStringExtra("msg");
                    String mid = intent.getStringExtra("mid");
                    playSound();
                    ChatMessage chatMessage = new ChatMessage(0, mid, msg, sender_id, receiver_id, time, type);
                    addData(chatMessage);
                    sendReadNotification();
                } else if (type.equalsIgnoreCase("read")) {
                    if (receiver_id.equalsIgnoreCase(uid)) {
                        setReadMessage();
                    }
                } else if (type.equalsIgnoreCase("block")) {
                    if (sender_id.equalsIgnoreCase(uid)) {
                        showDialog("You are blocked now", true);
                    }
                } else {
                    if (sender_id.equalsIgnoreCase(uid)) {
                        tvUserName.setText(chatUser.getRealName());
                        if (!TextUtils.isEmpty(chatUser.getProfilepic()))
                            Glide.with(mActivity).load(R.drawable.temp_profile).apply(RequestOptions.circleCropTransform()).into(imgGroup);
                        //Picasso.with(getApplicationContext()).load(chatUser.getProfilepic()).placeholder(R.drawable.ic_logo).into(imgGroup);
                        Snackbar.make(etMessage, "User has been enable to see name", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(Data.RECEIVER_MSG));
    }

    private void removeBroadCast() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeBroadCast();
        Data.setChatUser_id("");
    }

    private void setReadMessage() {
        for (ChatMessage message : chatList) {
            message.setRead(1);
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private void showDialog(String msg, final boolean isBack) {
        final Dialog
                dialog = new Dialog(ChatActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       /* dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;*/
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.session_logut_dilog);
        final TextView tvOk = dialog.findViewById(R.id.tvOk);
        final TextView tvContent = dialog.findViewById(R.id.tvContent);
        final TextView llData = dialog.findViewById(R.id.tvOk);
        tvContent.setText(msg);
        tvContent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_bg));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBack)
                    finish();
                else
                    dialog.dismiss();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 400);
    }

    private void onRecycleScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                final boolean hasEnded = newState == RecyclerView.SCROLL_STATE_IDLE;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (hasEnded) {
                            llDate.setVisibility(View.GONE);
                        } else
                            llDate.setVisibility(View.VISIBLE);
                    }
                }, 150);
            }
        });

    }

    private void getUserData() {
        boolean isShowRealName = false;
        if (tvUserName != null) {
            isDataLoading = true;
            if (Data.getChatUser() != null) {
                isAddedOnChatHistory = true;
                chatUser = Data.getChatUser();
                uid = Integer.parseInt(chatUser.getSenderId()) == Integer.parseInt("1234") ? chatUser.getReceiverId() : chatUser.getSenderId();
                tvUserName.setText(chatUser.getRealName());
            } else {
                isAddedOnChatHistory = false;
                String id = getIntent().getStringExtra("uid");
                String name = getIntent().getStringExtra("realName");
                String msg = getIntent().getStringExtra("msg");
                String time = getIntent().getStringExtra("msgTime");
                String url = getIntent().getStringExtra("url");
                uid = id;
                isShowRealName = getIntent().getBooleanExtra("isShowRealName", false);
                chatUser = new ChatUser("", sharePreferenceData.getUserId(mActivity), id, name, "url", "msg", "time", url, "chat");
                tvUserName.setText(name.trim());
            }
            Data.setChatUser_id(uid);
            isShowRealName = true;
            if (!TextUtils.isEmpty(chatUser.getProfilepic())) {
                Glide.with(mActivity).load(chatUser.getProfilepic()).apply(RequestOptions.circleCropTransform()).into(imgGroup);
                //   Picasso.with(getApplicationContext()).load(chatUser.getProfilepic()).placeholder(R.mipmap.ic_launcher_round).into(imgGroup);
            } else
                imgGroup.setBackgroundResource(R.drawable.ic_logo);
        }
        Data.setChatUser_id(uid);
    }

    public void setDateOnTop(String dateTime, int position) {
        try {
            Date d = formatDateTime.parse(dateTime);
            // holder.tvFromTime.setText(formatDate.format(d));
            String time = formatDate.format(d);
            tvVisibleDate.setText(time);
        } catch (Exception ex) {
        }

        if (position == 0) {
            if (!isLoading && !noData) {
                getChatData();
            }
        }

    }


    /*private void getMyData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("sender_id", uid);
                    jsonObject.put("receiver_id", sharePreferenceData.getUserId(mActivity));
                    requestServer.sendStringPost(Data.GET_CHAT_USER_DETAIL, jsonObject, REQ_POST_GET_NAME_DETAIL, false);
                } catch (Exception ex) {
                }
            }
        }, 1500);

    }*/

    private void initMode() {
        mActionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.option_chat_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // Return false if nothing is done
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                if (item.getItemId() == R.id.item_coyp) {
                    copyMessage();

                } else if (item.getItemId() == R.id.item_delete) {
                    showHideNameConfirmation(TYPE_DELETE_MESSAGE);
                }

                return true;

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
                isMultiSelected = false;
                for (ChatMessage chatMessage : selectedList) {
                    chatMessage.setSelected(false);
                }
                adapter.notifyDataSetChanged();
                selectedList.clear();
            }
        };
    }

    private void showHideNameConfirmation(final int type) {
        //chkboxNameChange.setChecked(isMyShowNameEnable);
        String title = "";
        String msg = "";
        if (type == TYPE_CLEAR_CHAT) {
            title = "Confirmation to clear chat";
            msg = "Do you really want to  clear all chat ?";

        } else if (type == TYPE_DELETE_MESSAGE) {
            title = "Confirmation to delete chat";
            msg = "Do you really want to delete selected chat ?";
        }
        new AlertDialog.Builder(ChatActivity.this)
                .setTitle(title)
                .setMessage(msg)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (type == TYPE_CLEAR_CHAT) {
                            clearAllChat();
                        } else if (type == TYPE_DELETE_MESSAGE) {
                            deleteChatById();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    private void clearAllChat() {
        try {

          /*  String url = Data.URL + "message/delete_allmsg.php";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sender_id", sessionManager.getUID());
            jsonObject.put("receiver_id", uid);
            requestServer.sendStringPost(url, jsonObject, TYPE_CLEAR_CHAT, true);*/

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private void deleteChatById() {
        String id = "";
        for (ChatMessage chatMessage : selectedList) {
            if (id.length() <= 0) {
                id += chatMessage.getMID();
            } else {
                id += "," + chatMessage.getMID();
            }
        }


        if (id.length() > 0) {
            try {
                /*String url = Data.URL + "message/delete_selectedmsg.php";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sender_id", sessionManager.getUID());
                jsonObject.put("receiver_id", uid);
                jsonObject.put("delete_mid", id);
                requestServer.sendStringPost(url, jsonObject, TYPE_DELETE_MESSAGE, true);*/

            } catch (Exception ex) {
            }
        }
    }

    private void addOnChatHistory() {
        if (!isAddedOnChatHistory) {
            isAddedOnChatHistory = true;
            ChatUser user = findObjByIdChat(String.valueOf(receiver_id));
            //   Log.e("chat",chatUser.getRealName());
            if (user != null) {
                user.setRealName(chatUser.getRealName());
                //user.setVirtualName(chatUser.getVirtualName());
                user.setMessage(chatUser.getMessage());
                user.setMessages(chatUser.getMessages());
                user.setTime(chatUser.getTime());
                user.setShowSendername(chatUser.getShowSendername());
                user.setSenderId(chatUser.getSenderId());
                user.setReceiverId(chatUser.getReceiverId());
                chatUser = user;
            } else {
                FragmentTeacherChatHistory.chatUserList.add(chatUser);
            }
        }

    }

    private ChatUser findObjByIdChat(String id) {
        ChatUser obj = null;
        for (ChatUser chatUser : FragmentTeacherChatHistory.chatUserList) {

            Log.e("chat", chatUser.getRealName());
            if (chatUser.getType().equalsIgnoreCase("chat")) {
                if (chatUser.getReceiverId().equals(id) || chatUser.getSenderId().equals(id)) {
                    obj = chatUser;
                    break;
                }
            }
        }
        return obj;
    }


    public void onLongClick(int position) {

        if (!isMultiSelected) {
            isMultiSelected = true;
            if (mActionMode == null) {
                mActionMode = startActionMode(mActionModeCallback);
            }
        }

        onClick(position);

     /*   ChatMessage chatMessage = chatList.get(position);
        if (!chatMessage.isSelected())
            chatMessage.setSelected(true);
        else
            chatMessage.setSelected(false);
        adapter.notifyDataSetChanged();*/
    }

    public void onClick(int position) {
        if (selectedList.size() <= 15) {
            if (isMultiSelected) {
                ChatMessage chatMessage = chatList.get(position);
                if (!chatMessage.isSelected()) {
                    if (selectedList.size() <= 14) {
                        chatMessage.setSelected(true);
                        selectedList.add(chatMessage);
                    } else
                        Toast.makeText(getApplicationContext(), "You can't select more than 15 record", Toast.LENGTH_SHORT).show();
                } else {
                    selectedList.remove(chatMessage);
                    chatMessage.setSelected(false);
                }
                adapter.notifyDataSetChanged();

                if (selectedList.size() <= 0) {
                    isMultiSelected = false;
                    mActionMode.finish();
                    selectedList.clear();

                } else if (mActionMode != null) {
                    mActionMode.setTitle(String.valueOf(selectedList.size()));
                }
            }
        }
    }

    private void sendReadNotification() {
        chatUser.setUnreadCount(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = Data.URL + "message/update_read_status.php";
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("sender_id", sharePreferenceData.getUserId(mActivity));
                    jsonObject.put("receiver_id", uid);
                    requestServer.sendStringPost(url, jsonObject, REQ_READ_NOTIFICATION, false);
                } catch (Exception ex) {
                }
            }
        }).start();

    }

    @Override
    public void onSuccess(int reqCode, String response) {

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    private void copyMessage() {
        String text = "";
        for (ChatMessage chatMessage : selectedList) {
            text += chatMessage.getMessages() + "\n ";
        }
        text = text.trim();
        Data.copyText(text, getApplicationContext());
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
