package com.appstar.customgallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.tutionportal.R;

import java.util.ArrayList;

public class ImageGallery extends AppCompatActivity {

    public static int MAX_IMAGE_LIMIT = 6;
    Cursor mImageCursor;
    GridView gridView;
    ImageGalleryAdapter galleryAdapter;
    TextView tvUserName, tvOk;
    ProgressBar progressBar;
    int selectedCounter;
    private ArrayList<String> mSelectedItems = new ArrayList<String>();
    private ArrayList<SelectedImage> mGalleryModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        setToolBar();
        initView();
        getData();
        onClickListener();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvUserName = findViewById(R.id.tvName);
        tvOk = findViewById(R.id.tvOk);
        tvOk.setVisibility(View.VISIBLE);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onClickListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("paths", getSelectedImage());
                setResult(MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE, intent);
                finish();
            }
        });
    }

    private void getData() {
        initPhoneImages(getIntent().getStringExtra("name"));
    }

    private void initView() {
        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.progress);
    }

    private void initPhoneImages(final String folderName) {
        try {
            tvUserName.setText(folderName);
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            String searchParams = null;
            String bucket = folderName;
            searchParams = "bucket_display_name = \"" + bucket + "\"";

            final String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID};

            mImageCursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    searchParams, null, orderBy + " DESC");

            for (int i = 0; i < mImageCursor.getCount(); i++) {
                mImageCursor.moveToPosition(i);
                int dataColumnIndex = mImageCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA);
                // MediaModel galleryModel = new MediaModel(mImageCursor.getString(
                //        dataColumnIndex).toString(), false);
                String path = mImageCursor.getString(
                        dataColumnIndex).toString();
                SelectedImage selectedImage = new SelectedImage();
                selectedImage.setPath(path);
                if (!path.contains("Hidi/sel.jpg"))
                    mGalleryModelList.add(selectedImage);
            }

            progressBar.setVisibility(View.GONE);
            galleryAdapter = new ImageGalleryAdapter(getApplicationContext(), R.layout.gallery_view_grid_bucket_item_media_chooser, mGalleryModelList);
            gridView.setAdapter(galleryAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //  Toast.makeText(getApplicationContext(), mGalleryModelList.get(position), Toast.LENGTH_SHORT).show();
                    SelectedImage selectedImage = mGalleryModelList.get(position);
                    if (MAX_IMAGE_LIMIT == 1) {
                        selectedImage.setSelected(true);
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("paths", getSelectedImage());
                        setResult(MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE, intent);
                        finish();
                    } else {

                        if (selectedImage.isSelected()) {
                            selectedImage.setSelected(false);
                            galleryAdapter.notifyDataSetChanged();
                            selectedCounter--;
                        } else {
                            if (selectedCounter < MAX_IMAGE_LIMIT) {
                                selectedImage.setSelected(true);
                                galleryAdapter.notifyDataSetChanged();
                                selectedCounter++;


                            } else
                                Toast.makeText(getApplicationContext(), "You can't select more than " + MAX_IMAGE_LIMIT, Toast.LENGTH_SHORT).show();
                        }
                        if (selectedCounter <= 0)
                            tvUserName.setText(folderName);
                        else
                            tvUserName.setText(folderName + "  (" + selectedCounter + ")");
                    }
                    //mGalleryModelList.get(position).setSelected(!mGalleryModelList.get(position).isSelected());


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isSelectedMaxLimit() {
        boolean bool = false;
        int counter = 0;
        for (SelectedImage obj : mGalleryModelList) {
            if (obj.isSelected() && counter <= MAX_IMAGE_LIMIT)
                counter++;
        }
        if (counter < MAX_IMAGE_LIMIT)
            return false;
        else return true;

    }

    private ArrayList<String> getSelectedImage() {
        ArrayList<String> stringList = new ArrayList<>();
        for (SelectedImage obj : mGalleryModelList) {
            if (obj.isSelected()) {
                stringList.add(obj.getPath());
            }
        }
        return stringList;
    }

}
