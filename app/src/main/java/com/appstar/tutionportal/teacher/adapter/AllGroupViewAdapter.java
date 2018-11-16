package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.util.SharePreferenceData;

import java.security.acl.Group;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class AllGroupViewAdapter extends RecyclerView.Adapter<AllGroupViewAdapter.ViewHolder> {
    List<Group> groupList;
    DateFormat formatDate = new SimpleDateFormat("MMMM dd, yyyy");
    DateFormat formatTime = new SimpleDateFormat("hh:mm a");
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    int groupSrc;
    SharePreferenceData sharePreferenceData;
    String myId;
    private Context context;

    public AllGroupViewAdapter(Context context, List<Group> groupList) {
        this.context = context;
        this.groupList = groupList;
        sharePreferenceData = new SharePreferenceData(context);
    }

    public void setMyId(String id) {
        myId = id;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Group group = groupList.get(position);
       /* if (!TextUtils.isEmpty(group.getGroupIcon())) {
            holder.logo.setImageResource(groupSrc);
           // Picasso.with(context).load(group.getGroupIcon()).placeholder(groupSrc).into(holder.logo);
        } else
            holder.logo.setImageResource(groupSrc);
        holder.tvGroupName.setText(group.getGroupName());*/
        // here you are admin
    /*    if (group.getGroupAdmin().equals(String.valueOf(sharePreferenceData.getUserId(context))))) {
            holder.imgAdmin.setVisibility(View.VISIBLE);
            holder.tvYourName.setText("Your virtual name is-:  " + group.getGroupAdminName());

            holder.imgAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (group.getGroupAdmin().equals(myId)) {
                        Intent intent = new Intent(context, GroupAdminSetting.class);
                        intent.putExtra("group_code", group.getGroupCode());
                        intent.putExtra("group_name", group.getGroupName());
                        intent.putExtra("isAdmin", true);
                        intent.putExtra("needGet", false);
                        // intent.putExtra("obj", group);
                        context.startActivity(intent);

                    }
                }
            });
        } else {
            if (TextUtils.isEmpty(group.getMyVirtualName())) {
                group.setMyVirtualName(getVirtuelName(group));
                holder.tvYourName.setText("Your virtual name is-:  " + group.getMyVirtualName());
            } else {
                holder.tvYourName.setText(Html.fromHtml("Your virtual name is-:  <b>" + group.getMyVirtualName() + "</b>"));
            }
            holder.imgAdmin.setVisibility(View.GONE);


        }*/

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    if (group.getGroupAdmin().equals(myId)) {

                    Intent intent = new Intent(context, GroupAdminSetting.class);
                    intent.putExtra("group_code", group.getGroupCode());
                    intent.putExtra("group_name", group.getGroupName());
                    intent.putExtra("needGet", false);
                    intent.putExtra("obj", group);
                    context.startActivity(intent);
                } else {*/
            /*    String virtualName = "";
                if (group.getGroupAdmin().equals(String.valueOf(sharePreferenceData.getUserId(context)))) {
                    virtualName = group.getGroupAdminName();
                } else {
                    virtualName = group.getMyVirtualName();
                }
                Data.setChatUser(null);
                Intent intent = new Intent(context, GroupChatActivity.class);
                intent.putExtra("gid", group.getGroupId());
                intent.putExtra("groupName", group.getGroupName());
                intent.putExtra("groupCode", group.getGroupCode());
                intent.putExtra("vName", virtualName);
                intent.putExtra("url", group.getGroupIcon());
                intent.putExtra("admin_id", group.getGroupAdmin());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
                // }
            }
        });

    }


    private String getVirtuelName(Group group) {
        String name = "";
        String uid = String.valueOf(sharePreferenceData.getUserId(context));
    /*    String[] memberIds = group.getGroupMember().split(",");
        String[] memberNames = group.getVirtualName().split(",");
        for (int i = 0; i < memberIds.length; i++) {
            String obj = memberIds[i];
            if (obj.equals(uid)) {
                name = memberNames[i];
                break;
            }
        }*/

        return name;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_group_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        //   groupSrc = Data.isNightModeEnabled ? R.drawable.ic_group_default_white : R.drawable.ic_group_default_black;
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo, imgAdmin;
        TextView tvGroupName, tvYourName;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            imgAdmin = itemView.findViewById(R.id.imgAdmin);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvYourName = itemView.findViewById(R.id.tvYourName);
            layout = itemView.findViewById(R.id.linearData);
        }
    }


}
