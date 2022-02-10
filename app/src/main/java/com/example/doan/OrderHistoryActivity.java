package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.Adapter.OrderAdapter;
import com.example.Db.DBService;
import com.example.model.Order;
import com.example.model.User;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView rcvListOrderHistory;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        sessionManager = new SessionManager(getApplicationContext());
        linkViews();
        loadData();
        addEvents();
        bottomNavigation();


    }
    private void addEvents() {
        rcvListOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



    private void linkViews() {
        rcvListOrderHistory = findViewById(R.id.rcvListOrderHistory);
    }
    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvListOrderHistory.setLayoutManager(linearLayoutManager);
        User u = sessionManager.getUser();
        ArrayList<Order> orders = new DBService(this).getListOrderHis(u.getId());
        adapter = new OrderAdapter(orders);
        rcvListOrderHistory.setAdapter(adapter);
    }
    private void bottomNavigation() {

        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        LinearLayout listBtn = findViewById(R.id.listBtn);
        LinearLayout lnProfile = findViewById(R.id.lnProfile);
        LinearLayout lnCart = findViewById(R.id.lnCart);
        lnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getUser().getId() >0 && sessionManager.getLogin()==false)
                {
                    startActivity(new Intent(OrderHistoryActivity.this, ProfileActivity.class));
                }
                else {
                    startActivity(new Intent(OrderHistoryActivity.this, LoginActivity.class));
                }

            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderHistoryActivity.this, CartActivity.class));

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderHistoryActivity.this, MainActivity.class));
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderHistoryActivity.this, ListProductActivity.class));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}