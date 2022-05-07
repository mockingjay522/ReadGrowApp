package com.example.readgrow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.*;

public class ReadingTracker extends AppCompatActivity {
    BookDatabaseHelper bookDatabaseHelper;
    List<String> booksName;
    ListView bookLists;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_tracker);
        bookDatabaseHelper = new BookDatabaseHelper(this);

        GetReadingTraker();
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,booksName);
        ListView bookLists = (ListView) findViewById(R.id.bookArrayList);
        bookLists.setAdapter(adapter);
    }

    /***
     * this method get all the book that get shared, rented, or taken by the usder
     */
    public void GetReadingTraker(){
      //0.instantiate an arraylist to have all the books' name form 3 table(share, gave, rent)
        this.booksName = new  ArrayList<>();
     //-1 get the user id from Share refrences and pass it to the database
        SharedPreferences shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);
        int userID = Integer.parseInt(shareFromLogin.getString("userID",""));
     //-2 get the user's book from

        Cursor rentedBook = bookDatabaseHelper.GetRentBookByReaderId(userID);
        if(rentedBook.getCount()>0)
            while (rentedBook.moveToNext())
                this.booksName.add(BookNameHelper(Integer.parseInt(rentedBook.getString(0))) + "  |  " + "Rented");

     //-3 get the user's book from shared
        //Cursor sharedBook = bookDatabaseHelper.GetShareBookByBookId(userID);
        Cursor sharedBook = bookDatabaseHelper.GetShareBookByReaderId(userID);
        if(sharedBook.getCount()>0)
            while (sharedBook.moveToNext())
                this.booksName.add(BookNameHelper(Integer.parseInt(sharedBook.getString(0)))+ "  |  " + "Shared");

    //-3 get the user's book from GaveAway
        //Cursor gaveAwayBook = bookDatabaseHelper.GetGiveBookByBookId(userID);
        Cursor gaveAwayBook = bookDatabaseHelper.GetGiveBookByReaderId(userID);
        if(gaveAwayBook.getCount()>0)
            while (gaveAwayBook.moveToNext())
              this.booksName.add(BookNameHelper(Integer.parseInt(gaveAwayBook.getString(0)))+ "  |  " + "Taken");

    }

    public String BookNameHelper(int bookID){
        String name="";
        Cursor getName = bookDatabaseHelper.GetBookById(bookID);
        if(getName.getCount()>0)
            while (getName.moveToNext())
                name = getName.getString(2);

        return name;
    }
}