package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class SignUpActivity2 extends AppCompatActivity {

    String country;
    String province;
    int age;
    String postalCode;
    String address;
    String genre;
    String fullAddress;
    BookDatabaseHelper bookDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        bookDatabaseHelper = new BookDatabaseHelper(this);

        Spinner countryTxt = findViewById(R.id.signUp2_country);
        Spinner provinceTxt = findViewById(R.id.signUp2_province);
        EditText ageTxt = findViewById(R.id.signUp2_age);
        EditText postCodeTxt = findViewById(R.id.signUp2_postalcode);
        EditText addressTxt = findViewById(R.id.signUp2_address);
        Spinner genreTxt = findViewById(R.id.signUp2_genre);

        Button btnCreateAcc = findViewById(R.id.signUp2_button);

        /**use SharePrefences to get information from SignUp_Activity01*/
        SharedPreferences sharedPrefer = PreferenceManager.getDefaultSharedPreferences(this);

        String fName = sharedPrefer.getString("signUp01_fName", "Non");
        String email = sharedPrefer.getString("signUp01_email","none");
        String passWord = sharedPrefer.getString("signUp01_passWord","none");

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            int ID;
            int interestID;
            @Override
            public void onClick(View view) {
                /**
                 * Check if user miss required information
                 * */
                if(TextUtils.isEmpty(ageTxt.getText().toString()) || TextUtils.isEmpty(postCodeTxt.getText().toString())||
                        TextUtils.isEmpty(addressTxt.getText().toString())){
                    if(TextUtils.isEmpty(ageTxt.getText().toString())){
                        ageTxt.setError("Your age is missing");
                    }
                    if(TextUtils.isEmpty(addressTxt.getText().toString())){
                        addressTxt.setError("Your address is missing");
                    }
                    if(TextUtils.isEmpty(postCodeTxt.getText().toString())){
                        postCodeTxt.setError("Missing postal code");
                    }
                }else{
                    country = countryTxt.getSelectedItem().toString();
                    province = provinceTxt.getSelectedItem().toString();
                    age = Integer.parseInt(ageTxt.getText().toString());
                    postalCode = postCodeTxt.getText().toString();
                    address = addressTxt.getText().toString();
                    genre = genreTxt.getSelectedItem().toString();

                    fullAddress = address + ", " + province + ", " + country;
                    /** Store the information of new user into database
                     * */
                    ID = bookDatabaseHelper.AddReader(fName, age, fullAddress, postalCode, email, passWord);
                    interestID = bookDatabaseHelper.AddBookReaderInterest(ID, genre);
                    if(ID > 0 && interestID>0){
                        Toast.makeText(SignUpActivity2.this, Html.fromHtml("<big>User is created successfully</big>"),
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity2.this, MainActivity.class));
                    }else{
                        Toast.makeText(SignUpActivity2.this, "User account not created", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}