package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

     Button addOrUpdate,findBook,readingTracker;
     TextView userNameHoder; // (Hi User)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        // define controllers
        addOrUpdate = findViewById(R.id.home_addBook);
        findBook = findViewById(R.id.home_findBook);
        readingTracker = findViewById(R.id.home_readingTracker);
        userNameHoder = findViewById(R.id.home_text);

        // get the user name from SharedPreferences to be spotted in home_text (Hi User)
        SharedPreferences shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);
        userNameHoder.setText(shareFromLogin.getString("userName",""));


        addOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, AddBook.class));
            }
        });

        findBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, FindBookActivity.class));
            }
        });

        readingTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, ReadingTracker.class));
            }
        });
    }
}