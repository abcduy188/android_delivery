package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Db.DBService;

public class ProfileActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView txtName,txtorder;
    EditText edtName, edtSdt, edtDiaChi;
    Button btnCapnhat;
    LinearLayout lnOrderHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        newSession();
        linkViews();
        loadData();
        addEvents();
        bottomNavigation();
    }

    private void newSession() {
        sessionManager = new SessionManager(getApplicationContext());
    }

    private void linkViews() {
        txtName = findViewById(R.id.txtName);
        edtName = findViewById(R.id.edtName);
        edtSdt = findViewById(R.id.edtSdt);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnCapnhat = findViewById(R.id.btnCapnhat);
        txtorder = findViewById(R.id.txtorder);
         lnOrderHistory = findViewById(R.id.lnOrderHistory);
    }

    private void loadData() {
        String name = sessionManager.getUser().getName();
        String sdt = sessionManager.getUser().getSdt();
        String diachi = sessionManager.getUser().getDiaChi();
        if (name == null || name.equals("")) {
            txtName.setText("Chưa cập nhật");
        } else {
            txtName.setText(name);
        }
        if (name != null || !name.equals("")) {
            edtName.setText(name);
        }
        if (sdt != null || !sdt.equals("")) {
            edtSdt.setText(sdt);
        }
        if (diachi != null || !diachi.equals("")) {
            edtDiaChi.setText(diachi);
        }
    }

    private void addEvents() {
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String sdt = edtSdt.getText().toString();
                String diachi = edtDiaChi.getText().toString();
                long flag = new DBService(ProfileActivity.this).updateUser(sessionManager.getUser().getId(), name, sdt, diachi);
                if (flag > 0) {
                    sessionManager.setUser(sessionManager.getUser().getId(), sessionManager.getUser().getUsername(), sessionManager.getUser().getPassword(), name, sdt, diachi);
                    Toast.makeText(ProfileActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(ProfileActivity.this, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,OrderHistoryActivity.class));
            }
        });
    }
    private void bottomNavigation() {

        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        LinearLayout listBtn = findViewById(R.id.listBtn);
        LinearLayout lnProfile = findViewById(R.id.lnProfile);
        LinearLayout lnCart = findViewById(R.id.lnCart);
        lnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setLogin(false);
                sessionManager.setUser(0,"","","","","");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                System.exit(0);
            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, CartActivity.class));

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ListProductActivity.class));
            }
        });
    }

}