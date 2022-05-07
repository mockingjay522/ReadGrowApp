package com.example.readgrow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class AdminSendMessage extends AppCompatActivity {
    EditText txtTitleMessage;
    EditText txtContentMessage;
    Button btnSendMessage;

    BookDatabaseHelper databaseHelper;
    SharedPreferences preferFrom_ListUser_ToSendMessage;

    int chosenUserID;
    String chosenUserName;
    String comingMessage;

    LocalDate today;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send_message);

        txtTitleMessage = findViewById(R.id.txt_Message_FromAdmin);
        txtContentMessage = findViewById(R.id.txt_Content_Message);
        btnSendMessage = findViewById(R.id.btn_Admin_Click_SendMessage);

        today = LocalDate.now();

        preferFrom_ListUser_ToSendMessage = PreferenceManager.getDefaultSharedPreferences(this);
        chosenUserID = preferFrom_ListUser_ToSendMessage.getInt("chosenID_ToSend_Message",0);

        databaseHelper = new BookDatabaseHelper(this);
        Cursor cursor_GetUserName = databaseHelper.shareBookDB.rawQuery("Select name " +
                "from book_reader where reader_id = "
                + chosenUserID, null);
        if(cursor_GetUserName.getCount()>0){
            while (cursor_GetUserName.moveToNext()){
                chosenUserName = cursor_GetUserName.getString(0);
            }
        }
        Cursor cursor_CheckAdmin = databaseHelper.GetAllAdmins();
        if(cursor_CheckAdmin.getCount()>0){

        } else databaseHelper.AddAdmin("Admin");
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(txtContentMessage.getText().toString())||
                        TextUtils.isEmpty(txtTitleMessage.getText().toString())) {
                    if(TextUtils.isEmpty(txtContentMessage.getText().toString()))
                    txtContentMessage.setError("Please enter the content of your message");
                    if(TextUtils.isEmpty(txtTitleMessage.getText().toString()))
                        txtTitleMessage.setError("Please enter title of the message here");
                }else{
                    databaseHelper.AddAdminMessage(1, chosenUserID, txtTitleMessage.getText().toString(),
                            txtContentMessage.getText().toString(), today.toString());
                    Toast.makeText(AdminSendMessage.this, "Send message sucessful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminSendMessage.this, ListUserForSendMessage.class));
                }
            }
        });
    }
}