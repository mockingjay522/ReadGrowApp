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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateBook extends AppCompatActivity {

    SharedPreferences preferencesFromUpdateBook;
    BookDatabaseHelper bookDatabaseHelper;
    BookInfo picked_Book;
    int status;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        EditText bookTitle = findViewById(R.id.txtUpdate_Title);
        EditText author = findViewById(R.id.txtUpdate_Author);
        EditText publication = findViewById(R.id.txtUpdate_Publication);
        EditText year = findViewById(R.id.txtUpdate_Year);
        RadioButton shareRdBtn = findViewById(R.id.rd_ShareBtn);
        RadioButton rentRdBtn = findViewById(R.id.rd_Rent);
        RadioButton give_AwayRdBtn = findViewById(R.id.rdUpdate_GiveAway);
        Spinner avail_Or_Not = findViewById(R.id.avail_or_Not);

        Button updateBook = findViewById(R.id.btnAddNewBook);



        preferencesFromUpdateBook = PreferenceManager.getDefaultSharedPreferences(this);

        int pickBook_ID = Integer.parseInt(preferencesFromUpdateBook.getString("pickBook_ID","0"));

        bookDatabaseHelper = new BookDatabaseHelper(this);
        Cursor cursor = bookDatabaseHelper.GetBookById(pickBook_ID);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                userID = Integer.parseInt(cursor.getString(1));
                picked_Book = new BookInfo(cursor.getString(2),cursor.getString(3),cursor.getString(4),
                        cursor.getString(5), Integer.parseInt(cursor.getString(6)));
            }

        }else {
            Toast.makeText(UpdateBook.this, "You have not added any books yet", Toast.LENGTH_SHORT).show();
        }

        author.setText(picked_Book.getAuthor());
        bookTitle.setText(picked_Book.getBookName());
        publication.setText(picked_Book.getPublication());
        year.setText(picked_Book.getYear());

        switch (picked_Book.getStatus()){
            case 1: shareRdBtn.setChecked(true);
                break;
            case 2: rentRdBtn.setChecked(true);
                break;
            case 3: give_AwayRdBtn.setChecked(true);
                break;
            default: give_AwayRdBtn.setChecked(true);
        }
        updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(bookTitle.getText().toString()) || TextUtils.isEmpty(author.getText().toString()) ||
                        TextUtils.isEmpty(publication.getText().toString()) || TextUtils.isEmpty(year.getText().toString())){
                    //(btnShareBook.isSelected()==false) || (btnRentBook.isSelected()==false) || (btnGiveAway.isSelected()==false)){
                    if(TextUtils.isEmpty(bookTitle.getText().toString())) bookTitle.setError("Missing the book title");
                    if(TextUtils.isEmpty(author.getText().toString())) author.setError("Missing the book author");
                    if(TextUtils.isEmpty(publication.getText().toString())) publication.setError("Missing the publication");
                    if(TextUtils.isEmpty(year.getText().toString())) year.setError("Missing the publicated year");
//                    if((btnShareBook.isSelected()==false) || (btnRentBook.isSelected()==false) || (btnGiveAway.isSelected()==false))
//                        Toast.makeText(AddBookPage.this, "Please choose the status for rent or share or give away", Toast.LENGTH_SHORT).show();;
                }else {

                    if(avail_Or_Not.getSelectedItem().toString().equals("Unavailable")){
                        status = 0;
                        updateBookInfo(pickBook_ID, userID, bookTitle.getText().toString(),author.getText().toString(),
                                publication.getText().toString(), year.getText().toString(), status);
//                        bookDatabaseHelper.UpdateBook(pickBook_ID, userID, bookTitle.getText().toString(),
//                                author.getText().toString(),
//                                publication.getText().toString(), year.getText().toString(), status);
//                        Toast.makeText(UpdateBook.this, "Update book sucessful", Toast.LENGTH_SHORT).show();
                    }else{
                        if (shareRdBtn.isChecked() || rentRdBtn.isChecked() || give_AwayRdBtn.isChecked()) {

                            if (shareRdBtn.isChecked()) {
                                status = 1;
                                updateBookInfo(pickBook_ID, userID, bookTitle.getText().toString(),author.getText().toString(),
                                        publication.getText().toString(), year.getText().toString(), status);
//                                bookDatabaseHelper.UpdateBook(pickBook_ID, userID, bookTitle.getText().toString(),
//                                        author.getText().toString(),
//                                        publication.getText().toString(), year.getText().toString(), status);
//                                Toast.makeText(UpdateBook.this, "Update book sucessful", Toast.LENGTH_SHORT).show();

                            }
                            if (rentRdBtn.isChecked()) {
                                status = 2;
                                updateBookInfo(pickBook_ID, userID, bookTitle.getText().toString(),author.getText().toString(),
                                        publication.getText().toString(), year.getText().toString(), status);
//                                bookDatabaseHelper.UpdateBook(pickBook_ID, userID, bookTitle.getText().toString(),
//                                        author.getText().toString(),
//                                        publication.getText().toString(), year.getText().toString(), status);
//                                Toast.makeText(UpdateBook.this, "Update book successful", Toast.LENGTH_SHORT).show();
                            }
                            if (give_AwayRdBtn.isChecked()) {
                                status = 3;
                                updateBookInfo(pickBook_ID, userID, bookTitle.getText().toString(),author.getText().toString(),
                                        publication.getText().toString(), year.getText().toString(), status);
//                                bookDatabaseHelper.UpdateBook(pickBook_ID, userID, bookTitle.getText().toString(),
//                                        author.getText().toString(),
//                                        publication.getText().toString(), year.getText().toString(), status);
//                                Toast.makeText(UpdateBook.this, "Update book sucessful", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(UpdateBook.this, "Please select a status", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });

    }
    public void updateBookInfo(int bookID, int userID, String bTitle, String author, String publication, String year, int status){
        bookDatabaseHelper = new BookDatabaseHelper(this);
        bookDatabaseHelper.UpdateBook(bookID,userID,bTitle,author,publication,year,status);
        Toast.makeText(this, "Update book successful", Toast.LENGTH_SHORT).show();
    }
}