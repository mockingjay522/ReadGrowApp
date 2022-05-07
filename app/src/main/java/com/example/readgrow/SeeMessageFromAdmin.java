package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class SeeMessageFromAdmin extends AppCompatActivity {
    TextView showMessage;
    SharedPreferences preferFrom_Login;
    BookDatabaseHelper databaseHelper;
    int loginID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_message_from_admin);
        showMessage = findViewById(R.id.txt_Message_FromAdmin);

        preferFrom_Login = PreferenceManager.getDefaultSharedPreferences(this);
        loginID = Integer.parseInt(preferFrom_Login.getString("userID","0"));

        databaseHelper = new BookDatabaseHelper(this);
        Cursor cursor = databaseHelper.GetAdminMessageByReaderId(loginID);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                showMessage.setText("Admin: " + cursor.getString(4));
            }
        }
    }
}