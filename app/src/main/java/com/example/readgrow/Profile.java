package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class Profile extends AppCompatActivity {
    BookDatabaseHelper bookDatabaseHelper;
    BookUser bookUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initializing views
        EditText name = findViewById(R.id.profile_name);
        EditText username = findViewById(R.id.profile_username);
        EditText password = findViewById(R.id.profile_password);
        EditText email = findViewById(R.id.profile_email);
        Spinner country = findViewById(R.id.profile_country);
        EditText address = findViewById(R.id.profile_address);
        EditText postalCode = findViewById(R.id.profile_postalcode);
        Spinner province = findViewById(R.id.profile_province);
        Button btnUpdateProfile = findViewById(R.id.profile_updateBtn);

        //Getting shared preferences to load the information
        SharedPreferences shareFormLogin = PreferenceManager.getDefaultSharedPreferences(this);
        int userID = Integer.parseInt(shareFormLogin.getString("userID", ""));

        //Initializing icons
        ImageView viewMessage = findViewById(R.id.viewMessage2);
        ImageView goHome = findViewById(R.id.back_home2);

        //Getting profile information from db
        bookDatabaseHelper = new BookDatabaseHelper(this);
        Cursor cursor = bookDatabaseHelper.GetBookReaderById(userID);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                bookUser = new BookUser(cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            }
        }

        name.setText(bookUser.getfName());
        username.setText(bookUser.getUsername());
        password.setText(bookUser.getPassword());
        email.setText(bookUser.getEmail());
//        country.setSe
        address.setText(bookUser.getAddress());
        postalCode.setText(bookUser.getPostalCode());



        //Creating icons listeners
        viewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, ReplyRequest.class));
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, HomePage.class));
            }
        });
    }
}