package com.example.workcalendar.Presenter.Notify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.workcalendar.GlobalApplication;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class NotifyTest {
    PendingIntent notifyPending;
    Context context;

    @Before
    public void init()
    {
        context=GlobalApplication.getAppContext();
        Intent intent=new Intent(context,NotifyService.class);
        intent.putExtra("message_date","22.11.2019");
        notifyPending=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Test
    public void start() throws InterruptedException {
        CountDownLatch count=new CountDownLatch(1);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+5000,notifyPending);
        count.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void cencel() {
    }

    @Test
    public void checkWorkDayForUserNotify() {
    }
}