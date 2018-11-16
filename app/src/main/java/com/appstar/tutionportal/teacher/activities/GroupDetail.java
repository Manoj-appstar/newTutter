package com.appstar.tutionportal.teacher.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.volley.RequestServer;

import org.json.JSONArray;
import org.json.JSONObject;

public class GroupDetail extends AppCompatActivity implements OnResponseListener {
    CardView cvCreateGroup, cvViewGroup;
    ImageView imgJoin, imgCreate;
    TextView tvJoinGroup, tvCreateGroup;
    RequestServer requestServer;
    int REQ_JOIN_GROUP = 876;
    Activity mActivity;
    SharePreferenceData sharePreferenceData;
    String groupCode;
    Dialog dialog;
    TextView tvClassName;
    String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);
        getData();
        sharePreferenceData = new SharePreferenceData();
        mActivity = GroupDetail.this;
        initView();
        clickListener();

    }

    private void getData() {
        from = getIntent().getStringExtra("from");
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_detail_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.app_bar_view_group) {
         *//*   Intent intent = new Intent(GroupDetail.this, ViewMyAllGroups.class);
            startActivity(intent);*//*
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        // setToolBar();
        tvClassName = findViewById(R.id.tvClassName);
        tvClassName.setText("Group");
        cvCreateGroup = findViewById(R.id.cvCreateGroup);
        cvViewGroup = findViewById(R.id.cvViewGroup);
        imgJoin = findViewById(R.id.imgView);
        imgCreate = findViewById(R.id.imgCreate);
        tvJoinGroup = findViewById(R.id.tvViewGroup);
        tvCreateGroup = findViewById(R.id.tvCreateGroup);
        requestServer = new RequestServer(this, this);
    }

    private void setToolBar() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void clickListener() {
        findViewById(R.id.tvViewGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.disabledView(view);
                startActivity(new Intent(GroupDetail.this, ViewMyAllGroups.class));
            }
        });
        cvViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJoinGroupDialog();
            }
        });
        cvCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDetail.this, CreateGroup.class);
                startActivity(intent);
            }
        });
    }

    private void showJoinGroupDialog() {
        groupCode = "";
        dialog = new Dialog(GroupDetail.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.joind_group);
        final EditText etGroupCode = dialog.findViewById(R.id.etGroupCode);

    /*    if (Data.isNightModeEnabled) {
            ((LinearLayout) dialog.findViewById(R.id.llDiaogGroup)).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_dialog_black));
            etGroupCode.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_bg));
            ((TextView) dialog.findViewById(R.id.tvHint)).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.slow_white));
        } else
            etGroupCode.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_bg));
*/
        dialog.findViewById(R.id.btnJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etGroupCode.getText().toString().trim().length() < 5) {
                    etGroupCode.setError("Group code is not valid");
                    etGroupCode.requestFocus();
                } else {
                    try {
                        groupCode = etGroupCode.getText().toString().trim();
                     /*   Intent intent = new Intent(GroupDetail.this, SelectName.class);
                        intent.putExtra("from", 5);
                        startActivityForResult(intent, Data.GET_POST_NAME);*/

                    } catch (Exception ex) {
                    }
                }
            }
        });

        // dialog.findViewById(R.id.ln_dialog).setBackgroundColor(ContextCompat.getColor(context, Data.isNightModeEnabled ? R.color.slow_black : R.color.white_bg));

        // TextView tvAddHidi = dialog.findViewById(R.id.tvAddPost);

        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Data.GET_POST_NAME) {
            if (data != null) {
                final String name = data.getStringExtra("name");
                if (name.length() > 0) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject object = new JSONObject();
                                object.put("group_member_id", sharePreferenceData.getUserId(mActivity));
                                object.put("group_code", groupCode);
                                object.put("member_virtualname", name);
                                requestServer.sendStringPost(Data.JOIN_GROUP, object, REQ_JOIN_GROUP, true);
                            } catch (Exception ex) {
                            }
                        }
                    }, 300);

                    //createGroup();
                }
            }

        }

    }


    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (reqCode == REQ_JOIN_GROUP) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0) {
                    jsonObject = jsonArray.getJSONObject(0);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("success")) {
                        if (dialog != null) {
                            if (dialog.isShowing()) {
                                dialog.cancel();
                            }
                        }
                        Toast.makeText(getApplicationContext(), "You are added successfully.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(GroupDetail.this, ViewMyAllGroups.class);
                                startActivity(intent);
                            }
                        }, 400);


                    } else
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Group code is not valid.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
        }

        int a = 10;
        int b = 11;
        b = a;
        a = b;

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
