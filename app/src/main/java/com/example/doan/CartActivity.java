package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.Adapter.CartListAdapter;
import com.example.ChangeNumberItemsListener;
import com.example.model.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private TextView totalFeeTxt,totalTxt, emptyTxt,txtEmpty;
    private double tax;
    private ScrollView scrollView;
    Button btnCheckout;
    ArrayList<Food> list;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sessionManager = new SessionManager(getApplicationContext());

        initView();
        initList();

        calculateCard();
        bottomNavigation();
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
                    startActivity(new Intent(CartActivity.this, ProfileActivity.class));
                }
                else {
                    startActivity(new Intent(CartActivity.this, LoginActivity.class));
                }

            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, CartActivity.class));

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, ListProductActivity.class));
            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        // Luu y: luu gioHang vao pres "gioHangGlobal"
        SharedPreferences pre = getSharedPreferences("gioHangGlobal",MODE_PRIVATE);

        //Lấy dữ liệu trong preshare nếu có
        Gson gson = new Gson();
        String result = pre.getString("gioHang","");
        list = gson.fromJson(result,new TypeToken<ArrayList<Food>>(){}.getType());

        if(list.size() == 0){
            txtEmpty.setText("Giỏ hàng của bạn đang trống!!");
            btnCheckout.setText("Please buy somthings!");
            btnCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Quay lai trang chu
                    Intent a= new Intent(CartActivity.this,MainActivity.class);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                }
            });
            return;
        }else{
            if(sessionManager.getLogin()==true && sessionManager.getUser().getId()>0)
            {
                btnCheckout.setText("Tiếp tục");

                btnCheckout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Thanh toan
                        Intent a= new Intent(CartActivity.this,OrderActivity.class);

                        a.putExtra("tongtien", totalFeeTxt.getText().toString().trim());
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                    }
                });
            }else {
                btnCheckout.setText("Bạn phải đăng nhập trước");

                btnCheckout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Thanh toan
                        Intent a= new Intent(CartActivity.this,LoginActivity.class);
                        startActivity(a);
                    }
                });
            }



        }


        adapter = new CartListAdapter(list, this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        recyclerViewList.setAdapter(adapter);
    }

    private void calculateCard() {
        double total = Math.round(getTotalFee() * 100) / 100;
        int count = getTotalitems();
        totalFeeTxt.setText(String.valueOf(count));
        //totalTxt.setText(String.valueOf(total));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(total);
        totalTxt.setText(str1);
        initList();
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerview);
        totalFeeTxt = findViewById(R.id.txtcount);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        txtEmpty = findViewById(R.id.txtEmpty);
        btnCheckout= findViewById(R.id.btnCheckout);
    }

    public Double getTotalFee() {
        SharedPreferences pre = getSharedPreferences("gioHangGlobal",MODE_PRIVATE);

        //Lấy dữ liệu trong preshare nếu có
        Gson gson = new Gson();
        String result = pre.getString("gioHang","");
        list = gson.fromJson(result,new TypeToken<ArrayList<Food>>(){}.getType());

        if(list != null){
            ArrayList<Food> listFood2 = list;
            double fee = 0;
            for (int i = 0; i < listFood2.size(); i++) {
                fee = fee + (listFood2.get(i).getFee() * listFood2.get(i).getNumberInCart());
            }
            return fee;
        }
        return 0.0;
    }
    public int getTotalitems() {
        SharedPreferences pre = getSharedPreferences("gioHangGlobal",MODE_PRIVATE);

        //Lấy dữ liệu trong preshare nếu có
        Gson gson = new Gson();
        String result = pre.getString("gioHang","");
        list = gson.fromJson(result,new TypeToken<ArrayList<Food>>(){}.getType());
        int count = 0;
        if(list != null){
            ArrayList<Food> listFood2 = list;

            for (int i = 0; i < listFood2.size(); i++) {
              count+=  listFood2.get(i).getNumberInCart();
            }
            return count;
        }
        return 0;
    }
}