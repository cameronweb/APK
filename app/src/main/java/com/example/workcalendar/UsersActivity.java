package com.example.workcalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.workcalendar.Adapters.UsersList_Adapter;
import com.example.workcalendar.DAO.DAOFactory;
import com.example.workcalendar.DAO.UsersDAO;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.users_listview);
        //toolbar = findViewById(R.id.users_tool_bar);
        registerForContextMenu(recyclerView);
        setTitle(R.string.users_view_label);
        //setSupportActionBar(toolbar);
        InitActivity(false);
    }

    private void InitActivity(boolean afterEdit) {

        DAOFactory Sqllitefactory = DAOFactory.getDAOFactory(getString(R.string.database));
        UsersDAO usersDAO = Sqllitefactory.getUserDAO();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        UsersList_Adapter usersList_adapter = new UsersList_Adapter(usersDAO.getUsers());
        recyclerView.setAdapter(usersList_adapter);
        if (afterEdit)
        {
            usersList_adapter.notifyDataSetChanged();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.users_add_menu_item:
                ShowActivity(false,0,Add_User.class);
                break;

        }
        return true;
    }

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getGroupId();
        final int user_id=item.getItemId();
        switch (id) {
            case 0:
                ShowActivity(true,item.getItemId(),Add_User.class);
                break;
            case 1:
                DialogInterface.OnClickListener dialofClickLisiner=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                Delete_user(user_id);
                                InitActivity(true);
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder=new AlertDialog.Builder(UsersActivity.this);
                builder.setMessage("Удалить запись?").setPositiveButton("Да",dialofClickLisiner).setNegativeButton("Нет",dialofClickLisiner).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void ShowActivity(boolean isEdit, int user_id,Class class_name) {
        Intent intent = new Intent(UsersActivity.this, class_name);
        intent.putExtra("isEdit", isEdit);
        intent.putExtra("User_id",user_id);
        startActivityForResult(intent, 1);
    }
    public void ShowActivity(Class class_name) {
        Intent intent = new Intent(UsersActivity.this, class_name);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null & resultCode == RESULT_OK) {
            boolean afteredit=data.getBooleanExtra("type",false);
            InitActivity(afteredit);
        }
    }

    public void onFabClick(View view) {
        if(view.getId()==R.id.fab_add_user) {
            ShowActivity(false, 0,Add_User.class);
        }else
        {
            ShowActivity(MainActivity.class);
        }
    }
    public void Delete_user(int user_id)
    {
        DAOFactory factory=DAOFactory.getDAOFactory(getString(R.string.database));
        UsersDAO usersDAO=factory.getUserDAO();
        usersDAO.DeleteUser(user_id);
    }
}
