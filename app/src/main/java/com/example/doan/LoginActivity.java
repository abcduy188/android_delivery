package com.example.doan;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Db.DBService;
import com.example.GetMD5;
import com.example.model.User;


public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtPassWord;
    Button btnLogin, btnRegister;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());

        linkViews();
        addEvents();
    }
    //code events button click
    private void addEvents() {
        //code click button login account
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserName.getText().toString();
                String password = edtPassWord.getText().toString();

                User getUser = new DBService(LoginActivity.this).getUser(username, GetMD5.MD5(password));
                if(getUser!= null)
                {
                    sessionManager.setUser(getUser.getId(),getUser.getUsername(),getUser.getPassword(), getUser.getName(), getUser.getSdt(),getUser.getDiaChi());
                    sessionManager.setLogin(true);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //code click button register account
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserName.getText().toString();
                String password = edtPassWord.getText().toString();
                boolean check = new DBService(LoginActivity.this).checkUserName(username);
                if(check == true)
                {
                    Toast.makeText(LoginActivity.this, "Account already exists", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    long flag = new DBService(LoginActivity.this).insertUser(username,password);
                    if(flag>0)
                    {
                        Toast.makeText(LoginActivity.this, "Register succes", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Register fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void linkViews() {
        edtUserName = (EditText) findViewById(R.id.edtDangNhap);
        edtPassWord = (EditText) findViewById(R.id.edtMatKhau);
        btnLogin = findViewById(R.id.btnDangNhap);
        btnRegister = findViewById(R.id.btnDangKy);
    }


}