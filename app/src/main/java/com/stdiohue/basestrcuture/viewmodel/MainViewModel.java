package com.stdiohue.basestrcuture.viewmodel;

import android.database.Cursor;

import com.stdiohue.basestrcuture.ui.adapter.TenNguoiAdapter;

import java.util.ArrayList;

public interface MainViewModel {
    boolean insertUserTable(String userName, String phone);
    boolean updateUser(String oldUserName,String userName, String userPhone);
    int deleteUser(String userName);
    void getDataUser(ArrayList arrayList, TenNguoiAdapter adapter);
}
