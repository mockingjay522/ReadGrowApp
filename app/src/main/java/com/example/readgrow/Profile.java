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
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    BookDatabaseHelper bookDatabaseHelper;
    BookUser bookUser;
    SharedPreferences updateProfilePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initializing views
        EditText name = findViewById(R.id.profile_name);
        EditText password = findViewById(R.id.profile_password);
        EditText email = findViewById(R.id.profile_email);
        EditText address = findViewById(R.id.profile_address);
        EditText postalCode = findViewById(R.id.profile_postalcode);
        EditText age = findViewById(R.id.profile_age);
        Button btnUpdateProfile = findViewById(R.id.btn_Edit_Profile);

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

        //Setting information loaded from the database
        name.setText(bookUser.getfName());
        password.setText(bookUser.getPassword());
        email.setText(bookUser.getEmail());
        address.setText(bookUser.getAddress());
        age.setText("" + bookUser.getAge());
        String fullAddress = bookUser.getAddress();
        address.setText(fullAddress);
        postalCode.setText(bookUser.getPostalCode());



        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userID = Integer.parseInt(shareFormLogin.getString("userID", ""));
                updateProfilePreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                bookDatabaseHelper = new BookDatabaseHelper(Profile.this);
                bookDatabaseHelper.UpdateBookReader(userID, name.getText().toString(), Integer.parseInt(age.getText().toString()), address.getText().toString(), email.getText().toString(), password.getText().toString());
                Toast.makeText(Profile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this, HomePage.class));
            }
        });

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