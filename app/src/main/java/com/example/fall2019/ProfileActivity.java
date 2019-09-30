package com.example.fall2019;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        mImageButton = (ImageButton)findViewById(R.id.imageButton3);

        if(mImageButton != null)
            mImageButton.setOnClickListener(v -> dispatchTakePictureIntent());

        Log.e(ACTIVITY_NAME, "In function:" + "onCreate");
        Log.e(ACTIVITY_NAME, "In function:" + "onStart");
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");
        Log.e(ACTIVITY_NAME, "In function:" + "onPause");
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");
        Log.e(ACTIVITY_NAME, "In function:" + "onActivityResultonStart");

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }


}
