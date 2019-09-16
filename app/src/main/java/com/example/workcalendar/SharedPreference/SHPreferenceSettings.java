package com.example.workcalendar.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.example.workcalendar.DAO.SettingsDAO;
import com.example.workcalendar.GlobalApplication;

import java.sql.Timestamp;

public class SHPreferenceSettings implements SettingsDAO {
    public static final String PREFERENCE_NAME="WorkDays_Settings";
    public static final String USERID="USERID";
    public static final String NOTIFY="NOTIFY";
    public static final String NOTIFY_TIME="NOTIFY_TIME_H";
    private Context context;
    private SharedPreferences sharedPreferences;
    public SHPreferenceSettings() {
        super();
        context=GlobalApplication.getAppContext();
        sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME,context.MODE_PRIVATE);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int getUserId() {
        return sharedPreferences.getInt(USERID,0);
    }

    @Override
    public void setUserId(int userId) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(USERID,userId);
        editor.commit();
    }

    @Override
    public boolean getNotify() {
        return sharedPreferences.getBoolean(NOTIFY,false);
    }

    @Override
    public boolean setNotify(boolean checked) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(NOTIFY,checked);
        return editor.commit();
    }

    @Override
    public String getServerURL() {
        return null;
    }

    @Override
    public void setServerURL(String serverURL) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Timestamp getNotifyTime() {

        String time=sharedPreferences.getString(NOTIFY_TIME,"1981-09-24 20:00:00");
        return Timestamp.valueOf(time);
    }

    @Override
    public void setNotifyTime(String time) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(NOTIFY_TIME,time);
        editor.commit();
    }
}
