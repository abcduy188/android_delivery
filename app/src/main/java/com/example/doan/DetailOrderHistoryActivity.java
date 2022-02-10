package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.OrderAdapter;

import com.example.Adapter.OrderDetailHisAdapter;
import com.example.Db.DBService;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.User;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailOrderHistoryActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView rcvListDetailOrderHis;
    SessionManager sessionManager;
    TextView date, txtid, total, txtname,txtPhone, txtAddress;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_history);
        sessionManager = new SessionManager(getApplicationContext());
        linkViews();
        loadData();
        bottomNavigation();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void bottomNavigation() {

        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        LinearLayout listBtn = findViewById(R.id.listBtn);
        LinearLayout lnProfile = findViewById(R.id.lnProfile);
        LinearLayout lnCart = findViewById(R.id.lnCart);
        lnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getUser().getId() >0)
                {
                    startActivity(new Intent(DetailOrderHistoryActivity.this, ProfileActivity.class));
                }
                else {
                    startActivity(new Intent(DetailOrderHistoryActivity.this, LoginActivity.class));
                }

            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailOrderHistoryActivity.this, CartActivity.class));

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailOrderHistoryActivity.this, MainActivity.class));
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailOrderHistoryActivity.this, ListProductActivity.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = getIntent().getIntExtra("idOrder",0);
        if(sessionManager.getUser().getId() == 1)
        {
            if(item.getItemId() == R.id.mnOke)
            {
                int flag = new DBService(DetailOrderHistoryActivity.this).editStatisOrder(id,2);
                if(flag >0 )
                {
                    Toast.makeText(DetailOrderHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailOrderHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                finish();

            }else  if(item.getItemId() == R.id.mnDone)
            {
                int flag = new DBService(DetailOrderHistoryActivity.this).editStatisOrder(id,3);
                if(flag >0 )
                {
                    Toast.makeText(DetailOrderHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailOrderHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
            else  if(item.getItemId() == R.id.mnCancel)
            {
                int flag = new DBService(DetailOrderHistoryActivity.this).editStatisOrder(id,-1);
                if(flag >0 )
                {
                    Toast.makeText(DetailOrderHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailOrderHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
        else {
            Toast.makeText(this, "You dont have permition", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {

        int id = getIntent().getIntExtra("idOrder",0);
        txtid.setText("Mã đơn hàng: "+id);
        date.setText(getIntent().getStringExtra("date"));
        //total.setText(String.valueOf(getIntent().getDoubleExtra("total",0)));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(getIntent().getDoubleExtra("total",0));
        total.setText(str1);
        txtname.setText(getIntent().getStringExtra("name"));
        txtPhone.setText(getIntent().getStringExtra("phone"));
        txtAddress.setText(getIntent().getStringExtra("address"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvListDetailOrderHis.setLayoutManager(linearLayoutManager);
        ArrayList<OrderDetail> orderDetails = new DBService(this).getListDetailHis(id);
        adapter = new OrderDetailHisAdapter(orderDetails);
        rcvListDetailOrderHis.setAdapter(adapter);
    }

    private void linkViews() {
        sessionManager = new SessionManager(getApplicationContext());
        rcvListDetailOrderHis =findViewById(R.id.rcvListDetailOrderHis);
        date = findViewById(R.id.txtdate);
        txtid =findViewById(R.id.txtid);
        total = findViewById(R.id.txtTotal);
        txtname = findViewById(R.id.txtshipname);
        txtPhone =findViewById(R.id.txtphone);
        txtAddress = findViewById(R.id.txtaddress);
    }
}