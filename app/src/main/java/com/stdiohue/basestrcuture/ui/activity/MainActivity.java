package com.stdiohue.basestrcuture.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.stdiohue.basestrcuture.R;
import com.stdiohue.basestrcuture.base.BaseActivity;
import com.stdiohue.basestrcuture.model.Data;
import com.stdiohue.basestrcuture.database.dao.Database;
import com.stdiohue.basestrcuture.databinding.ActivityMainBinding;
import com.stdiohue.basestrcuture.ui.adapter.TenNguoiAdapter;
import com.stdiohue.basestrcuture.ui.dialog.AddUserDialog;
import com.stdiohue.basestrcuture.ui.dialog.EditUserDialog;
import com.stdiohue.basestrcuture.viewmodel.MainViewModel;
import com.stdiohue.basestrcuture.viewmodel.impl.MainViewModelImpl;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements TenNguoiAdapter.OnclickListener{
    private Database database;
    ArrayList<Data> arrayList;
    TenNguoiAdapter adapter;
    private MainViewModel viewModel;
    private EditUserDialog editUserDialog;
    private AddUserDialog addUserDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        arrayList = new ArrayList<>();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new TenNguoiAdapter(this);
        viewDataBinding.recyclerView.setAdapter(adapter);
        database = new Database(this,"DanhBa.sqlite",null,1);
        viewModel = new MainViewModelImpl(database);
        database.getDataUser(arrayList,adapter);
    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {

    }

    @Override
    protected void pauseScreen() {

    }

    @Override
    protected void destroyScreen() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            addUserDialog = new AddUserDialog(this,viewModel,database,arrayList,adapter);
            addUserDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNameClick(String txtName, String txtPhone) {
        editUserDialog = new EditUserDialog(this, txtName, txtPhone, viewModel,database, arrayList, adapter);
        editUserDialog.show();
    }
}
