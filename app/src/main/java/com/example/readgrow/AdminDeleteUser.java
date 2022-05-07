package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminDeleteUser extends AppCompatActivity {
    TextView txtName;
    TextView txtAge;
    TextView txtEmail;
    TextView txtPassword;
    TextView txtPostalCode;
    TextView txtAddress;
    Button btnDeleteUser;

    BookDatabaseHelper databaseHelper;
    SharedPreferences preferFrom_AdminListUser;

    ArrayList<BookUser> listUSer = new ArrayList<>();

    int picked_UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_user);

        txtName = findViewById(R.id.deleteUser_Name);
        txtAge = findViewById(R.id.deleteUser_Age);
        txtEmail = findViewById(R.id.deleteUser_Email);
        txtPassword = findViewById(R.id.deleteUser_Password);
        txtPostalCode = findViewById(R.id.deleteUser_PostalCode);
        txtAddress = findViewById(R.id.deleteUser_Address);
        btnDeleteUser = findViewById(R.id.btn_Edit_Profile);

        preferFrom_AdminListUser = PreferenceManager.getDefaultSharedPreferences(this);
        picked_UserID = preferFrom_AdminListUser.getInt("chosenID_byAdmin", 0);

        databaseHelper = new BookDatabaseHelper(this);
        /**Set data into the field*/
        Cursor cursor = databaseHelper.GetBookReaderById(picked_UserID);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                txtName.setText(cursor.getString(1));
                txtAge.setText(cursor.getString(2));
                txtAddress.setText(cursor.getString(3));
                txtPostalCode.setText(cursor.getString(4));
                txtEmail.setText(cursor.getString(5));
                txtPassword.setText(cursor.getString(6));
            }
        }
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deledeUser = databaseHelper.DeleteUser_By_Update_UserStatus(picked_UserID);
                if(deledeUser>0){
                    startActivity(new Intent(AdminDeleteUser.this, AdminListUser.class));
                }
            }
        });
    }
}