package com.example.fall2019;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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



        Log.e(ACTIVITY_NAME, "In function:" + "onCreate");
        String preEmail = getIntent().getStringExtra("email");
        EditText editText = findViewById(R.id.editText7);
        editText.setText(preEmail);

        mImageButton = (ImageButton)findViewById(R.id.imageButton3);
        if(mImageButton != null)
            mImageButton.setOnClickListener(v -> dispatchTakePictureIntent());



            //GO TO CHAT
            Button goToChat = findViewById(R.id.button3);
            if(goToChat != null)
                goToChat.setOnClickListener(e -> {

                    Intent goToChatPage = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                    startActivity(goToChatPage);
                });


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

        Log.e(ACTIVITY_NAME, "In function:" + "onActivityResult");
    }

    @Override
    protected void onStart() {
        super.onStart();
       Log.e(ACTIVITY_NAME, "In function:" + "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
       Log.e(ACTIVITY_NAME, "In function:" + "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");
    }


}
