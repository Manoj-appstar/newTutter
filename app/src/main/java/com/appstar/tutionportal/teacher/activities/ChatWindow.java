package com.appstar.tutionportal.teacher.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.appstar.tutionportal.Model.MessageListener;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.Notification;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity implements MessageListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Data.setMessageListener(null);
        Data.setChatUser_id("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageAdded(Intent intent) {

    }

    @Override
    public void onErrorMessage() {

    }
}