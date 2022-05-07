package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;

    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences shareFromLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookDatabaseHelper = new BookDatabaseHelper(this);

        TextView signUp = findViewById(R.id.login_signUp);
        txtEmail = findViewById(R.id.login_username);
        txtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);


        btnLogin.setOnClickListener(this::ValidateUser);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity1.class));
            }
        });

    }

    public void ValidateUser(View view) {


        String userID;
        String userName;

        String email = txtEmail.getText().toString();
        String passWord = txtPassword.getText().toString();

        /**Login by Admin*/
        if(email.equals("admin")&&passWord.equals("admin")){
            startActivity(new Intent(MainActivity.this, AdminHomePage.class));
        }

        Cursor cursor = bookDatabaseHelper.GetBookReaderByPassAndEmail(passWord, email);

        if (cursor.getCount() > 0)
            while (cursor.moveToNext()){
                //-1 get the user main info
                userID = cursor.getString(0); // index 0 is user id
                userName = cursor.getString(1); // index 0 is user id
                //-2 show welcome toast
                Toast.makeText(this, "You are welcome here  " + cursor.getString(1), Toast.LENGTH_LONG).show();
                //-3 set shareFromLogin
                shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = shareFromLogin.edit();
                editor.putString("userID",userID);
                editor.putString("userName",userName);
                editor.commit();
                // move to the HomePage
                startActivity(new Intent(MainActivity.this,HomePage.class));
            }
        else
            Toast.makeText(this, "the user name or password is not valid", Toast.LENGTH_SHORT).show();
    }
}