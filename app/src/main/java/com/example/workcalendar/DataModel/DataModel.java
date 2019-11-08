package com.example.workcalendar.DataModel;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public interface DataModel {
    WorkDay getWorkDay(int workId);

    ArrayList<Users> getUsers();

    void updateUser(Users users);

    void insertUser(Users users);

    void deleteUser(int userId);

    Users getUser(int userId);

    boolean setSettingNotify(boolean checked);

    boolean isSettingNotify();

    boolean setWifiSync(boolean checked);

    boolean isWifiSync();

    Timestamp getSettingsNotifyTime();

    void setSettingsNotifyTime(String time);

    int getNotifyUser();

    void setNotifyUser(int user_id);

    ArrayList<WorkDay> getWorkDays();

    ArrayList<WorkDay> getFuterWorkDays();

    ArrayList<WorkDay> getWorkDaysByUser(int user_id);

    ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date afterDate);

    void deleteWorkDay(int wotkDayId);

    void insertWorkDay(WorkDay workDay);

    void updateWorkDay(WorkDay workDay);

    boolean checkExists(Date date);
}