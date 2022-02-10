package com.example.doan;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.model.User;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    /*public void setUser1(String username*//*, String password*//*){
        editor.putString("KEY_USERNAME", username);
        //editor.putString("KEY_PASSWORD", password);
        editor.commit();
    }

    public String getUser(){
        return sharedPreferences.getString("KEY_USERNAME","");
    }*/
    public void setUser(int id, String username, String password, String name, String sdt, String diachi)
    {
        editor.putInt("KEY_ID", id);
        editor.putString("KEY_USERNAME", username);
        editor.putString("KEY_PASSWORD", password);
        editor.putString("KEY_NAME", name);
        editor.putString("KEY_SDT", sdt);
        editor.putString("KEY_DIACHI", diachi);
        editor.commit();
    }
    public User getUser(){
        int id = sharedPreferences.getInt("KEY_ID",0);
        String username = sharedPreferences.getString("KEY_USERNAME","");
        String password = sharedPreferences.getString("KEY_PASSWORD","");
        String name = sharedPreferences.getString("KEY_NAME","");
        String sdt = sharedPreferences.getString("KEY_SDT","");
        String diachi = sharedPreferences.getString("KEY_DIACHI","");
        User u = new User(id,username,password,name,sdt,diachi);
        return u;
    }
}
