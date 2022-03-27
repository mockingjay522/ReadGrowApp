package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    //BookDatabaseHelper bookDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_page);

       // bookDatabaseHelper = new BookDatabaseHelper(this);
        btnAddBook = findViewById(R.id.btnAdd);

        bName = findViewById(R.id.txtBookName);
        author = findViewById(R.id.txtAuthor);
        publication = findViewById(R.id.txtPublication);
        year = findViewById(R.id.txtDate);
        totalCost = findViewById(R.id.txtTotalCost);
        costPerWeek = findViewById(R.id.txtWeekCost);
        options = findViewById(R.id.optionsGroup);

        TextView testID = findViewById(R.id.txtTestID);

        /**Get userID from Login Activity*/
        SharedPreferences shareFormLogin = PreferenceManager.getDefaultSharedPreferences(this);
        shareFormLogin.getString("userID", "none");
        String test = shareFormLogin.getString("userID", "none");

        testID.setText(test);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                int uid = 19191;
//                String bookName = bName.getText().toString();
//                String authorName = author.getText().toString();
//                String _publication = publication.getText().toString();
//                String _year = year.getText().toString();
//                double tCost = Double.parseDouble(totalCost.getText().toString());
//                double weekCost = Double.parseDouble(costPerWeek.getText().toString());
//
//                /*getting Radio Selected Id*/
//                int selectedId = options.getCheckedRadioButtonId();
//                radioButton = findViewById(selectedId);

//                bookDatabaseHelper.AddBook(uid, bookName, _publication, _year, )
            }
        });
    }

}