package com.example.fall2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;





import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shared Preferences
        EditText editText = findViewById(R.id.editText2);
        SharedPreferences prefs = getSharedPreferences("SavedEmail", MODE_PRIVATE);
        String previous = prefs.getString("ReserveName", "");

        editText.setText(previous);

        Button loginButton = findViewById(R.id.button);
        if(loginButton != null)
            loginButton.setOnClickListener(e -> {
                Intent goToPage2 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(goToPage2);
            });
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText editText = findViewById(R.id.editText2);
        SharedPreferences prefs = getSharedPreferences("SavedEmail", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", editText.getText().toString());
        editor.commit();
    }






}
