package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.Model.ChatUser;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.ChatActivity;
import com.appstar.tutionportal.teacher.activities.GroupChatActivity;
import com.appstar.tutionportal.util.Data;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewChatListUserAdapter extends RecyclerView.Adapter<NewChatListUserAdapter.ViewHolder> {
    List<ChatUser> chatList;
    DateFormat formatDate = new SimpleDateFormat("MMMM dd, yyyy");
    DateFormat formatTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int groupSrc;
    private Context context;


    public NewChatListUserAdapter(Context context, List<ChatUser> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ChatUser chatUser = chatList.get(position);
        holder.tvUserName.setText(chatUser.getRealName());
        holder.tvMessage.setText(chatUser.getMessages());
        Glide.with(context).load(R.drawable.temp_profile).apply(RequestOptions.circleCropTransform()).into(holder.logo);
        holder.linearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chatUser.getType().equals("group")) {
                    Data.setChatUser(chatUser);
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    Data.setChatUser(chatUser);
                    Intent intent = new Intent(context, GroupChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    chatUser.setUnreadCount(0);
                    context.startActivity(intent);
                    // Toast.makeText(context, "Group page is on production", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (chatUser.getType().equals("chat")) {
            if (chatUser.isSelected()) {
                holder.linearData.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else {
                holder.linearData.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
            holder.imgGroup.setVisibility(View.GONE);

        }
        try {
            if (isToday(formatDateTime.parse(chatUser.getTime()))) {
                holder.tvTime.setText(formatTime.format(formatDateTime.parse(chatUser.getTime())));
            } else
                holder.tvTime.setText(formatDate.format(formatDateTime.parse(chatUser.getTime())));

        } catch (Exception ex) {
        }


        if (chatUser.getUnreadCount() > 0) {
            holder.cardUnReadCount.setVisibility(View.VISIBLE);
            holder.tvUnReadCount.setText(String.valueOf(chatUser.getUnreadCount()));
        } else {
            holder.cardUnReadCount.setVisibility(View.GONE);
        }

        holder.linearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chatUser.getType().equals("group")) {
                    Data.setChatUser(chatUser);
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("uid","123");
                    intent.putExtra("realName","123");
                    intent.putExtra("msg","123");
                    intent.putExtra("msgTime","123");
                    intent.putExtra("url","123");
                    intent.putExtra("isShowRealName",false);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  //  context.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                    context.startActivity(intent);

                } else {
                    Data.setChatUser(chatUser);
                    Intent intent = new Intent(context, GroupChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    chatUser.setUnreadCount(0);
                    context.startActivity(intent);
                    // Toast.makeText(context, "Group page is on production", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (chatUser.getType().equalsIgnoreCase("chat"))
            holder.setIsRecyclable(false);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public int getItemCount() {

        return chatList.size();
    }

    public boolean isToday(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                && today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                && today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo, imgGroup;
        TextView tvUserName, tvTime, tvMessage, tvUnReadCount;
        LinearLayout linearData;
        CardView cardUnReadCount;


        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvTime = itemView.findViewById(R.id.tvTime);
            logo = itemView.findViewById(R.id.logo);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            linearData = itemView.findViewById(R.id.linearData);
            imgGroup = itemView.findViewById(R.id.imgGroup);
            cardUnReadCount = itemView.findViewById(R.id.cardUnReadCount);
            tvUnReadCount = itemView.findViewById(R.id.tvUnReadCount);
        }
    }
}
