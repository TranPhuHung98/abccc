package com.stdiohue.basestrcuture.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stdiohue.basestrcuture.database.DataBaseInteractor;
import com.stdiohue.basestrcuture.model.Data;
import com.stdiohue.basestrcuture.ui.adapter.TenNguoiAdapter;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper implements DataBaseInteractor {
    public String tableName="Contact";
    public String co1="ID";
    public String co2="Name";
    public String co3="Phone";
    ArrayList<Data> arrayList;
    TenNguoiAdapter adapter;
    Context ct;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.ct=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+tableName+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+co2+" varchar(20),"+co3+" varchar(11))");
//        db.execSQL("create table "+tableName+" ("+co2+" varchar(20) PRIMARY KEY,"+co3+" varchar(11))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean insertUser(String userName, String userPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(co2,userName);
        contentValues.put(co3,userPhone);
        long result = db.insert(tableName,null,contentValues);
        return result != -1;
    }

    @Override
    public boolean updateUser(String oldUserName,String userName, String userPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(co2,userName);
        contentValues.put(co3,userPhone);
//        db.update(tableName,contentValues,"Name = ?",new String[]{String.valueOf(userName)});
//        db.execSQL("UPDATE "+tableName+" SET Name='"+userName+"', Phone='"+userPhone+"' where Name='"+oldUserName+"'");
        insertUser(userName,userPhone);
        deleteUser(oldUserName);
        return true;
    }

    @Override
    public Integer deleteUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,"Name = ?",new String[]{String.valueOf(userName)});
    }

    @Override
    public Cursor selectUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+tableName,null);
        return res;
    }

//    public Cursor sortUser(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor c = db.query(tableName,null,null,null,null,null,co2+" DESC");
//        return c;
//    }

    @Override
    public void getDataUser(ArrayList arrayList, TenNguoiAdapter adapter){
        Cursor dataDanhBa = selectUser();
        arrayList.clear();
        while (dataDanhBa.moveToNext()) {
            String Ten = dataDanhBa.getString(1);
            String SoDT = dataDanhBa.getString(2);
            arrayList.add(new Data(Ten, SoDT));
        }
        adapter.updateAdapter(arrayList);
    }

}
