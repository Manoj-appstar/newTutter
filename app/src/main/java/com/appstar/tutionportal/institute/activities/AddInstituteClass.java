package com.appstar.tutionportal.institute.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.customgallery.GalleryFolder;
import com.appstar.customgallery.ImageGallery;
import com.appstar.customgallery.MediaChooserConstants;
import com.appstar.tutionportal.Dialog.DialogError;
import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FilePath;
import com.appstar.tutionportal.student.extras.UrlManager;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.Data;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsInstitute;
import com.appstar.tutionportal.volley.RequestServer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddInstituteClass extends AppCompatActivity implements OnResponseListener {
    CardView cvCancel, cvAdd;
    EditText etInstituteName, etInstituteLocation, etWebsite;
    ImageView ivInstituteImage, ivAddImage;
    TextView tvAdd;
    RequestServer requestServer;
    int REQ_ADD = 198;
    List<String> listData = new ArrayList<>();
    String path1;
    private Activity mActivity;
    private Uri filePath;
    private SharePreferenceData sharePreferenceData;
    private UtilsInstitute utilsInstitute;
    private ArrayList<Institute> instituteArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addinstitute_name);
        mActivity = AddInstituteClass.this;
        sharePreferenceData = new SharePreferenceData();
        utilsInstitute = new UtilsInstitute();
        requestServer = new RequestServer(this, this);
        findView();
        onClick();

    }

    private void findView() {
        etInstituteName = findViewById(R.id.etInstituteName);
        etInstituteLocation = findViewById(R.id.etInstituteLocation);
        ivInstituteImage = findViewById(R.id.ivInstituteIMage);
        ivAddImage = findViewById(R.id.ivAddImage);
        etWebsite = findViewById(R.id.etWebsite);
        cvAdd = findViewById(R.id.cvAdd);
        cvCancel = findViewById(R.id.cvCancel);
        tvAdd = findViewById(R.id.tvAdd);
    }

    private void onClick() {
        cvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(AddInstituteClass.this, InstituteDashboard.class);
                startActivity(intent);*/

                finish();
            }
        });
        cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(AddInstituteClass.this, AddInstituteBranch.class);
                intent.putExtra("institute_id", "fafafa");
                intent.putExtra("institute_name", "asfafasf");
                startActivity(intent);*/
                callAPI();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Intent intent = new Intent(AddInstituteClass.this, AddInstituteBranch.class);
                intent.putExtra("institute_id", "sdfsdgfd");
                intent.putExtra("institute_name", "dsfsdfs");
                startActivity(intent);*/
                callAPI();
            }
        });

        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path1 = FilePath.getPath(getApplicationContext(), filePath);
            ivInstituteImage.setImageURI(filePath);
        }
        if (requestCode == MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE) {
            if (data != null) {
                try {
                    listData = data.getStringArrayListExtra("paths");
                    Glide.with(getApplicationContext()).load(new File(listData.get(0).toString())).apply(RequestOptions.circleCropTransform()).into(ivInstituteImage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void callAPI() {
        if (validation()) {
            try {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("director_id", sharePreferenceData.getUserId(getApplicationContext()));
                hashMap.put("name", etInstituteName.getText().toString().trim());
                hashMap.put("description", etInstituteLocation.getText().toString().trim());
                hashMap.put("website", etWebsite.getText().toString().trim());
                hashMap.put("phone_no", "7080122321");
                hashMap.put("email_id", "mn@mail.com");
                hashMap.put("status", "1");
                requestServer.uploadMultiDataWithImage(REQ_ADD, UrlManager.Add_Institute,
                        listData.size() > 0 ? listData.get(0).toString() : "", hashMap, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validation() {
        boolean bool = false;
        try {

            if (etInstituteName.getText().toString().trim().equals("")) {

                Snackbar.make(etInstituteName, "Enter your institute name", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else if (etInstituteName.getText().toString().trim().length() < 3) {

                Snackbar.make(etInstituteName, "Institute name is too short", Snackbar.LENGTH_SHORT).show();
                bool = false;
            } else
                bool = true;

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return bool;
    }

    @Override
    public void onSuccess(int reqCode, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (reqCode == REQ_ADD) {
                if (jsonObject.has("status")) {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        Gson gson = new Gson();
                        //  Institute institute = new Institute();
                        Type category = new TypeToken<Institute>() {
                        }.getType();
                        Institute userDetail = gson.fromJson(jsonObject.getJSONObject("data").toString(), category);
                        //  Data.setInstituteList(userDetail);
                        Data.getInstituteList().add(userDetail);
                        Intent intent = new Intent(AddInstituteClass.this, AddInstituteBranch.class);
                        intent.putExtra("institute_id", userDetail.getInstituteId());
                        intent.putExtra("institute_name", userDetail.getName());
                        startActivity(intent);
                        finish();
                    } else {
                        DialogError.setMessage(mActivity, jsonObject.getString("message"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
