package com.example.Db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Database {
    public static SQLiteDatabase initDatabase(Activity activity){
        String databaseName = "doana.db";
        try{
            String outFileName=activity.getApplicationInfo().dataDir+"/databases/"+databaseName;
            File f=new File(outFileName);
            if(!f.exists())
            {
                InputStream e = activity.getAssets().open(databaseName);
                File folder=new File(activity.getApplicationInfo().dataDir+"/databases/");
                if(!folder.exists()){
                    folder.mkdir();
                }

                FileOutputStream outputStream=new FileOutputStream(outFileName);
                byte[]buffer=new byte[1024];

                int length;
                while ((length=e.read(buffer))>0){
                    outputStream.write(buffer,0,length);
                }
                outputStream.flush();
                outputStream.close();
                e.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE,null);
    }
}
