package com.example.workcalendar;

import ServerBLL.FireBaseClient;
import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import com.example.workcalendar.Presenter.Notify.NotifyService;
import com.example.workcalendar.Presenter.Notify.WIFIStateService;
import com.example.workcalendar.Presenter.Presenter;
import com.example.workcalendar.ViewModel.UsersActivity;
import com.example.workcalendar.ViewModel.WorkDayLyaout;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.workcalendar.Presenter.Adapters.Wokday_adapter;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;
    ListView list_view;
    Presenter presenter;
    WIFIStateService wifiStateService;
    private Disposable subscription;
    @Inject
    public ArrayList<WorkDay> workDayArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalApplication.workComponent().injectMain(this);
        init();
        loadWorkDays();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.dispose();
        unregisterReceiver(wifiStateService);
    }

    public  void loadWorkDays()
    {
        subscription=GlobalApplication.getPresenter().loadWorkDaysToObservable().subscribe((workDays)->{
            Wokday_adapter adapter=new Wokday_adapter(GlobalApplication.getAppContext(),workDays);
            list_view.setAdapter(adapter);
        });
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
                mDrawer.openDrawer(GravityCompat.START);
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
        //workDayArrayList=new ArrayList<WorkDay>();
        checkPermission();
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
            case R.id.menu_exit:
                applacationExit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void applacationExit()
    {

        finishAndRemoveTask();
        System.exit(0);
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
        GlobalApplication.getPresenter().deleteWorkDay(work_id);
        loadWorkDays();
    }
    public void onFabAddClick(View view)
    {
        ShowActivity(WorkDayLyaout.class,false,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==31337)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                registerWifiStateReciever();
            }
        }
    }
    public void registerWifiStateReciever()
    {
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        wifiStateService=new WIFIStateService();
        registerReceiver(wifiStateService, filter);
    }

    public void checkPermission()
    {
        int permission= ContextCompat.checkSelfPermission(GlobalApplication.getAppContext(), Manifest.permission.ACCESS_NETWORK_STATE);
        if(permission== PackageManager.PERMISSION_GRANTED)
        {
          registerWifiStateReciever();
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.CHANGE_WIFI_STATE},31337);
        }
    }



}
