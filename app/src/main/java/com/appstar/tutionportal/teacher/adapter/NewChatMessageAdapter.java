package com.appstar.tutionportal.teacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.appstar.tutionportal.Model.ChatMessage;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.ChatActivity;
import com.appstar.tutionportal.util.SharePreferenceData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class NewChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private List<ChatMessage> chatList;
    //  DateFormat formatDate = new SimpleDateFormat("yyyy-MMdd");
    DateFormat formatTime = new SimpleDateFormat("hh:mm a", Locale.US);
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
    SharePreferenceData sharePreferenceData;
    public boolean noDataFound;
    private ChatActivity chatObj;
    Activity mActivity;

    public NewChatMessageAdapter(Context context, List<ChatMessage> chatWindowSets) {
        this.context = context;
        this.chatList = chatWindowSets;
    }

    public void setObject(ChatActivity obj) {
        this.chatObj = obj;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            ChatMessage chatMessage = chatList.get(position);
            sharePreferenceData = new SharePreferenceData();
        //    if (chatMessage.getSenderId() == Integer.parseInt(sharePreferenceData.getUserId(mActivity))) {
            if (chatMessage.getSenderId() == Integer.parseInt(String.valueOf(1234))) {
                holder.tvFromMsg.setText(chatMessage.getMessages());
                holder.linearReceive.setVisibility(View.GONE);
                holder.linearSend.setVisibility(View.VISIBLE);

                if (chatMessage.getRead() <= 0) {
                    if (!chatMessage.isSendToServer())
                        holder.imgStatus.setImageResource(R.drawable.ic_status0_new);
                    else
                        holder.imgStatus.setImageResource(R.drawable.ic_unread_status_new);
                } else
                    holder.imgStatus.setImageResource(R.drawable.ic_read_status);

                try {
                    holder.tvFromTime.setText(formatTime.format(formatDateTime.parse(chatMessage.getDateTime())));

              /*      StringTokenizer tk = new StringTokenizer(chatMessage.getDateTime());
                    String date = tk.nextToken();
                    String time = tk.nextToken();
                    Date dt = sdf.parse(time);
                    String time2 = sdfs.format(dt);
                    // holder.tvFromTime.setText(formatDate.format(d));
                    holder.tvFromTime.setText(time2);*/
                } catch (Exception ex) {
                }
            } else {
                holder.tvToMsg.setText(chatMessage.getMessages());
                //holder.tvToTime.setText(chatMessage.getTime());
                holder.linearReceive.setVisibility(View.VISIBLE);
                holder.linearSend.setVisibility(View.GONE);
                try {
                   /* StringTokenizer tk = new StringTokenizer(chatMessage.getDateTime());
                    String date = tk.nextToken();
                    String time = tk.nextToken();
                    Date dt = sdf.parse(time);
                    String time2 = sdfs.format(dt);
                    holder.tvToTime.setText(time2);*/

                    holder.tvToTime.setText(formatTime.format(formatDateTime.parse(chatMessage.getDateTime())));
                } catch (Exception ex) {
                }
            }
            holder.rlData.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (chatObj != null)
                        chatObj.onLongClick(position);
                    return true;
                }
            });
            holder.rlData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chatObj != null)
                        chatObj.onClick(position);
                }
            });


            if (chatMessage.isSelected()) {
                holder.rlData.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
            } else {
                holder.rlData.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }
            if (chatObj != null)
                chatObj.setDateOnTop(chatMessage.getDateTime(), position);
            holder.setIsRecyclable(false);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);

            return new LoadingViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item, parent, false);
            ViewHolder viewHoldermine = new ViewHolder(v);
            return viewHoldermine;
        }

        return null;
    }


    @Override
    public int getItemViewType(int position) {

        return VIEW_TYPE_ITEM;

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFromMsg, tvFromTime, tvToMsg, tvToTime;
        LinearLayout linearSend, linearReceive;
        RelativeLayout rlData;
        ImageView imgStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            rlData = itemView.findViewById(R.id.rlData);
            tvFromMsg = itemView.findViewById(R.id.tvFromMsg);
            tvToMsg = itemView.findViewById(R.id.tvToMsg);
            tvFromTime = itemView.findViewById(R.id.tvFromTime);
            tvToTime = itemView.findViewById(R.id.tvToTime);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            linearSend = itemView.findViewById(R.id.llFrom);
            linearReceive = itemView.findViewById(R.id.llTo);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

}

