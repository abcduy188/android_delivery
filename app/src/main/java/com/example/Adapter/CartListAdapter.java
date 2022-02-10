package com.example.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ChangeNumberItemsListener;


import com.example.doan.R;
import com.example.model.Food;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<Food> foods;
    private ChangeNumberItemsListener changeNumberItemsListener;
    Context context;

    public CartListAdapter(ArrayList<Food> Foods, Context context, ChangeNumberItemsListener changeNumberItemsListener) {

        this.foods = Foods;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foods.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foods.get(position).getFee()));
        double a = ((foods.get(position).getNumberInCart() * foods.get(position).getFee()) * 100.0) / 100.0;
        //holder.totalEachItem.setText(String.valueOf(Math.round((foods.get(position).getNumberInCart() * foods.get(position).getFee()) * 100.0) / 100.0));
        holder.num.setText(String.valueOf(foods.get(position).getNumberInCart()));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(a);
        holder.totalEachItem.setText(String.valueOf(str1));

        Bitmap drawableResourceId = BitmapFactory.decodeByteArray(foods.get(position).getPic(),0,foods.get(position).getPic().length);

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);


        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNumberItemsListener change = new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                    }
                };
                updatePres(position,1,change);
                changeNumberItemsListener.changed();
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNumberItemsListener change = new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                    }
                };
                updatePres(position,-1,change);
                changeNumberItemsListener.changed();
            }
        });

    }
    //Cap nhat lại pres
    public void updatePres(int pos,int amount,ChangeNumberItemsListener change){
        // Luu y: luu gioHang vao pres "gioHangGlobal"
        SharedPreferences pre = context.getSharedPreferences("gioHangGlobal",context.MODE_PRIVATE);
        //Save vao pres
        boolean needRemove = false;
        if(foods.get(pos).getNumberInCart()+amount == 0){
            needRemove = true;
        }
        if(needRemove){
            foods.remove(pos);
            change.changed();
        }else{
            foods.get(pos).setNumberInCart(foods.get(pos).getNumberInCart()+amount);
            change.changed();
        }

        SharedPreferences.Editor editor = pre.edit();
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(foods);
        editor.putString("gioHang", json1);
        editor.commit();
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);
        }
    }
}
