package com.appstar.image;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.customgallery.SelectedImage;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.util.PathUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;

public class AllImageViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    ArrayList<String> sList = new ArrayList<>();
    ArrayList<SelectedImage> selImages = new ArrayList<>();
    SelectedImageAdapter adapter;
    String path;
    ActionMode.Callback mActionModeCallback;
    boolean isMultiSelected;
    int countSelected;
    int selectedPosition;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_images);
        initView();
        getData();
        setToolBar();
        initMode();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //   tvUserName = findViewById(R.id.tvName);
        TextView tvOk = findViewById(R.id.tvOk);
//        tvOk.setVisibility(View.VISIBLE);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleView);
    }

    private void getData() {
        try {
            sList = getIntent().getStringArrayListExtra("paths");
        } catch (Exception ex) {
        }
        if (sList.size() > 0) {
            for (String obj : sList) {
                SelectedImage selectedImage = new SelectedImage();
                selectedImage.setPath(obj);
                selImages.add(selectedImage);
            }
            layoutManager = new GridLayoutManager(AllImageViewActivity.this, 2);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new SelectedImageAdapter(AllImageViewActivity.this, selImages);
            adapter.setObject(this);
            recyclerView.setAdapter(adapter);
        }
    }


    private void initMode() {
        mActionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.image_delete_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // Return false if nothing is done
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                if (item.getItemId() == R.id.item_delete) {
                    //  showHideNameConfirmation(TYPE_DELETE_MESSAGE);
                    showHideNameConfirmation();
                }
                return true;

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
             /*   isMultiSelected = false;
                for (ChatMessage chatMessage : selectedList) {
                    chatMessage.setSelected(false);
                }
                adapter.notifyDataSetChanged();
                selectedList.clear();*/
            }

        };
    }


    public void onLongClick(int position) {
        if (!isMultiSelected) {
            countSelected++;
            isMultiSelected = true;
            if (mActionMode == null) {
                mActionMode = startActionMode(mActionModeCallback);
                selImages.get(position).setSelected(true);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onClick(int position) {
        SelectedImage selectedImage = selImages.get(position);
        if (isMultiSelected) {
            if (!selectedImage.isSelected()) {
                countSelected++;
            } else
                countSelected--;
            isMultiSelected = true;
            selImages.get(position).setSelected(!selectedImage.isSelected());
            adapter.notifyDataSetChanged();
            if (countSelected <= 0) {
                isMultiSelected = false;
                mActionMode.finish();
                mActionMode = null;
            }
        } else {
            selectedPosition = position;
            Toast.makeText(getApplicationContext(), selectedImage.getPath(), Toast.LENGTH_SHORT).show();
            CropImage.activity(Uri.fromFile(new File(selectedImage.getPath()))).setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
    }

    private boolean getTotalSelectedItem() {
        boolean bool = false;
        for (SelectedImage selectedImage : selImages) {
            if (selectedImage.isSelected()) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    private void showHideNameConfirmation() {
        //chkboxNameChange.setChecked(isMyShowNameEnable);
        String title = "";
        String msg = "";

        title = "Confirmation to remove image";
        msg = "Do you really want to remove images ?";
        new AlertDialog.Builder(AllImageViewActivity.this)
                .setTitle(title)
                .setMessage(msg)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        removeImages();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }


    private void removeImages() {
        ArrayList<SelectedImage> list = new ArrayList();
        for (SelectedImage selectedImage : selImages) {
            if (!selectedImage.isSelected()) {
                {
                    list.add(selectedImage);
                }
            }
        }
        selImages.clear();
        selImages.addAll(list);
        adapter = new SelectedImageAdapter(AllImageViewActivity.this, selImages);
        recyclerView.setAdapter(adapter);
        if (mActionMode != null) {
            mActionMode.finish();
            isMultiSelected = false;
            mActionMode = null;
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    String path = PathUtil.getPath(getApplicationContext(), resultUri);
                    Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
                    if (!TextUtils.isEmpty(path)) {
                        selImages.get(selectedPosition).setPath(path);
                        adapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(getApplicationContext(), "Error to crop image", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
