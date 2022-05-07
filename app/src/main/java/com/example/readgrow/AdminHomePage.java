package com.example.readgrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        Button btnCreateUser = findViewById(R.id.btn_Admin_CreateUser);
        Button btnManageUser = findViewById(R.id.btn_admin_ManageUser);
        Button btnAdminSendMessage = findViewById(R.id.btn_admin_SendMessage);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this, SignUpActivity1.class));
            }
        });

        btnManageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this, AdminListUser.class));
            }
        });

        btnAdminSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this, ListUserForSendMessage.class));
            }
        });
    }
}