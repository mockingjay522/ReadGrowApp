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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String email;
    String passWord;
    BookDatabaseHelper bookDatabaseHelper;
    SharedPreferences shareFromLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<BookUser> listBookUser = new ArrayList<>();
        bookDatabaseHelper = new BookDatabaseHelper(this);

        TextView signUp = findViewById(R.id.login_signUp);
        EditText txtEmail = findViewById(R.id.login_username);
        EditText txtPassword = findViewById(R.id.login_password);
        Button btnLogin = findViewById(R.id.login_button);

        shareFromLogin = PreferenceManager.getDefaultSharedPreferences(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**SharedPreference to save UserID for Add book activity*/

                if(TextUtils.isEmpty(txtEmail.getText().toString()) || TextUtils.isEmpty(txtPassword.getText().toString())) {
                    if (TextUtils.isEmpty(txtEmail.getText().toString())) {
                        txtEmail.setError("Your Email is missing");
                    }
                    if (TextUtils.isEmpty(txtPassword.getText().toString())) {
                        txtPassword.setError("Your Password is missing");
                    }
                }else {
                    email = txtEmail.getText().toString();
                    passWord = txtPassword.getText().toString();
                    StringBuilder str = new StringBuilder();
                    Cursor cursor = bookDatabaseHelper.GetAllBookReader();

                    /**if statement for check the account exiting or not*/
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
//                        str.append(" userID " + cursor.getString(0)); //userID
//                        str.append(" name " + cursor.getString(1)); //name
//                        str.append(" age " + cursor.getString(2)); //age
//                        str.append(" address " + cursor.getString(3)); //address
//                        str.append(" email " + cursor.getString(4)); //email
//                        str.append(" password " + cursor.getString(5)); //password
                            /**store the data into ListArray */
                            listBookUser.add(new BookUser(cursor.getString(0),cursor.getString(1), cursor.getString(4),
                                    cursor.getString(5), Integer.parseInt(cursor.getString(2)), cursor.getString(3)));
                        }

                    }

                    if (listBookUser.isEmpty()) {

                        Toast.makeText(MainActivity.this, "Sorry! The Account is not exit, " +
                                "                                   Please Sign up to use", Toast.LENGTH_SHORT).show();
                    } else {
                        /**Check if email is exiting*/
                        int exitingEmail = 0;
                        int exitingPassword = 0;
                        for (int i = 0; i < listBookUser.size(); i++) {
                            if (listBookUser.get(i).getEmail().equalsIgnoreCase(email)) {
                                exitingEmail = 1;
                                if(listBookUser.get(i).getPassword().equals(passWord)){
                                    exitingPassword = 1;

                                    /**Create SharePreference to get userIN for Add book activity*/

                                    SharedPreferences.Editor editID = shareFromLogin.edit();
                                    editID.putString("userID", listBookUser.get(i).getUserID());
                                    editID.apply();
                                }
                            }
                        }


                        if (exitingEmail != 0 && exitingPassword != 0) {

                            startActivity(new Intent(MainActivity.this, HomePage.class));
                        } else {

                            Toast.makeText(MainActivity.this, "Wrong Email or Password! Please enter agian",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity1.class));
            }
        });
    }
}