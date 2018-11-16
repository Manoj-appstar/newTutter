package com.appstar.tutionportal.teacher.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.customgallery.GalleryFolder;
import com.appstar.customgallery.ImageGallery;
import com.appstar.customgallery.MediaChooserConstants;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FilePath;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectUserImage extends AppCompatActivity {
    String path1;
    ImageView imgProfile;
    Button btnCancel, btnDone;
    String paths;
    List<String> listData = new ArrayList<>();
    int i = 0;
    private Uri filePath;
    private Utils utils;
    private Activity mActivity;
    private SharePreferenceData sharePreferenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selector);
        utils = new Utils();
        mActivity = this;
        sharePreferenceData = new SharePreferenceData();
        imgProfile = findViewById(R.id.imgProfile);
        Glide.with(mActivity).load(sharePreferenceData.getUserImage(mActivity)).into(imgProfile);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvClassName = findViewById(R.id.tvClassName);
        tvClassName.setText("Change Your Profile Pic");
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("path", listData.get(0).toString());
                setResult(12345, intent);
                finish();
            }
        });
        //  openImagePicker();
        openImageChooser();
    }

    private Activity openImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                goToImageIntent();
            }
        } else {
            goToImageIntent();
        }
        return null;
    }

    public void openImageChooser() {

        ImageGallery.MAX_IMAGE_LIMIT = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                Intent intent = new Intent(mActivity, GalleryFolder.class);
                intent.putExtra("position", 1);
                startActivityForResult(intent, MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE);
            }
        } else {
            startActivityForResult(new Intent(mActivity, GalleryFolder.class), MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE);
        }
    }


    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }

    public void goToImageIntent() {
//      isPermissionGivenAlready = true
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path1 = FilePath.getPath(getApplicationContext(), filePath);
            imgProfile.setImageURI(filePath);

        }
        if (requestCode == MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE) {
            if (data != null) {
                try {
                    listData = data.getStringArrayListExtra("paths");
                    Glide.with(getApplicationContext()).load(new File(listData.get(0).toString())).into(imgProfile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
