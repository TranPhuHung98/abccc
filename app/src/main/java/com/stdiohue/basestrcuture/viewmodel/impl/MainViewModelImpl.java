package com.stdiohue.basestrcuture.viewmodel.impl;

import android.database.Cursor;

import com.stdiohue.basestrcuture.database.DataBaseInteractor;
import com.stdiohue.basestrcuture.databinding.DanhSachNguoiBinding;
import com.stdiohue.basestrcuture.ui.adapter.TenNguoiAdapter;
import com.stdiohue.basestrcuture.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainViewModelImpl implements MainViewModel {
    private DataBaseInteractor database;
    private DanhSachNguoiBinding binding;
    private TenNguoiAdapter.OnclickListener listener;

    public MainViewModelImpl(DataBaseInteractor database) {
        this.database = database;
    }

    @Override
    public boolean insertUserTable(String userName, String phone) {
        return database.insertUser(userName, phone);
    }

    @Override
    public boolean updateUser(String oldUserName,String userName, String userPhone) {
        return database.updateUser(oldUserName,userName,userPhone);
    }

    @Override
    public int deleteUser(String userName) {
        return database.deleteUser(userName);
    }

    @Override
    public void getDataUser(ArrayList arrayList, TenNguoiAdapter adapter) {
        database.getDataUser(arrayList,adapter);
    }

//    @Override
//    public Cursor selectTable() {
//        return database.selectUser();
//    }
//
//    @Override
//    public void onItemClick() {
////        listener.onNameClick(binding.txtTen.);
//    }

}
