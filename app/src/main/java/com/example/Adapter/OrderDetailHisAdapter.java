package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.model.OrderDetail;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderDetailHisAdapter extends RecyclerView.Adapter<OrderDetailHisAdapter.ViewHolder>{

    ArrayList<OrderDetail> orderDetails;

    public OrderDetailHisAdapter(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public OrderDetailHisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderdetailhis, parent, false);
        return new OrderDetailHisAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txttitle.setText(orderDetails.get(position).getQty()+"x "+orderDetails.get(position).getNameProduct());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(orderDetails.get(position).getPrice());

        holder.txtprice.setText(str1);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttitle, txtprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtprice = itemView.findViewById(R.id.txtprice);

        }
    }
}
