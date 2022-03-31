package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FindBookActivity extends AppCompatActivity implements RecycleViewAdapter.ItemClickListener {
    String [] testTitle = {"The lord of the ring", "Rich dad poor dad", "Sherlock Home", "Robinson Crusue"};
    String [] testStatus = {"Rent", "Share", "Give away", "Share"};
    RecyclerView recyclerView;
    BookDatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);

         recyclerView = findViewById(R.id.availBook_RecycleView);
        int numOfCol =1;


        databaseHelper = new BookDatabaseHelper(this);
        Cursor cursor = databaseHelper.GetBooksByReaderId(1);


        RecycleViewAdapter adapter = new RecycleViewAdapter(this, testTitle,testStatus);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(FindBookActivity.this, "Test Sucessful", Toast.LENGTH_SHORT).show();
    }
}