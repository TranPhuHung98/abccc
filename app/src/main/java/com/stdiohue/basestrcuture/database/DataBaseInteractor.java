package com.stdiohue.basestrcuture.database;

import android.database.Cursor;

import com.stdiohue.basestrcuture.ui.adapter.TenNguoiAdapter;

import java.util.ArrayList;

public interface DataBaseInteractor {

    boolean insertUser(String userName, String userPhone);

    boolean updateUser(String oldUserName,String userName, String userPhone);

    Integer deleteUser(String userName);

    Cursor selectUser();

    void getDataUser(ArrayList arrayList, TenNguoiAdapter adapter);
}
