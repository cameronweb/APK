package com.example.workcalendar.Notify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.workcalendar.DAO.DB;
import com.example.workcalendar.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;

public class Notify  {
    private  Intent notifyBroadcastIntent;
    private  PendingIntent broadcastPendingIntent;
    private  Context context;
    private  Date workDateForNotify; //Дата дежурства без времени
    private  ArrayList<WorkDay> userWorkDays;
    private  int currentDatePosition =0; //индекс в массиве дежурств

    public void start(boolean update)
    {
        context= GlobalApplication.getAppContext();
        notifyBroadcastIntent =new Intent(context,NotifyService.class);
        if (!isRunnigNotify()) { //проверка есть ли уже запущеный аларм менеджер если нет создаст
            broadcastPendingIntent = PendingIntent.getBroadcast(context, 0, notifyBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            init();
        }else if (update) //Аларм менеджер запущен проверяем надо ли его обновить
        {
            broadcastPendingIntent =PendingIntent.getBroadcast(context,0, notifyBroadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            init();
        }
    }
    public void cencel()
    {
        context= GlobalApplication.getAppContext();
        notifyBroadcastIntent =new Intent(context,NotifyService.class);
        broadcastPendingIntent = PendingIntent.getBroadcast(context, 0, notifyBroadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(broadcastPendingIntent);
    }

    private void init(){
        Date startDateTime=getNotifyDateTime();//Дата дежурства с временем
        //Log.d("WorkCalendar","Дата и время запуска "+ startDateTime.toString());
        if(startDateTime!=null &  checkDateTimeToStart(startDateTime)) {

            startNotify(startDateTime, broadcastPendingIntent);
        }
        else{
            currentDatePosition +=1;
            if (userWorkDays.size()>= currentDatePosition +1) { //Проверка есть ли еще одна дата дежурства если первая уже не подходит например время уже больше чем заплонирована
                workDateForNotify = userWorkDays.get(currentDatePosition).get_day();
                startDateTime =new Date(getStartNotifyTime(workDateForNotify));
                if(checkDateTimeToStart(startDateTime))
                {
                    startNotify(startDateTime, broadcastPendingIntent);
                }
            }
        }
    }
    private boolean isRunnigNotify()
    {
        return (PendingIntent.getBroadcast(context,0, notifyBroadcastIntent,PendingIntent.FLAG_NO_CREATE)!=null);
    }

    private Date getNotifyDateTime() //выдает время и дату для нотификации
    {
        int user_id=DB.getNotifyUser();
        ArrayList<WorkDay> nextWorkDays=DB.getUserNextWorkDay(user_id,new Date(System.currentTimeMillis()));
        if(nextWorkDays.size()>0) {
            userWorkDays=nextWorkDays;
            /*Comparator<WorkDay> workDayComparatorByDate= new Comparator<WorkDay>() {
                @Override
                public int compare(WorkDay o1, WorkDay o2) {
                    return o1.get_day().compareTo(o2.get_day());
                }
            };
            Collections.sort(nextWorkDays,workDayComparatorByDate);*/

            workDateForNotify = nextWorkDays.get(currentDatePosition).get_day();
            long startNotifyTime = getStartNotifyTime(workDateForNotify);
            return new Date(startNotifyTime);
        }else{
            return null;
        }
    }

    private long getStartNotifyTime(Date work_date) //вохвращает дату и время для уведомления в милисекундах
    {
        Timestamp timestamp= DB.getSettingsNotifyTime();
        int hours=timestamp.getHours();
        int minuts=timestamp.getMinutes();
        Date date=new Date(work_date.getTime());
        date.setHours(hours);
        date.setMinutes(minuts);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(Calendar.DAY_OF_MONTH,-1);
        return calendar.getTimeInMillis();
    }
    private boolean checkDateTimeToStart(Date work_date)
    {
        long currentTime=System.currentTimeMillis();
        long notifyTime=work_date.getTime();
        return currentTime<notifyTime ? true : false;

    }

    private void startNotify(Date startDate, PendingIntent notifyPendingIntent)
    {
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        notifyBroadcastIntent.putExtra("message_date",format.format(workDateForNotify));
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,startDate.getTime(),notifyPendingIntent);
    }


    public void checkWorkDayForUserNotify(WorkDay newWorkDay) //проверка добавленого дежурства для текущего пользователя. если пользователи совподают то запускаем уведомление
    {
        int user_id=DB.getNotifyUser();
        boolean isNotify=DB.isSettingNotify();
        if (user_id==newWorkDay.getUserId() & isNotify){
            start(true);
        }

    }

}