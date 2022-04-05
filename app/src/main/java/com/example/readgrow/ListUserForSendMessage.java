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

import java.util.ArrayList;

public class ListUserForSendMessage extends AppCompatActivity implements RecycleViewAdapter.ItemClickListener{
    BookDatabaseHelper databaseHelper;
    ArrayList<Integer> list_UserID;
    ArrayList<String> list_UserEmail;
    SharedPreferences preferForm_Admin_ListUser;
    RecyclerView recycler_List_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_for_send_message);

        list_UserEmail = new ArrayList<>();
        list_UserID = new ArrayList<>();

        recycler_List_User = findViewById(R.id.recyleview_ListUser_ToSendMessage);

        databaseHelper = new BookDatabaseHelper(this);
        preferForm_Admin_ListUser = PreferenceManager.getDefaultSharedPreferences(this);

        Cursor cursor = databaseHelper.GetAvailableBookReader();
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                list_UserID.add(cursor.getInt(0));
                list_UserEmail.add(cursor.getString(5));
            }
        }
        RecycleViewAdapter adapter = new RecycleViewAdapter(getBaseContext(), list_UserID, list_UserEmail);
        recycler_List_User.setAdapter(adapter);
        recycler_List_User.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        SharedPreferences.Editor editorUserID = preferForm_Admin_ListUser.edit();
        editorUserID.putInt("chosenID_ToSend_Message", list_UserID.get(position));
        editorUserID.apply();
        startActivity(new Intent(ListUserForSendMessage.this, AdminSendMessage.class));
    }
}