package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateBook0 extends AppCompatActivity {
    ArrayList <String > listBooks = new ArrayList<>();
    ArrayList <String> list_Num_Status = new ArrayList<>();
    ArrayList <String> bookStatus = new ArrayList<>();
    ArrayList <String> listBook_ID = new ArrayList<>();

    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences shareFormLogin;
    SharedPreferences preferencesFromUpdateBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book0);

        shareFormLogin = PreferenceManager.getDefaultSharedPreferences(this);
        int userID = Integer.parseInt(shareFormLogin.getString("userID", "0")) ;

        /**get data from book table*/
        bookDatabaseHelper = new BookDatabaseHelper(this);
        Cursor cursor = bookDatabaseHelper.GetBooksByReaderId(userID);
        StringBuilder str = new StringBuilder();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                listBook_ID.add(cursor.getString(0));//column 0 is bookID
                listBooks.add(cursor.getString(2)); //column 2 is book Title
                list_Num_Status.add(cursor.getString(6));} //column 6 is book status
        }else{
            Toast.makeText(UpdateBook0.this, "You have not added any books yet", Toast.LENGTH_SHORT).show();
        }
        /**Convert the status number as a String*/
        for(int i =0; i<list_Num_Status.size(); i++){
            if(list_Num_Status.get(i).equals("0")) bookStatus.add("Rent");
            else if(list_Num_Status.get(i).equals("1")) bookStatus.add("Share");
            else if(list_Num_Status.get(i).equals("2")) bookStatus.add("Give Away");
            else bookStatus.add("Unavailable");
        }
        /**Set HashMap listView for the books found by Reader ID*/
        ListView myListBook = findViewById(R.id.MyBooks_listview);
        List<HashMap<String, String>> list_Added_Book = new ArrayList<>();
        for(int i = 0; i< listBooks.size(); i++){
            HashMap <String, String> hashMap= new HashMap<>();
            hashMap.put("bookTitle", listBooks.get(i));
            hashMap.put("bookStatus", bookStatus.get(i));
            list_Added_Book.add(hashMap);
        }
        /**Set listView of the books found by Reader ID*/
        String [] from = {"bookTitle", "bookStatus"};
        int [] to = {R.id.bookTitle, R.id.bookStatus};
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), list_Added_Book, R.layout.listview_layout, from, to);
        myListBook.setAdapter(adapter);

        preferencesFromUpdateBook = PreferenceManager.getDefaultSharedPreferences(this);

        myListBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for(int i = 0; i< listBook_ID.size();i++){
                    SharedPreferences.Editor saveBookID = preferencesFromUpdateBook.edit();
                    saveBookID.putString("pickBook_ID", listBook_ID.get(position));
                    saveBookID.apply();
                }
                startActivity(new Intent(UpdateBook0.this, UpdateBook.class));
            }
        });
    }


}