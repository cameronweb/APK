package com.example.workcalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.workcalendar.Adapters.Wokday_adapter;
import com.example.workcalendar.DAO.DAOFactory;
import com.example.workcalendar.DAO.WorkDayDAO;
import com.example.workcalendar.Entity.WorkDay;
import com.example.workcalendar.AsyncTasks.AsyncTask_LoadWorDays;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;
    ListView list_view;
    private ArrayList<WorkDay> workDayArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadWorkDays();

    }
    public  void loadWorkDays()
    {
        AsyncTask_LoadWorDays task_loadWorDays=new AsyncTask_LoadWorDays(list_view);
        task_loadWorDays.execute(workDayArrayList);

    }
    public void init()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.menu_navigator);
        navigationView.setNavigationItemSelectedListener(this);
        list_view=(ListView)findViewById(R.id.list_view);
        registerForContextMenu(list_view);
        toolbar=(Toolbar)findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.START);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        workDayArrayList=new ArrayList<WorkDay>();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id =menuItem.getItemId();
        switch(id)
        {
            case R.id.add_neworkday:
                ShowActivity(WorkDayLyaout.class, false,0);
                break;
            case R.id.users_show:
                ShowActivity(UsersActivity.class, false,0);
                break;
            case R.id.app_settings:
                ShowActivity(Settings.class,false,0);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.START);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ListView listView =(ListView)v;
        Wokday_adapter adapter=(Wokday_adapter) listView.getAdapter();
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo=(AdapterView.AdapterContextMenuInfo)menuInfo;
        int position=adapterContextMenuInfo.position;
        int id=adapter.getItem(position).getId();
        menu.setHeaderTitle("Выбирите дейстивие "+String.valueOf(id));
        menu.add(0,id,0,"Редактировать");
        menu.add(1,id,0,"Удалить");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.date_main_filter_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int id=item.getItemId();

        DialogInterface.OnClickListener dialofClickLisiner=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteWorkDay(id);
                        break;
                }
            }
        };
        if(item.getGroupId()==1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Удалить запись?").setPositiveButton("Да", dialofClickLisiner).setNegativeButton("Нет", dialofClickLisiner).show();
            return true;
        }else{
            ShowActivity(WorkDayLyaout.class,true,id);
        }
        return true;
    }

    public  void ShowActivity(Class name, boolean isEdit,int workDayId)
    {
        Intent intent =new Intent(MainActivity.this,name);
        intent.putExtra("isEdit",isEdit);
        intent.putExtra("WorkDayID",workDayId);
        startActivity(intent);
    }
    public void onMenuItem_Click(View view)
    {
        ShowActivity(UsersActivity.class, false,0);
    }

    public void deleteWorkDay(int work_id)
    {
        DAOFactory factory=DAOFactory.getDAOFactory(getString(R.string.database));
        WorkDayDAO workDayDAO=factory.getWorkDayDAO();
        workDayDAO.deleteWorkDay(work_id);
        loadWorkDays();
    }
    public void onFabAddClick(View view)
    {
        ShowActivity(WorkDayLyaout.class,false,0);
    }
}
