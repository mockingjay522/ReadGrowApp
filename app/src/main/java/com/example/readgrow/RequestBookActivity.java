package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class RequestBookActivity extends AppCompatActivity {
    TextView bookName;
    SharedPreferences preferFrom_FindBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_book);

        bookName = findViewById(R.id.requestBook_bookname);
        preferFrom_FindBook = PreferenceManager.getDefaultSharedPreferences(this);
        int picked_BookID = preferFrom_FindBook.getInt("interest_bookID", 0);
        bookName.setText(Integer.toString(picked_BookID));

    }

}