package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.example.model.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailActivty extends AppCompatActivity {

    private TextView addToCardBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private Food object;
    private int numberOrder = 1;

    private ArrayList<Food> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);

        linkViews();
        loadData();
    }

    private void loadData() {
        object = (Food) getIntent().getSerializableExtra("object");

        Bitmap drawableResourceId = BitmapFactory.decodeByteArray(object.getPic(),0,object.getPic().length);

        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        titleTxt.setText(object.getTitle());
        //feeTxt.setText(String.valueOf(object.getFee()));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(object.getFee());
        feeTxt.setText(str1);
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingPreferences(object);
                Toast.makeText(DetailActivty.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    //     Luu vao gioHangGlobal
    public void savingPreferences(Food f){
        // Luu y: luu gioHang vao pres "gioHangGlobal"
        SharedPreferences pre = getSharedPreferences("gioHangGlobal",MODE_PRIVATE);

        //Lấy dữ liệu trong preshare nếu có
        Gson gson = new Gson();
        String result = pre.getString("gioHang","");
        list = gson.fromJson(result,new TypeToken<ArrayList<Food>>(){}.getType());

        // Check f co ton tai trong list chưa
        boolean isExist = false;
        if(list != null){
            for (int i=0;i< list.size();i++){
                if(f.getId() == list.get(i).getId()){
                    list.get(i).setNumberInCart(list.get(i).getNumberInCart()+numberOrder);
                    isExist= true;
                }
            }
        }
        if(list == null){
            list = new ArrayList<Food>();
        }
        if(!isExist){
            f.setNumberInCart(numberOrder);
            list.add(f);
        }

        //Save vao pres
        SharedPreferences.Editor editor = pre.edit();
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(list);
        editor.putString("gioHang", json1);
        editor.commit();
    }

    private void linkViews() {
        addToCardBtn = findViewById(R.id.addToCardBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood = findViewById(R.id.foodPic);
    }
}