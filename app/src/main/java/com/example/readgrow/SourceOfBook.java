package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SourceOfBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_of_book);

        Button btnReadingOnline = findViewById(R.id.btn_ReadingOnline);
        Button btnBorrowBook = findViewById(R.id.btn_BorrowBook);

        btnReadingOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bookbub.com/welcome")));
            }
        });

        btnBorrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SourceOfBook.this, FindBookActivity.class));
            }
        });
    }
}