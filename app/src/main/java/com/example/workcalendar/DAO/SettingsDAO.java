package com.example.workcalendar.DAO;

import java.sql.Timestamp;

public interface SettingsDAO {
    int getUserId();
    void setUserId(int userId);
    boolean getNotify();
    boolean setNotify(boolean checked);
    String getServerURL();
    void setServerURL(String serverURL);
    Timestamp getNotifyTime();
    void setNotifyTime(String time);
}
