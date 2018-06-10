package com.stdiohue.basestrcuture.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.stdiohue.basestrcuture.database.dao.Database;
import com.stdiohue.basestrcuture.databinding.DialogAddUserBinding;
import com.stdiohue.basestrcuture.model.Data;
import com.stdiohue.basestrcuture.ui.adapter.TenNguoiAdapter;
import com.stdiohue.basestrcuture.viewmodel.MainViewModel;

import java.util.ArrayList;

public class AddUserDialog extends Dialog {
    private DialogAddUserBinding binding;
    private Database database;
    private TenNguoiAdapter adapter;
    private ArrayList<Data> arrayList;
    MainViewModel viewModel;

    public AddUserDialog(@NonNull Context context, MainViewModel mainViewModel,Database datab, ArrayList arrayL, TenNguoiAdapter adap) {
        super(context);
        this.viewModel = mainViewModel;
        this.arrayList=arrayL;
        this.adapter = adap;
        this.database=datab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DialogAddUserBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());
    }
    @Override
    public void show(){
        super.show();
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addname = binding.addName.getText().toString();
                String addphone = binding.addPhone.getText().toString();
                if (addname.equals("")){
                    Toast.makeText(getContext(),"Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
                }else {
                    if (addphone.equals("")) {
                        Toast.makeText(getContext(), "Bạn chưa nhập số", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.insertUserTable(addname, addphone);
                        Toast.makeText(getContext(),"Đã thêm", Toast.LENGTH_SHORT).show();
                        dismiss();
                        viewModel.getDataUser(arrayList,adapter);
                    }
                }
            }
        });
    }

}
