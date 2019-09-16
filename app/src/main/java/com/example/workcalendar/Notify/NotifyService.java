package com.example.workcalendar.Notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.system.Os;
import android.util.Log;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.NotifyViewActivity;
import com.example.workcalendar.R;

public class NotifyService extends BroadcastReceiver {
    private static final int NOTIFY_ID=31337;
    @Override
    public void onReceive(Context context, Intent intent) {

        String message_date=intent.getExtras().getString("message_date");
        sendNotify(message_date);

    }

    public void sendNotify(String work_date)
    {
        Context context=GlobalApplication.getAppContext();
        String message=context.getString(R.string.notify_messge_text);
        String message_text=String.format(message,work_date);
        Intent intent=new Intent(context, NotifyViewActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder=new Notification.Builder(context);
        builder.setContentIntent(pendingIntent).
                setContentTitle(context.getString(R.string.notify_message_title)).
                setContentText(message_text).
                setSmallIcon(R.drawable.baseline_calendar_today_black_18).
                setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.baseline_event_black_36)).
                setTicker(context.getString(R.string.notify_message_ticker)).
                setWhen(System.currentTimeMillis()).
                setAutoCancel(false);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID,builder.build());
    }

}
