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

public class SignUpActivity1 extends AppCompatActivity {
    String fName;
    String passWord;
    String email;
    SharedPreferences sharedPrefer;
    BookDatabaseHelper bookDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        /**Arraylist to store Object com.example.readgrow.BookUser*/
        ArrayList <BookUser> listBookUser = new ArrayList<>();

       bookDataBaseHelper = new BookDatabaseHelper(this);

        EditText fullName = findViewById(R.id.signUp1_name);
        //EditText userNameTxt = findViewById(R.id.signUp1_username);
        EditText passWordTxt = findViewById(R.id.signUp1_password);
        EditText emailTxt = findViewById(R.id.signUp1_email);
        Button btnSignUpNext = findViewById(R.id.signUp1_button);

        TextView showTest = findViewById(R.id.showTest);

        /** Initialize SharedPreferences to store data for SignUpActivity02*/
        sharedPrefer = PreferenceManager.getDefaultSharedPreferences(this);


        btnSignUpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                StringBuilder str = new StringBuilder();



                /**
                 * Check if user miss required information
                 * */

                if(TextUtils.isEmpty(fullName.getText().toString()) || TextUtils.isEmpty(passWordTxt.getText().toString())
                || TextUtils.isEmpty(emailTxt.getText().toString())){
                    if(TextUtils.isEmpty(fullName.getText().toString())){
                        fullName.setError("Your full name is missing");
                    }
                    if(TextUtils.isEmpty(passWordTxt.getText().toString())){
                        passWordTxt.setError("Your password is missing");
                    }
                    if(TextUtils.isEmpty(emailTxt.getText().toString())){
                        emailTxt.setError("Your email is missing");
                    }
                }else{
                    fName = fullName.getText().toString();
                    passWord = passWordTxt.getText().toString();
                    email = emailTxt.getText().toString();

                    Cursor cursor = bookDataBaseHelper.CheckExitingEmail(email);
                    /**if statement for check the account exiting or not*/
                    if(cursor.getCount()>0){
                        Toast.makeText(SignUpActivity1.this, "The email is exiting, please enter a new one", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        /**Create ShareRefrence Object*/
                        SharedPreferences.Editor editor = sharedPrefer.edit();
                        editor.putString("signUp01_fName", fName);
                        editor.putString("signUp01_email", email);
                        editor.putString("signUp01_passWord", passWord);
                        editor.apply();
                        startActivity(new Intent(SignUpActivity1.this, SignUpActivity2.class));
                    }
//                    if(listBookUser.isEmpty()){
//
//                        /**Create ShareRefrence Object*/
//                        SharedPreferences.Editor editor = sharedPrefer.edit();
//                        editor.putString("signUp01_fName", fName);
//                        editor.putString("signUp01_email", email);
//                        editor.putString("signUp01_passWord", passWord);
//                        editor.apply();
//                        startActivity(new Intent(SignUpActivity1.this, SignUpActivity2.class));
//                    }else{
//                        /**Check if email is exiting*/
//                        int exitingEmail = 0;
//                        for(int i = 0; i<listBookUser.size();i++){
//                            if(listBookUser.get(i).getEmail().equalsIgnoreCase(email)){
//                                exitingEmail = 1;}
//                        }
//                        if(exitingEmail !=0){
//                            Toast.makeText(SignUpActivity1.this, "Email is exiting, please enter a new one!", Toast.LENGTH_SHORT).show();
//                        }else{
//                            /**Create ShareRefrence Object*/
//
//                            SharedPreferences.Editor editor = sharedPrefer.edit();
//                            editor.putString("signUp01_fName", fName);
//                            editor.putString("signUp01_email", email);
//                            editor.putString("signUp01_passWord", passWord);
//                            editor.apply();
//                            startActivity(new Intent(SignUpActivity1.this, SignUpActivity2.class));
//                        }
//                    }
                }
            }
        });
    }
}