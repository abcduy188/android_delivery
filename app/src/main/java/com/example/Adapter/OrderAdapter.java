package com.example.Adapter;
import static com.example.doan.R.color.bae;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.DetailOrderHistoryActivity;
import com.example.doan.R;
import com.example.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    ArrayList<Order> orders;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderhistory, parent, false);
        return new OrderAdapter.ViewHolder(inflate);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.txtdateorder.setText(orders.get(position).getCreateDate());
        //holder.txtprice.setText(String.valueOf(orders.get(position).getProductPrice()));
        holder.txtprice.setText(String.valueOf(orders.get(position).getProductPrice()));
        holder.txtTotalItemOrder.setText(String.valueOf(orders.get(position).getCountItems()));
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(orders.get(position).getProductPrice());
        holder.txtprice.setText(str1);
        if(orders.get(position).getStatus() == 1)
        {

            holder.txtstatus.setText("Chờ duyệt");
        }else if(orders.get(position).getStatus() == 3)
        {

            holder.txtstatus.setText("Hoàn thành");
        }else if(orders.get(position).getStatus() == 2)
        {

            holder.txtstatus.setText("Đang chờ giao hàng");
        }else if(orders.get(position).getStatus() == -1)
        {

            holder.txtstatus.setText("Đã Hủy");
        }else {

            holder.txtstatus.setText("Lỗi");
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(view.getContext(), DetailOrderHistoryActivity.class);
                intent.putExtra("idOrder", order.getId());
                intent.putExtra("date",order.getCreateDate());
                intent.putExtra("total",order.getProductPrice());
                intent.putExtra("name",order.getShipName());
                intent.putExtra("address",order.getShipAddress());
                intent.putExtra("phone",order.getShipPhone());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdateorder, txtprice, txtTotalItemOrder,txtstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdateorder = itemView.findViewById(R.id.txtdateorder);
            txtprice = itemView.findViewById(R.id.txtprice);
            txtTotalItemOrder = itemView.findViewById(R.id.txtTotalItemOrder);
            txtstatus = itemView.findViewById(R.id.txtstatusCancel);
        }
    }
}
