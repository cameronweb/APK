package com.example.workcalendar;
import android.app.Application;
import android.content.Context;
import com.example.workcalendar.Adapters.DataBaseModule;
import com.example.workcalendar.Components.DaggerWorkComponent;
import com.example.workcalendar.DAO.DB;
import com.example.workcalendar.Notify.Notify;

import com.example.workcalendar.Components.WorkComponent;

public class GlobalApplication extends Application {

    private static Context appContext;
    private static WorkComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        if (DB.isSettingNotify()) {
            Notify notify = new Notify();
            notify.start(false);
        }
     component= DaggerWorkComponent.builder().dataBaseModule(new DataBaseModule()).build();

        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }
    public static WorkComponent workComponent()
    {
        return component;
    }
    public static Context getAppContext() {
        return appContext;
    }
}
