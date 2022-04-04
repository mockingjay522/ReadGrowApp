package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FindBookActivity extends AppCompatActivity {
    BookDatabaseHelper databaseHelper;
    Spinner spinner;
    Button sortBtn;
    Button searchBtn;
    ListView resultListView;
    private Object ArrayList;
    EditText txtLocation;
    //TextView test;
    SharedPreferences preferFrom_FindBook;
    SharedPreferences shareFromLogin;
    String location;
    int ID_login;
    List<BookInfo> books = new ArrayList<>();
    ArrayList <String > listTitle = new ArrayList<>();
    ArrayList <Integer> list_Num_Status = new ArrayList<>();
    ArrayList <String> bookStatus = new ArrayList<>();
    ArrayList <Integer> listBook_ID = new ArrayList<>();
    List<HashMap<String, String>> list_Added_Book;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);


        databaseHelper = new BookDatabaseHelper(this);

        sortBtn = findViewById(R.id.btnSortOption);
        resultListView = findViewById(R.id.availBookListView);
        txtLocation = findViewById(R.id.txtPostalCode);
        searchBtn = findViewById(R.id.btnSearch);
        preferFrom_FindBook = PreferenceManager.getDefaultSharedPreferences(this);
        shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**Check if user missing input postal code*/

                if (TextUtils.isEmpty(txtLocation.getText().toString())) {
                    txtLocation.setError("Please enter a postal code");
                } else {
                    location = txtLocation.getText().toString().trim();
                }

                /**get SharedPreference when user login*/
                ID_login = Integer.parseInt(shareFromLogin.getString("userID", "0"));

                /**Get data from DB base on postal code, userID*/
                Cursor cursorSecondary = databaseHelper.shareBookDB.rawQuery(
                "select distinct b.book_id, b.title, b.book_status, b.reader_id \n" +
                        " from book b join book_reader u on b.reader_id = u.reader_id \n" +
                        " where u.postal_code like " +"\"" + location + "\""+ " and b.reader_id != " + ID_login +
                        " and u.user_status = 1 and b.book_status < 3 " +
                        " and b.book_id not in (select book_id from requested_book)", null);
                if(cursorSecondary.getCount()>0) {
                    while (cursorSecondary.moveToNext()) {
                        listBook_ID.add(cursorSecondary.getInt(0));
                        listTitle.add(cursorSecondary.getString(1));
                        //list_Num_Status.add(cursorSecondary.getInt(2));
                        if(cursorSecondary.getString(2).equals("0")) bookStatus.add("Rent");
                        else if(cursorSecondary.getString(2).equals("1")) bookStatus.add("Share");
                        else if(cursorSecondary.getString(2).equals("2")) bookStatus.add("Give Away");
                    }
                }
                /**Set Adapter into listview*/
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,listTitle);
                resultListView.setAdapter(adapter);
            }

        });

        /**When click Item on listView, will save SharePreference for the next activity*/
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < listBook_ID.size(); i++) {
                    SharedPreferences.Editor editPick_Book = preferFrom_FindBook.edit();
                    editPick_Book.putInt("interest_bookID", listBook_ID.get(position));
                    editPick_Book.apply();
                }
                startActivity(new Intent(FindBookActivity.this, RequestBookActivity.class));
            }
        });
    }
}

