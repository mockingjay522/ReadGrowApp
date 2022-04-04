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
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class SendMessage extends AppCompatActivity {
    Button acceptRequest;
    TextView showMessage;
    EditText typeMessage;

    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences preferFrom_ReplyRequest;

    int receiverID;
    int senderID;
    int requested_Book_ID;
    int bookStatus;
    int addMessage, deleteRequest;
    int updateStatus, deleteBook;
    int book_RentPrice;
    String senderName;
    String contentMessage;
    LocalDate today;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        today = LocalDate.now();

        acceptRequest = findViewById(R.id.btnAccept);
        showMessage = findViewById(R.id.txt_showMessage);
        typeMessage = findViewById(R.id.txt_typeMessage);

        preferFrom_ReplyRequest = PreferenceManager.getDefaultSharedPreferences(this);
        requested_Book_ID = preferFrom_ReplyRequest.getInt("requested_book_ID", 0);
        senderID = preferFrom_ReplyRequest.getInt("requested_Sender_ID", 0);
        receiverID = Integer.parseInt(preferFrom_ReplyRequest.getString("userID", "0"));


        bookDatabaseHelper = new BookDatabaseHelper(this);
        /**Get name and content message*/
        Cursor get_SenderName = bookDatabaseHelper.shareBookDB.rawQuery("Select name, address, postal_code " +
                "from book_reader where reader_id = "
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
        /**Get book status*/
        Cursor bookInfor = bookDatabaseHelper.shareBookDB.rawQuery("Select book_status, book_rent_price " +
                "from book where book_id = "
                + requested_Book_ID, null);
        if(bookInfor.getCount()>0){
            while (bookInfor.moveToNext()){
                bookStatus = bookInfor.getInt(0);
                book_RentPrice = bookInfor.getInt(1);
            }
        }
        /**Show text on chat box*/
        showMessage.setText(senderName + contentMessage);

        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**If user type a message, it will save in DB*/
                if(TextUtils.isEmpty(typeMessage.getText().toString())){

                }else {
                    int addMessage = bookDatabaseHelper.AddReaderMessage(receiverID,senderID,"Reply",
                            typeMessage.getText().toString(),requested_Book_ID);
                    }
                /**Delete request*/
                int deleteRequest = bookDatabaseHelper.DeleteRequestedBookByID(requested_Book_ID);
                /**Update book info into Unvailable and Add record into Rent_book or Share or Give_book*/
                switch (bookStatus){
                    case 0 : updateStatus = bookDatabaseHelper.Update_BookStatus_When_Accept(requested_Book_ID);
                            bookDatabaseHelper.AddRentBook(requested_Book_ID, senderID,book_RentPrice, today.toString());
                        break;
                    case 1 : updateStatus = bookDatabaseHelper.Update_BookStatus_When_Accept(requested_Book_ID);
                            bookDatabaseHelper.AddShareBook(requested_Book_ID,senderID,today.toString());
                        break;
                    case 2 : bookDatabaseHelper.AddGiveBook(requested_Book_ID,senderID, today.toString());
                            updateStatus = bookDatabaseHelper.Update_BookStatus_When_GiveAway(requested_Book_ID, senderID);
                        break;
                }
                if( (deleteRequest >0) && (updateStatus >0)){
                    Toast.makeText(SendMessage.this, "Accept request successful", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(SendMessage.this, ReplyRequest.class));
                }else {
                    Toast.makeText(SendMessage.this, "Cannot Accept", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}