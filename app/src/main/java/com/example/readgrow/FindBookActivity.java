package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class FindBookActivity extends AppCompatActivity{
    BookDatabaseHelper databaseHelper;
    Spinner spinner;
    Button sortBtn;
    Button searchBtn;
    ListView resultListView;
    private Object ArrayList;
    EditText txtLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);

        instantiateControllers();

        searchBtn.setOnClickListener(this::SortOption);

    }

    private void SortOption(View view) {
//        int sortOption = spinner.getSelectedItemPosition();
        List<BookInfo> books = new  ArrayList<>();
        String location = txtLocation.getText().toString().trim();
        Cursor getBooks =  databaseHelper.GetBookByPostalCode(location);

        if(getBooks.getCount()>0)
            while(getBooks.moveToNext())
                books.add(new BookInfo(getBooks.getInt(0),
                        getBooks.getString(1)
                        ,getBooks.getString(2)
                        ,getBooks.getString(3)
                        ,getBooks.getString(4)
                        ,getBooks.getInt(5)));

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,books);

        resultListView.setAdapter(adapter);

    }

    public void instantiateControllers(){
        databaseHelper = new BookDatabaseHelper(this);
        spinner = findViewById(R.id.searchBookSpinner);
        sortBtn = findViewById(R.id.btnSortOption);
        resultListView = findViewById(R.id.availBookListView);
        txtLocation = findViewById(R.id.txtPostalCode);
        searchBtn = findViewById(R.id.btnSearch);

    }

}