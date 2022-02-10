package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.CategoryAdapter;
//import com.example.Adapter.ListProductAdapter;
import com.example.Adapter.ProductAdapter;
import com.example.Db.DBService;
import com.example.model.Category;
import com.example.model.Food;
import com.example.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListProductActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private GridView girdView2;
    RecyclerView rcvListProduct;
    private RecyclerView recyclerViewCategoryList;
    private Intent prevIntent;
    SessionManager sessionManager;
    TextView txtNameCate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        linkView();

        prevIntent = getIntent();
//
        String key = prevIntent.getStringExtra("keyword");
        ArrayList<Food> foodlist = new DBService(this).getListFoodsByIdCate(prevIntent.getIntExtra("catId",-1));
        if( key != null){
            foodlist =new DBService(this).getListFoodsByKeyWord(key);
        }
        txtNameCate.setText(prevIntent.getStringExtra("title"));
        recyclerViewCategory();
        recyclerViewPopular(foodlist);
        bottomNavigation();
    }

    private void linkView() {
        sessionManager = new SessionManager(getApplicationContext());
        txtNameCate = findViewById(R.id.txtnameCate);
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
                    startActivity(new Intent(ListProductActivity.this, ProfileActivity.class));
                }
                else {
                    startActivity(new Intent(ListProductActivity.this, LoginActivity.class));
                }

            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListProductActivity.this, CartActivity.class));

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListProductActivity.this, MainActivity.class));
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListProductActivity.this, ListProductActivity.class));
            }
        });
    }


    //Rcv
    private void recyclerViewPopular(ArrayList<Food> foodlist) {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvListProduct = findViewById(R.id.rcvListProduct);
        rcvListProduct.setLayoutManager(linearLayoutManager);



        adapter = new ProductAdapter(foodlist);
        rcvListProduct.setAdapter(adapter);


    }
    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<Category> categoryList = new DBService(this).getListCategories();

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
    }
}