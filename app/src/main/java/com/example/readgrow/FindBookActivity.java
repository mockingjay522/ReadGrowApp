package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    SharedPreferences preferFrom_FindBook;
    SharedPreferences shareFromLogin;
    String location;
    int ID_login;
    List<BookInfo> books = new  ArrayList<>();
    ArrayList <String> listTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);

        instantiateControllers();

        searchBtn.setOnClickListener(this::SortOption);

    }

    private void SortOption(View view) {
//        int sortOption = spinner.getSelectedItemPosition();
        databaseHelper = new BookDatabaseHelper(this);
        spinner = findViewById(R.id.searchBookSpinner);
        sortBtn = findViewById(R.id.btnSortOption);
        resultListView = findViewById(R.id.availBookListView);
        txtLocation = findViewById(R.id.txtPostalCode);
        searchBtn = findViewById(R.id.btnSearch);
        preferFrom_FindBook = PreferenceManager.getDefaultSharedPreferences(this);
        shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);
        /*
         * Get data from input and the ID from login => to implement the GetBookByPostalCode
         */
        if(TextUtils.isEmpty(txtLocation.getText().toString())){
            txtLocation.setError("Please enter a postal code");
        }else {
            location = txtLocation.getText().toString().trim();
        }
        ID_login = Integer.parseInt(shareFromLogin.getString("userID", "0"));

        //Cursor getBooks = GetData(location, ID_login);
        Cursor getBooks =  databaseHelper.GetBookByPostalCode("b2c", 1 );
        /**Extract data to put into ListView Adapter*/
        if(getBooks.getCount()>0)
            while(getBooks.moveToNext())
                books.add(new BookInfo(getBooks.getInt(0),
                        getBooks.getString(1)
                        ,getBooks.getString(2)
                        ,getBooks.getString(3)
                        ,getBooks.getString(4)
                        ,getBooks.getInt(5)
                        ,getBooks.getString(6)));

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,books);

        resultListView.setAdapter(adapter);

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for(int i =0; i<books.size();i++){
                    SharedPreferences.Editor editPick_Book = preferFrom_FindBook.edit();
                    editPick_Book.putInt("interest_bookID", books.get(position).getBookID());
                    editPick_Book.apply();
                }
                startActivity(new Intent(FindBookActivity.this, RequestBookActivity.class));
            }
        });

    }

    public void instantiateControllers(){
        databaseHelper = new BookDatabaseHelper(this);
        spinner = findViewById(R.id.searchBookSpinner);
        sortBtn = findViewById(R.id.btnSortOption);
        resultListView = findViewById(R.id.availBookListView);
        txtLocation = findViewById(R.id.txtPostalCode);
        searchBtn = findViewById(R.id.btnSearch);
        preferFrom_FindBook = PreferenceManager.getDefaultSharedPreferences(this);
        shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);
    }
    private Cursor GetData(String postcode, int ID){
        Cursor cursorPriority = databaseHelper.GetBookByPostalCode(postcode, ID );
        Cursor cursorSecondary = databaseHelper.shareBookDB.rawQuery(
                "select b.book_id, b.title, b.author, b.publisher, b.publish_date, b.book_status, b.reader_id \n" +
                "from book b join book_reader u on b.reader_id = u.reader_id " +
                "where u.postal_code like " + "\""+  postcode + "\"" + " and b.reader_id != "+ ID, null);
        if(cursorPriority.getCount()>0){
            return cursorPriority;
        }else return cursorSecondary;

    }

}