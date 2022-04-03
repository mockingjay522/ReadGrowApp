package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessage extends AppCompatActivity {
    Button acceptRequest;
    TextView showMessage;
    EditText typeMessage;

    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences sharedPreferences;

    int receiverID;
    int senderID;
    int requested_Book_ID;
    int bookStatus;
    int addMessage, deleteRequest;
    int updateStatus, deleteBook;
    String senderName;
    String contentMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        acceptRequest = findViewById(R.id.btnAccept);
        showMessage = findViewById(R.id.txt_showMessage);
        typeMessage = findViewById(R.id.txt_typeMessage);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        requested_Book_ID = sharedPreferences.getInt("requested_book_ID", 0);
        senderID = sharedPreferences.getInt("requested_Sender_ID", 0);
        receiverID = Integer.parseInt(sharedPreferences.getString("userID", "0"));

        bookDatabaseHelper = new BookDatabaseHelper(this);

        Cursor get_SenderName = bookDatabaseHelper.shareBookDB.rawQuery("Select name from book_reader where reader_id = "
                + senderID, null);
            if(get_SenderName.getCount()>0){
                while (get_SenderName.moveToNext()) {senderName = get_SenderName.getString(0) + ": ";}
            }

        Cursor cursor = bookDatabaseHelper.GetReaderMessageByRecieverAndBookID(receiverID, requested_Book_ID);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                contentMessage = cursor.getString(3) ;
            }
        }

        Cursor bookInfor = bookDatabaseHelper.shareBookDB.rawQuery("Select book_status from book where book_id = "
                + requested_Book_ID, null);
        if(bookInfor.getCount()>0)
            while (bookInfor.moveToNext())
                bookStatus = bookInfor.getInt(0);

        showMessage.setText(senderName + contentMessage);

        acceptRequest.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(typeMessage.getText().toString())){

                }else {
                    int addMessage = bookDatabaseHelper.AddReaderMessage(receiverID,senderID,"Reply",
                            typeMessage.getText().toString(),requested_Book_ID);

                    }
                int deleteRequest = bookDatabaseHelper.DeleteRequestedBookByID(requested_Book_ID);
                switch (bookStatus){
                    case 0 : updateStatus = bookDatabaseHelper.Update_BookStatus_When_Accept(requested_Book_ID);
                        break;
                    case 1 : updateStatus = bookDatabaseHelper.Update_BookStatus_When_Accept(requested_Book_ID);
                    case 2 : deleteBook = bookDatabaseHelper.DeleteBookByID(requested_Book_ID);
                }
                if( (deleteRequest >0) && (deleteBook >0 || updateStatus >0)){
                    Toast.makeText(SendMessage.this, "Accept request successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SendMessage.this, "Cannot Accept", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}