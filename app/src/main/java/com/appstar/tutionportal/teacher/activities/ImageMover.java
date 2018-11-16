package com.appstar.tutionportal.teacher.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.appstar.avatar.TinderRecyclerView;
import com.appstar.tutionportal.R;

import java.util.ArrayList;
import java.util.List;

public class ImageMover extends AppCompatActivity {
    List<String> stringList = new ArrayList<>();
    TinderRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagemover);
        initView();
    }


    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void getData() {
    }
}
