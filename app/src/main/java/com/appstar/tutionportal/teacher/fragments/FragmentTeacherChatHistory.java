package com.appstar.tutionportal.teacher.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.Model.ChatUser;
import com.appstar.tutionportal.Model.LastChatData;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.teacher.activities.GroupDetail;
import com.appstar.tutionportal.teacher.adapter.NewChatListUserAdapter;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.SimpleDividerItemDecoration;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentTeacherChatHistory extends Fragment implements OnResponseListener {
    public static List<ChatUser> chatUserList = new ArrayList<>();
    public boolean isMultiSelected;
    BroadcastReceiver receiver, receiverAddUser;
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    RequestServer requestServer;
    NewChatListUserAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Activity mActivity;
    FloatingActionButton floadAddChat;
    LinearLayoutManager layoutManager;
    SharePreferenceData sharePreferenceData;
    ActionMode.Callback mActionModeCallback;
    List<ChatUser> selectedList = new ArrayList<>();
    ChatUser chatUser;
    BottomSheetDialog bottomSheetDialog;
    boolean isShowNeedDialog;
    View view;
    private int REQ_GET_ALL_USER_LAST_CHAT = 19998, REQ_GET_GROUP_CHAT_ALL = 19987, TYPE_CLEAR_CHAT = 199988;
    private ActionMode mActionMode;
    private Paint p = new Paint();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initView(view);
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        setList();

      /*  initMode();
          addUserReceiver();*/
        //  getUserChatData();
        swipeAction();
        floatingButtonClickListener(view);
        return view;
    }


    private void initView(View view) {

        recyclerView = view.findViewById(R.id.recyclerViewChatHistory);
        progressBar = view.findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        sortListByDate();
        adapter = new NewChatListUserAdapter(getActivity(), chatUserList);
        recyclerView.setAdapter(adapter);
        requestServer = new RequestServer(getActivity(), this);

    }

    private void setList() {
        for (int i = 0; i < 10; i++) {
            ChatUser chatUser = new ChatUser();
            if (i == 0) {
                chatUser.setRealName("John Stero Branch0");
                chatUser.setMessages("Hii");
                chatUser.setReceiverId(String.valueOf(123));
                chatUser.setSenderId(String.valueOf(1234));
                chatUser.setType("Chat");
                chatUser.setTime("2018-06-28 23:44:15");
            } else if (i == 1) {
                chatUser.setRealName("John Stero Branch1");
                chatUser.setMessages("Bye");
                chatUser.setReceiverId(String.valueOf(123));
                chatUser.setSenderId(String.valueOf(1234));
                chatUser.setType("Chat");
                chatUser.setTime("2018-07-20 12:32:06");
            } else {
                if (i % 2 == 0) {
                    chatUser.setRealName("John Stero Branch2");
                    chatUser.setMessages("How are you?");
                    chatUser.setReceiverId(String.valueOf(123));
                    chatUser.setSenderId(String.valueOf(1234));
                    chatUser.setType("Chat");
                    chatUser.setTime("2018-07-20 12:31:01");
                } else {
                    chatUser.setRealName("John Stero Branch3");
                    chatUser.setMessages("Where are You?");
                    chatUser.setReceiverId(String.valueOf(123));
                    chatUser.setSenderId(String.valueOf(1234));
                    chatUser.setType("Chat");
                    chatUser.setTime("2018-07-20 12:22:29");
                }
            }
            chatUserList.add(chatUser);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });

        if (reqCode == TYPE_CLEAR_CHAT) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.getString("deleted").equalsIgnoreCase("done")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (chatUser != null) {
                                chatUserList.remove(chatUser);
                                adapter.notifyDataSetChanged();
                                chatUser = null;
                            }
                        }
                    }, 200);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            try {
                Gson gson = new Gson();
                Type category = new TypeToken<LastChatData>() {
                }.getType();
                LastChatData data = gson.fromJson(response, category);
                if (data != null) {
                    if (data.getData() != null) {
                        if (chatUserList.size() > 0) {
                            chatUserList.addAll(data.getData());
                        } else {
                            chatUserList = data.getData();
                        }
                        if (reqCode != REQ_GET_ALL_USER_LAST_CHAT) {
                            sortListByDate();
                            adapter = new NewChatListUserAdapter(getActivity(), chatUserList);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                } else {
                    if (reqCode != REQ_GET_ALL_USER_LAST_CHAT) {
                        adapter = new NewChatListUserAdapter(getActivity(), chatUserList);
                        recyclerView.setAdapter(adapter);

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (reqCode == REQ_GET_ALL_USER_LAST_CHAT) {
                progressBar.setVisibility(View.VISIBLE);
                getUserGroupChatData();
            }
        }
        setBroadCast();
    }

    private void setBroadCast() {
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ChatUser chatUser = null;
                    String type = intent.getStringExtra("type");
                    String sender_id = intent.getStringExtra("sender_id");
                    String show_sendername = intent.getStringExtra("show_sendername");
                    if (!type.equalsIgnoreCase("show_name")) {
                        if (!type.equalsIgnoreCase("block") || !type.equalsIgnoreCase("read")) {
                            String time = intent.getStringExtra("time");
                            String real_name = intent.getStringExtra("real_name");
                            String virtual_name = intent.getStringExtra("virtual_name");
                            String icon = intent.getStringExtra("icon");
                            String receiver_id = intent.getStringExtra("receiver_id");
                            String msg = intent.getStringExtra("msg");
                            String mid = intent.getStringExtra("mid");
                            String gid = intent.getStringExtra("gid");
                            if (gid.equals("")) {
                                chatUser = findObjByIdChat(sender_id);
                            } else chatUser = findObjById(receiver_id);
                            if (chatUser != null) {
                                chatUser.setMessages(msg);
                                chatUser.setTime(time);
                                if (Data.getChatUser_id() == null ||
                                        !Data.getChatUser_id().equals(sender_id))
                                    chatUser.setUnreadCount(chatUser.getUnreadCount() + 1);
                                sortListByDate();
                                adapter = new NewChatListUserAdapter(getActivity(), chatUserList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                // public ChatUser(int id, String name, String virtualName, String url, String msg, String msgTime) {
                                chatUser = new ChatUser(mid, sender_id, receiver_id, msg, real_name, show_sendername, time, icon, type);
                                chatUser.setGid(gid);
                                chatUser.setUnreadCount(1);
                                chatUserList.add(chatUser);
                                if (adapter == null) {
                                    adapter = new NewChatListUserAdapter(getActivity(), chatUserList);
                                    recyclerView.setAdapter(adapter);
                                }
                                sortListByDate();
                                adapter = new NewChatListUserAdapter(getActivity(), chatUserList);

                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        ChatUser user = findObjById(sender_id);
                        if (user != null) {
                            if (show_sendername.equalsIgnoreCase("1")) {
                                user.setShowSendername("1");
                            } else
                                user.setShowSendername("0");
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            };
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                    new IntentFilter(Data.RECEIVER_MSG));
        }
    }

    private ChatUser findObjById(String id) {
        ChatUser obj = null;
        for (ChatUser chatUser : chatUserList) {
            if (chatUser.getReceiverId().equals(id) || chatUser.getSenderId().equals(id)) {
                obj = chatUser;
                break;
            }
        }
        return obj;
    }

    private ChatUser findObjByIdChat(String id) {
        ChatUser obj = null;
        for (ChatUser chatUser : chatUserList) {
            if (chatUser.getType().equalsIgnoreCase("chat")) {
                if (chatUser.getReceiverId().equals(id) || chatUser.getSenderId().equals(id)) {
                    obj = chatUser;
                    break;
                }
            }
        }
        return obj;
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }

    private void floatingButtonClickListener(View view) {
        view.findViewById(R.id.addchat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowNeedDialog) {
                    isShowNeedDialog = false;
                    PopupWindow popupWindow = popupDisplayGroup();
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 50, view.getHeight() + 10);
                } else {
                    isShowNeedDialog = false;
                    Data.disabledView(view, 1000);
                    Intent intent = new Intent(getActivity(), GroupDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void getUserChatData() {
        if (chatUserList.size() <= 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sender_id", sharePreferenceData.getUserId(mActivity));
                        requestServer.sendStringPost(Data.GET_ALL_LAST_MSG, jsonObject, REQ_GET_ALL_USER_LAST_CHAT, false);
                    } catch (Exception ex) {
                    }
                }
            }, 1000);
        } else {
            progressBar.setVisibility(View.GONE);
            adapter = new NewChatListUserAdapter(getActivity(), chatUserList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }


    public PopupWindow popupDisplayGroup() {

        final PopupWindow popupWindow = new PopupWindow(getActivity());

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.hint_swipe_delete_dialog, null);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(4);
        }
        popupWindow.setClippingEnabled(true);
        ImageView imgHint = view.findViewById(R.id.imgHint);
        imgHint.setVisibility(View.GONE);
        LinearLayout llTextHint = view.findViewById(R.id.llTextHint);
        ImageView imgSwipe = view.findViewById(R.id.imgSwipe);
        TextView tvTextHint = view.findViewById(R.id.tvTextHint);
        ImageView imgSwipeRight = view.findViewById(R.id.imgSwipeRight);
        CardView cardClose = view.findViewById(R.id.cardClose);
        //imgHint.setImageResource(R.mipmap.hint_name);
        imgHint.setImageDrawable(null);
        imgHint.setVisibility(View.GONE);
        imgSwipeRight.setVisibility(View.VISIBLE);
        imgSwipe.setVisibility(View.GONE);
        //imgSwipe.setImageResource(R.drawable.ic_show_bottom);
        tvTextHint.setText("Click toggle button to create or join group.");
        cardClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    private void getUserGroupChatData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sender_id", sharePreferenceData.getUserId(mActivity));
            requestServer.sendStringPost(Data.GET_LAST_GROUP_MSG, jsonObject, REQ_GET_GROUP_CHAT_ALL, false);
        } catch (Exception ex) {
        }
    }

    public void sortListByDate() {
        if (chatUserList.size() > 0) {
            Collections.sort(chatUserList, new Comparator<ChatUser>() {
                @Override
                public int compare(ChatUser t1, ChatUser t2) {
                    try {
                        return formatDateTime.parse(t2.getTime()).compareTo(formatDateTime.parse(t1.getTime()));
                    } catch (Exception ex) {
                        return 0;
                    }
                }
            });
        }
    }

    private void swipeAction() {


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int pos = viewHolder.getAdapterPosition();
                if (pos >= chatUserList.size())
                    return 0;
                else {
                    ChatUser user = chatUserList.get(viewHolder.getAdapterPosition());
                    if (user.getType().equalsIgnoreCase("group")) {
                        return 0;
                    }

                }

                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT) {
                    adapter.notifyDataSetChanged();
                    showHideNameConfirmation(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {


                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {

                        p.setColor(Color.parseColor("#C3C2C5"));


                        icon = BitmapFactory.decodeResource(
                                getResources(), R.mipmap.pin2);
                        c.drawRect((float) itemView.getRight(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                        // Set the image icon for Right swipe
                        c.drawBitmap(icon,
                                (float) itemView.getRight() + convertDpToPx(16),
                                (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight()) / 2,
                                p);


                    } else {

                        ChatUser user = chatUserList.get(viewHolder.getAdapterPosition());
                        if (!user.getType().equalsIgnoreCase("group")) {
                            p.setColor(Color.parseColor("#D32F2F"));
                            icon = BitmapFactory.decodeResource(
                                    getResources(), R.mipmap.delete7);


                            c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                    (float) itemView.getRight(), (float) itemView.getBottom(), p);

                            c.drawBitmap(icon,
                                    (float) itemView.getRight() - convertDpToPx(16) - icon.getWidth(),
                                    (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight()) / 2,
                                    p);

                        }
                    }

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };

// attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    private void showHideNameConfirmation(final int position) {
        //chkboxNameChange.setChecked(isMyShowNameEnable);
        String title = "";
        String msg = "";
        title = "Confirmation to clear chat";
        msg = "Do you really want to  clear all this user chat ?";
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        chatUser = chatUserList.get(position);
                        if (!chatUser.getType().equalsIgnoreCase("group")) {
                            chatUser = chatUserList.get(position);
                            dialog.dismiss();
                            setDelete(chatUser);
                        } else
                            Toast.makeText(getActivity(), "Can't delete group message", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }


    private void setDelete(ChatUser chatUser) {
        try {
            try {
                String uid = chatUser.getSenderId();
                if (uid.equalsIgnoreCase(String.valueOf(sharePreferenceData.getUserId(mActivity)))) {
                    uid = chatUser.getReceiverId();
                }
                String url = Data.URL + "message/delete_allmsg.php";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sender_id", sharePreferenceData.getUserId(mActivity));
                jsonObject.put("receiver_id", uid);
                requestServer.sendStringPost(url, jsonObject, TYPE_CLEAR_CHAT, true);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        } catch (Exception ex) {
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.setCurrentScreen(FragmentNames._TEACHER_HOME);
        sortListByDate();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
}
