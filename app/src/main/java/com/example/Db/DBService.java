package com.example.Db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.GetMD5;
import com.example.doan.LoginActivity;
import com.example.doan.SessionManager;
import com.example.model.Category;
import com.example.model.Food;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.User;

import java.util.ArrayList;

public class DBService {
    SQLiteDatabase database;
    SessionManager sessionManager ;
    public DBService(Activity activity)
    {
        database = Database.initDatabase(activity);
    }

    public ArrayList<Category> getListCategories(){
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Categories",null);
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            byte[] anh = cursor.getBlob(2);
            list.add(new Category(id,ten,anh));
        }
        return list;
    }
    public ArrayList<Food> getListFoods(){
        ArrayList<Food> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Food",null);
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String des = cursor.getString(2);
            double fee = cursor.getDouble(3);
            byte[] pic = cursor.getBlob(4);
            int catId = cursor.getInt(5);
            list.add(new Food(id,title,pic,des,fee,catId));
        }
        return list;
    }
    public ArrayList<Food> getListFoodsByIdCate(int catId){
        Cursor cursor = database.rawQuery("SELECT * FROM Food Where idCategories = ?",new String[]{catId+""});
        if(catId < 0){
            cursor = database.rawQuery("SELECT * FROM Food",null);
        }
        ArrayList<Food> list = new ArrayList<>();

        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String des = cursor.getString(2);
            double fee = cursor.getDouble(3);
            byte[] pic = cursor.getBlob(4);
            list.add(new Food(id,title,pic,des,fee,catId));
        }
        return list;

    }
    public ArrayList<Food> getListFoodsByKeyWord(String keyword){
        ArrayList<Food> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Food where title like ?",new String[]{"%"+keyword+"%"});
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String des = cursor.getString(2);
            double fee = cursor.getDouble(3);
            byte[] pic = cursor.getBlob(4);
            int catId = cursor.getInt(5);
            list.add(new Food(id,title,pic,des,fee,catId));
        }
        return list;
    }

    public boolean checkUserName(String username) {
        Cursor cursor = database.rawQuery("SELECT * FROM User where UserName = ?", new String[]{username});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public User getUser(String username, String password) {
        User u = null;
        Cursor cursor = database.rawQuery("SELECT * FROM User where UserName = ? and PassWord = ? ", new String[]{username,password});
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                u = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            }
        }
        return u;
    }
    public long insertUser(String username, String password)
    {
        ContentValues values = new ContentValues();
        values.put("UserName",username);
        values.put("PassWord", GetMD5.MD5(password));

        long flag = database.insert("User", null, values);
        return flag;
    }
    public long updateUser(int id, String name, String sdt, String diachi)
    {
        ContentValues values = new ContentValues();
        values.put("Name",name);
        values.put("SDT", sdt);
        values.put("DiaChi", diachi);
        int flag = database.update("User",values,"ID = ?",new String[]{String.valueOf(id)});
        return flag;
    }
    public long insertOrder(Order o){

        ContentValues values = new ContentValues();
        values.put("UserID",o.getUserID());
        values.put("NguoiNhan",o.getShipName());
        values.put("TrangThai",o.getStatus());
        values.put("SDT",o.getShipPhone());
        values.put("NgayTao", o.getCreateDate());
        values.put("TongTien",o.getProductPrice());
        values.put("DiaChi",o.getShipAddress());
        values.put("SoLuong",o.getCountItems());
        long flag = 0;
        try

        {
            flag = database.insert("DonHang", null,values);
        }
        catch(Exception e){
            Log.e(null,e.toString());

        }

        return flag;

    }
    public long insertOrderDetail (ArrayList<Food> list, long id)
    {
        if(list != null){
            ArrayList<Food> listFood2 = list;
            ContentValues vl2 = new ContentValues();
            long detail = 0;

            int ido = (int) id;
            for (int i = 0; i < listFood2.size(); i++) {
                double fee = 0;
                fee = fee + (listFood2.get(i).getFee() * listFood2.get(i).getNumberInCart());
                vl2.put("OrderID", ido);
                vl2.put("ProductID", listFood2.get(i).getId());
                vl2.put("Quantity", listFood2.get(i).getNumberInCart());
                vl2.put("Price",fee);
                vl2.put("ProductName",listFood2.get(i).getTitle());
                detail = database.insert("OrderDetail",null,vl2);
            }
            return detail;
        }
        return -1;
    }
    public ArrayList<Order> getListOrderHis(int idUser){
        Cursor cursor = null;
        if(idUser == 1)
        {
            cursor = database.rawQuery("SELECT * FROM DonHang",null );
        }
        else{
            cursor = database.rawQuery("SELECT * FROM DonHang Where UserID = ?",new String[]{idUser+""});
        }
        ArrayList<Order> list = new ArrayList<>();
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                list.add(new Order(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5),cursor.getDouble(6), cursor.getString(7),cursor.getInt(8)));
            }
        }
        return list;

    }
    public ArrayList<OrderDetail> getListDetailHis(int idUser){
        Cursor cursor = null;

            cursor = database.rawQuery("SELECT * FROM OrderDetail Where OrderID = ?",new String[]{idUser+""});

        ArrayList<OrderDetail> list = new ArrayList<>();
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                list.add(new OrderDetail(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getDouble(3),cursor.getString(4)));
            }
        }
        return list;

    }
    public int editStatisOrder(int id, int Status)
    {
        ContentValues values = new ContentValues();
        values.put("TrangThai",Status);
        int flag = database.update("DonHang",values, "ID=?",new String[]{String.valueOf(id)});
        return flag;
    }

}
