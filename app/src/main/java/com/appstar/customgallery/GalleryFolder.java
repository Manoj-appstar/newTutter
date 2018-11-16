package com.appstar.customgallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appstar.tutionportal.R;

import java.util.ArrayList;

public class GalleryFolder extends AppCompatActivity {
    GridView mGridView;
    ProgressBar progressBar;
    private Cursor mCursor;
    ImageView ivBack;
    String position;

    private final int INDEX_BUCKET_ID = 0;
    private final int INDEX_BUCKET_NAME = 1;
    private final int INDEX_BUCKET_URL = 2;
    BucketGridAdapter mBucketAdapter;
    private static final String[] PROJECTION_BUCKET = {
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        getData();
        setToolBar();
        initView();
        loadImages();
    }
    private void getData() {
        position= (getIntent().getStringExtra("position"));
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        progressBar = findViewById(R.id.progress);
        mGridView = findViewById(R.id.gridView);
    }

    private void loadImages() {
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        mCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PROJECTION_BUCKET, null, null, orderBy + " DESC");
        ArrayList<BucketEntry> buffer = new ArrayList<BucketEntry>();
        while (mCursor.moveToNext()) {
            BucketEntry entry = new BucketEntry(
                    mCursor.getInt(INDEX_BUCKET_ID),
                    mCursor.getString(INDEX_BUCKET_NAME), mCursor.getString(INDEX_BUCKET_URL));
            Log.d("url", mCursor.getString(INDEX_BUCKET_URL));

            // if (!entry.bucketUrl.contains("Hidi/sel.jpg")) {
            Log.d("Inserted", entry.bucketUrl);
            if (!buffer.contains(entry)) {
                buffer.add(entry);
            }
            // }
        }
        if (mCursor.getCount() > 0) {
            mBucketAdapter = new BucketGridAdapter(getApplicationContext(), 0, buffer, false);
            mGridView.setAdapter(mBucketAdapter);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_media_file_available), Toast.LENGTH_SHORT).show();
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BucketEntry bucketEntry = (BucketEntry) adapterView.getItemAtPosition(position);
                Intent selectImageIntent = new Intent(GalleryFolder.this, ImageGallery.class);
                selectImageIntent.putExtra("name", bucketEntry.bucketName);
                selectImageIntent.putExtra("image", true);
                selectImageIntent.putExtra("position",position);
                selectImageIntent.putExtra("isFromBucket", true);
                startActivityForResult(selectImageIntent, MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE);

            }
        });

        progressBar.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE) {
            if (data != null) {
                ArrayList<String> paths = data.getStringArrayListExtra("paths");
                //   Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("paths", paths);
                intent.putExtra("position", position);
                setResult(MediaChooserConstants.BUCKET_SELECT_IMAGE_CODE, intent);
                finish();
            }
        }

    }



}