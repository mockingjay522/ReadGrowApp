package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RequestBookActivity extends AppCompatActivity {
    TextView bookName;
    TextView author;
    TextView publication;
    TextView public_year;
    TextView cost;
    TextView bookOwner_name;
    TextView ownerEmail;
    TextView ownerAddress;
    EditText requestMessage;
    Button btnRequest;

    TextView test;
    TextView testbookID;

    int bookStatus;
    int ID_bookOwner;
    int loginID;
    int picked_BookID;

    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences preferFrom_FindBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_book);

        bookName = findViewById(R.id.requestBook_bookname);
        author = findViewById(R.id.requestBook_authorname);
        publication = findViewById(R.id.requestBook_publication);
        public_year = findViewById(R.id.requestBook_year);
        cost = findViewById(R.id.requestBook_totalCost);
        bookOwner_name = findViewById(R.id.requestBook_bookOwnername);
        ownerEmail = findViewById(R.id.requestBook_email);
        ownerAddress = findViewById(R.id.requestBook_location);

        requestMessage = findViewById(R.id.requestBook_message);
        btnRequest = findViewById(R.id.requestBook_Button);

        bookDatabaseHelper = new BookDatabaseHelper(this);

        preferFrom_FindBook = PreferenceManager.getDefaultSharedPreferences(this);
        picked_BookID = preferFrom_FindBook.getInt("interest_bookID", 0);
        loginID = Integer.parseInt(preferFrom_FindBook.getString("userID", "0"));

        test = findViewById(R.id.textView4);
        testbookID = findViewById(R.id.textView5);

        testbookID.setText(picked_BookID + " ");


        Cursor cursor = bookDatabaseHelper.GetBookById(picked_BookID);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                ID_bookOwner = cursor.getInt(1);
                bookName.setText(cursor.getString(2));
                author.setText(cursor.getString(3));
                publication.setText(cursor.getString(4));
                public_year.setText(cursor.getString(5));
                bookStatus = cursor.getInt(6);
                switch (bookStatus){
                    case 0: cost.setText("The total cost is: $"+cursor.getInt(7));
                    break;
                    case 1: cost.setText("The total cost is: $0");
                    break;
                    case 2: cost.setText("The total cost is: $0");
                    break;
                }
            }
        }
        test.setText(ID_bookOwner + " ");
        Cursor cursorOwner = bookDatabaseHelper.GetBookReaderById(ID_bookOwner);
        if(cursorOwner.getCount()>0){
            while (cursorOwner.moveToNext()){
                bookOwner_name.setText(cursorOwner.getString(1)); //user name
                ownerAddress.setText(cursorOwner.getString(3)); //user address
                ownerEmail.setText(cursorOwner.getString(5)); //user email
            }
        }

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(requestMessage.getText().toString())){
                    requestMessage.setError("Please enter a message before requesting");
                }else{
                    bookDatabaseHelper.AddRequested_Book(picked_BookID, loginID, ID_bookOwner);
                    bookDatabaseHelper.AddReaderMessage(loginID,ID_bookOwner,requestMessage.getText().toString(),
                            "Request", picked_BookID);
                    Toast.makeText(RequestBookActivity.this, "Request successful", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(RequestBookActivity.this, FindBookActivity.class));
                }

            }
        });
    }

}