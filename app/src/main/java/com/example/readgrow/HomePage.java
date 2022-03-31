package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button addOrUpdate;
    private Button findBook;
    private Button readingTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        addOrUpdate = findViewById(R.id.home_addBook);
        findBook = findViewById(R.id.home_findBook);
        readingTracker = findViewById(R.id.home_readingTracker);

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