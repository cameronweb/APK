package com.example.workcalendar;
import android.app.Application;
import android.content.Context;
import com.example.workcalendar.Presenter.Adapters.DataBaseModule;
import com.example.workcalendar.Presenter.Components.DaggerWorkComponent;
import com.example.workcalendar.Presenter.Notify.Notify;

import com.example.workcalendar.Presenter.Components.WorkComponent;
import com.example.workcalendar.Presenter.Presenter;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalApplication extends Application {

    private static Context appContext;
    private static WorkComponent component;
    private static Presenter presenter;
    private static boolean wifiConnected;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        component= DaggerWorkComponent.builder().dataBaseModule(new DataBaseModule()).build();
         presenter=component.getPresenter();
         component.injectPresenter(presenter);
        if (presenter.isSettingNotify()) {
            Notify notify = new Notify();
            notify.start(false);
        }



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
    public static Presenter getPresenter()
    {
        return presenter;
    }

    public static boolean isWifiConnected() {
        return wifiConnected;
    }

    public static void setWifiConnected(boolean wifiConnected) {
        GlobalApplication.wifiConnected = wifiConnected;

    }
}
