package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class ReplyRequest extends AppCompatActivity{
    BookDatabaseHelper databaseHelper;
    ArrayList <String > listBook_Title;
    ArrayList <Integer> listSender_ID ;
    ArrayList<Integer> listBook_ID ;
    ArrayList<Integer> listBook_Status ;
    ArrayList<Integer> listBook_Price ;
    ListView listRequestedBook;

    SharedPreferences preferFrom_ReplyRequest;

    int loginID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_request);

        listBook_Title = new ArrayList<>();
        listSender_ID = new ArrayList<>();
        listBook_ID = new ArrayList<>();

        listRequestedBook = findViewById(R.id.list_Requested_Book);

        databaseHelper = new BookDatabaseHelper(this);

        preferFrom_ReplyRequest = PreferenceManager.getDefaultSharedPreferences(this);
        loginID = Integer.parseInt(preferFrom_ReplyRequest.getString("userID", "0"));

        Cursor cursor = databaseHelper.GetRequestedBook(loginID);
        /**0 is book_id, 1 is title, 2 is sender_id*/
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                listBook_ID.add(cursor.getInt(0));
                listBook_Title.add(cursor.getString(1));
                listSender_ID.add(cursor.getInt(2));
            }
        }
        //SimpleAdapter adapter = new SimpleAdapter(this)
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listBook_Title);

        listRequestedBook.setAdapter(adapter);
        /**Click on the list will put the SharedPreference to get specific book
         *  So that user can answer the request in the next Activity*/
        listRequestedBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                for(int i =0; i<listBook_ID.size(); i++){
                    SharedPreferences.Editor editSender = preferFrom_ReplyRequest.edit();
                    editSender.putInt("requested_book_ID", listBook_ID.get(postion));
                    editSender.putInt("requested_Sender_ID", listSender_ID.get(postion));
                    editSender.apply();
                }
                startActivity(new Intent(ReplyRequest.this, SendMessage.class));
            }
        });

    }

}