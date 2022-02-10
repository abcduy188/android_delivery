package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.CartListAdapter;
import com.example.ChangeNumberItemsListener;
import com.example.Db.DBService;
import com.example.model.Food;
import com.example.model.Order;
import com.example.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {

    EditText edtDC, edtHoten, edtSdt;
    Button btnConfirm;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private TextView totalTxt, emptyTxt, txtcount;
    private double tax;
    private ScrollView scrollView;
    Button btnCheckout;
    ArrayList<Food> list;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initList();
        addEvents();

    }

    private void addEvents() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String total = txtcount.getText().toString().trim();
                if(total.equals("0"))
                {
                    btnConfirm.setText("Giỏ hàng đang trống");
                    startActivity(new Intent(OrderActivity.this,ListProductActivity.class));
                }
                else {
                    if (edtDC.getText().toString().trim().length() < 5) {
                        Toast.makeText(OrderActivity.this, "Vui lòng nhập đúng địa chỉ!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (edtHoten.getText().toString().trim().length() == 0 || edtSdt.getText().toString().trim().length() == 0) {
                            Toast.makeText(OrderActivity.this, "Vui lòng đầy đủ thông tin!", Toast.LENGTH_SHORT).show();

                        } else {
                            if (edtSdt.getText().toString().trim().length() != 10) {
                                Toast.makeText(OrderActivity.this, "Số điện thoại gồm 10 số!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (edtSdt.getText().toString().trim().charAt(0) != '0') {
                                    Toast.makeText(OrderActivity.this, "Phone format is 0xxx!", Toast.LENGTH_SHORT).show();
                                } else {

                                    //luu vao db
                                    if(sessionManager.getLogin() == true && sessionManager.getUser().getId()>0)
                                    {


                                        Date date = Calendar.getInstance().getTime();
                                        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss");
                                        String strDate = dateFormat.format(date);
                                        int status = 1;
                                        int idor = 0;
                                        int countitems = Integer.parseInt(txtcount.getText().toString());
                                        double price = Double.parseDouble(totalTxt.getText().toString());
                                        Order o = new Order(idor,sessionManager.getUser().getId(),strDate,edtHoten.getText().toString(),status,edtSdt.getText().toString(), price, edtDC.getText().toString(),countitems);
                                        long id = new DBService(OrderActivity.this).insertOrder(o);
                                        long doo = new DBService(OrderActivity.this).insertOrderDetail(list, id);
                                        if(doo > 0 )
                                        {
                                            Toast.makeText(OrderActivity.this, "Dat hang thanh cong", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(OrderActivity.this,CompleteActivity.class));
                                        }


                                    }





                                    Intent a = new Intent(OrderActivity.this, CompleteActivity.class);
                                    a.putExtra("ten", edtHoten.getText().toString().trim());
                                    a.putExtra("sdt", edtSdt.getText().toString().trim());
                                    a.putExtra("diachi", edtDC.getText().toString().trim());
                                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);




                                    startActivity(a);

                                }
                            }
                        }
                    }
                }
            }
        });
    }


    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);


        SharedPreferences pre = getSharedPreferences("gioHangGlobal",MODE_PRIVATE);


        Gson gson = new Gson();
        String result = pre.getString("gioHang","");
        list = gson.fromJson(result,new TypeToken<ArrayList<Food>>(){}.getType());


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
        txtcount.setText(String.valueOf(count));
        totalTxt.setText(String.valueOf(total));
    }
    private void initView() {
        edtDC = findViewById(R.id.edtDiaChi);
        edtHoten = findViewById(R.id.edtHoten);
        edtSdt = findViewById(R.id.edtSdt);
        btnConfirm = findViewById(R.id.btnCheckout);

        recyclerViewList = findViewById(R.id.recyclerview);

        txtcount = findViewById(R.id.txtcount);
        totalTxt = findViewById(R.id.txtTotalOrder);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView5);
        double total = Math.round(getTotalFee() * 100) / 100;

        int count = getTotalitems();
        txtcount.setText(String.valueOf(count));
        totalTxt.setText(String.valueOf(total));
        sessionManager = new SessionManager(getApplicationContext());
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