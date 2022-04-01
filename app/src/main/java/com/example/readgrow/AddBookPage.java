package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddBookPage extends AppCompatActivity {

    EditText bName;
    EditText author;
    EditText publication;
    EditText year;
    EditText totalCost;
    EditText costPerWeek;
    Button btnAddBook;
    RadioGroup options;
    RadioButton radioButton;
    double shareCost,rentCost;

    int status;

    BookDatabaseHelper bookDatabaseHelper;

    SharedPreferences  preferencesFromAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_page);

        bookDatabaseHelper = new BookDatabaseHelper(this);
        btnAddBook = findViewById(R.id.btn_Update_Book);

        bName = findViewById(R.id.txtUpdate_Title);
        author = findViewById(R.id.txtUpdate_Author);
        publication = findViewById(R.id.txtUpdate_Publication);
        year = findViewById(R.id.txtUpdate_Year);
        totalCost = findViewById(R.id.txtTotalCost);
        costPerWeek = findViewById(R.id.txtWeekCost);
        options = findViewById(R.id.optionsGroup);

        /*getting Radio Selected Id*/
        RadioButton btnShareBook = findViewById(R.id.rdUpdate_ShareBtn);
        RadioButton btnRentBook = findViewById(R.id.rdUpdate_Rent);
        RadioButton btnGiveAway = findViewById(R.id.rdUpdate_GiveAway);

        TextView testID = findViewById(R.id.txtTestID);

        /**Get userID from Login Activity*/
        SharedPreferences shareFormLogin = PreferenceManager.getDefaultSharedPreferences(this);
        int uid = Integer.parseInt(shareFormLogin.getString("userID", ""));
        preferencesFromAddBook = PreferenceManager.getDefaultSharedPreferences(this);

        //testID.setText(test);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(bName.getText().toString()) || TextUtils.isEmpty(author.getText().toString()) ||
                        TextUtils.isEmpty(publication.getText().toString()) || TextUtils.isEmpty(year.getText().toString())){
                        //(btnShareBook.isSelected()==false) || (btnRentBook.isSelected()==false) || (btnGiveAway.isSelected()==false)){
                    if(TextUtils.isEmpty(bName.getText().toString())) bName.setError("Missing the book title");
                    if(TextUtils.isEmpty(author.getText().toString())) author.setError("Missing the book author");
                    if(TextUtils.isEmpty(publication.getText().toString())) publication.setError("Missing the publication");
                    if(TextUtils.isEmpty(year.getText().toString())) year.setError("Missing the publicated year");
//                    if((btnShareBook.isSelected()==false) || (btnRentBook.isSelected()==false) || (btnGiveAway.isSelected()==false))
//                        Toast.makeText(AddBookPage.this, "Please choose the status for rent or share or give away", Toast.LENGTH_SHORT).show();;
                }else{

                    String bookName = bName.getText().toString();
                    String authorName = author.getText().toString();
                    String _publication = publication.getText().toString();
                    String _year = year.getText().toString();

                    SharedPreferences.Editor editBookID = preferencesFromAddBook.edit();

                    /**Set status for the book*/
                    if(btnShareBook.isChecked()||btnRentBook.isChecked()||btnGiveAway.isChecked()){
                        if(btnShareBook.isChecked()){
                            status = 1;
                            if(TextUtils.isEmpty(totalCost.getText().toString())) {
                                totalCost.setError("Missing the total cost");
                            } else {
                                bookDatabaseHelper.AddBook(uid, bookName, authorName, _publication, _year, status);
                                Toast.makeText(AddBookPage.this, "Add book successful", Toast.LENGTH_SHORT).show();
                                shareCost = Double.parseDouble(totalCost.getText().toString());
                                editBookID.putString("shareCost",Double.toString(shareCost));
                                editBookID.apply();
                                ResetInputs();

                            }

                        }
                        if(btnRentBook.isChecked()){
                            status = 2;
                            if(TextUtils.isEmpty(costPerWeek.getText().toString())) costPerWeek.setError("Missing the total cost");
                            else {
                                bookDatabaseHelper.AddBook(uid, bookName, authorName, _publication, _year, status);
                                Toast.makeText(AddBookPage.this, "Add book successful", Toast.LENGTH_SHORT).show();
                                rentCost = Double.parseDouble(totalCost.getText().toString());
                                editBookID.putString("rentCost",Double.toString(rentCost));
                                editBookID.apply();
                                ResetInputs();
                            }
                        }
                        if(btnGiveAway.isChecked()){
                            status = 3;
                            bookDatabaseHelper.AddBook(uid, bookName, authorName, _publication, _year, status);
                            Toast.makeText(AddBookPage.this, "Add book successful", Toast.LENGTH_SHORT).show();
                            ResetInputs();
                        }

                    }else{
                        Toast.makeText(AddBookPage.this, "Please select a status", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void ResetInputs(){
    startActivity(new Intent(AddBookPage.this,AddBookPage.class));
    }

}