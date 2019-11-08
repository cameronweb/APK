package com.example.workcalendar.Presenter.Notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.NotifyViewActivity;
import com.example.workcalendar.R;

import javax.inject.Inject;

public class NotifyService extends BroadcastReceiver {
    private static final int NOTIFY_ID=31337;
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        String message_date=intent.getStringExtra("message_date");
        this.context=context;
        sendNotify(message_date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotify(String work_date)
    {
        String message=context.getString(R.string.notify_messge_text);
        String message_text=String.format(message,work_date);
        Intent intent=new Intent(context, NotifyViewActivity.class);
        intent.putExtra("workdate",work_date);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManagerCompat notificationManager=NotificationManagerCompat.from(context);
        String id = "WorkCalendar_01";

// The user-visible name of the channel.
        CharSequence name = "WorkCalendar";

// The user-visible description of the channel.
        String description = "Calendar for working";

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

            NotificationChannel mChannel=notificationManager.getNotificationChannel(id);
            if(mChannel==null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mChannel.enableLights(true);

                AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).
                        setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build();
                mChannel.setSound(alarmSound, attributes);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
// Configure the notification channel.


        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,id);

        builder.setFullScreenIntent(pendingIntent,true).
                setContentTitle(context.getString(R.string.notify_message_title)).
                setContentText(message_text).
                setCategory(Notification.CATEGORY_MESSAGE).
                setSmallIcon(R.drawable.baseline_calendar_today_black_18).
                setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.baseline_event_black_36)).
                setTicker(context.getString(R.string.notify_message_ticker)).
                setWhen(System.currentTimeMillis()).
                setAutoCancel(true).
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationManager.notify(0,builder.build());
        GlobalApplication.workComponent().getNotify().start(false);
    }

}
