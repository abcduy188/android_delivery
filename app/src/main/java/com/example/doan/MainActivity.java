package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.CategoryAdapter;
import com.example.Adapter.PopularAdapter;
import com.example.Db.DBService;
import com.example.model.Category;
import com.example.model.Food;
import com.example.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    EditText edtSearch;
    Button btnSearch;
    TextView txtdateMain,txtClickFood;
    ConstraintLayout constraintLayout;

    SessionManager sessionManager;
    TextView txtNameMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkView();
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        addEvents();

    }

    private void addEvents() {
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListProductActivity.class));
            }
        });

        User u = sessionManager.getUser();
        if(sessionManager.getLogin() == true && sessionManager.getUser().getId()>0)
        {
            txtNameMain.setText("Xin chào, "+ u.getUsername());
        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSearch.getText().toString().length() >0){
                    Intent intent = new Intent(MainActivity.this,ListProductActivity.class);
                    String key = edtSearch.getText().toString().trim();
                    intent.putExtra("keyword",key);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Nhập món ăn cần tìm vào!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtClickFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListProductActivity.class));
            }
        });
    }

    private void linkView() {
        txtdateMain = findViewById(R.id.txtdatemain);
        txtClickFood = findViewById(R.id.txtClickFood);
        constraintLayout = findViewById(R.id.constraintLayout);
        edtSearch = findViewById(R.id.editTextTextPersonName);
        btnSearch = findViewById(R.id.btnSearch);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String strDate = dateFormat.format(date);
        txtdateMain.setText(strDate);
        txtNameMain = findViewById(R.id.txtNameMain);
        sessionManager = new SessionManager(getApplicationContext());
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
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

            }
        });

        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, CartActivity.class));

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListProductActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<Food> foodlist = new DBService(this).getListFoods();

        adapter2 = new PopularAdapter(foodlist);
        recyclerViewPopularList.setAdapter(adapter2);

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