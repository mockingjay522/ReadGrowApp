package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddBookPage extends AppCompatActivity {

    EditText bName;
    EditText author;
    EditText publication;
    EditText year;
    EditText rentPrice;
    EditText linkBook;
    Button btnAddBook;
    Spinner spinner;

    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences  preferencesFromAddBook;
    int status=-1; // the status here is : 0 is share, 1 is rent and 2 is gave away

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_page);

        instatntiateContrllers();
        btnAddBook.setOnClickListener(this::SubmitNewBook);
    }

    private void instatntiateContrllers() {
        bookDatabaseHelper = new BookDatabaseHelper(this);

        bName = findViewById(R.id.txtUpdate_Title);
        author = findViewById(R.id.txtUpdate_Author);
        publication = findViewById(R.id.txtUpdate_Publication);
        year = findViewById(R.id.txtUpdate_Year);
        rentPrice = findViewById(R.id.txtRentPrice);
        linkBook = findViewById(R.id.linkBook);
        btnAddBook = findViewById(R.id.btnAddNewBook);
        spinner = findViewById(R.id.bookOptionsSpinner);

    }

    private void SubmitNewBook(View view) {
        //0- check if there is any error in the inputs
        if (!ValidateInputs())
            return;

        //1- get the value of user id
        SharedPreferences shareFormLogin = PreferenceManager.getDefaultSharedPreferences(this);
        int userID = Integer.parseInt(shareFormLogin.getString("userID", ""));
        preferencesFromAddBook = PreferenceManager.getDefaultSharedPreferences(this);

        //2- get the value of all input
        String booktitle = bName.getText().toString();
        String authorName = author.getText().toString();
        String publicationer = publication.getText().toString();
        String yearofbook = year.getText().toString();
        int rentPriceVlaue = Integer.parseInt(rentPrice.getText().toString());
        String linkBookvlaue = linkBook.getText().toString();

        bookDatabaseHelper.AddBook(userID,booktitle,authorName,publicationer,yearofbook,status,rentPriceVlaue,linkBookvlaue);

        Toast.makeText(this, "the book is added", Toast.LENGTH_SHORT).show();
        ClearInputs();

    }

    private boolean ValidateInputs(){
        if(TextUtils.isEmpty(bName.getText().toString())) {
            bName.setError("Missing the book title");
        }
        if(TextUtils.isEmpty(publication.getText().toString())){
            publication.setError("Missing the book publication");
        }
        if(TextUtils.isEmpty(author.getText().toString())){
            author.setError("Missing the book author");
        }
        if(TextUtils.isEmpty(rentPrice.getText().toString())) {
            rentPrice.setError("Missing the book rent Price");
            return false;
        }
        if(TextUtils.isEmpty(year.getText().toString())){
            year.setError("Missing the book year");
        }
        // set the optopn
        if (spinner.getSelectedItemPosition()==1)
            status= -1;
        if (spinner.getSelectedItemPosition()==1)
                status= 0;
        if (spinner.getSelectedItemPosition()==2)
                status= 1;
        if (spinner.getSelectedItemPosition()==3)
                 status= 2;
        if (spinner.getSelectedItemPosition()==4)
              status= 3;
        // check the status
        if (status==-1){
            Toast.makeText(this, "Please specify the book status: Rent,Share,Give away", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private void ClearInputs(){
         bName.setText("");
         author.setText("");
         publication.setText("");
         year.setText("");
         rentPrice.setText("");
         linkBook.setText("");
         //--
        bName.requestFocus();
    }
}