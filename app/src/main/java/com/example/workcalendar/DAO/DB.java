package com.example.workcalendar.DAO;

import com.example.workcalendar.Entity.Users;
import com.example.workcalendar.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.R;
import com.example.workcalendar.Settings;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB {
    private static WorkDayDAO getWorkDayDAO() {
        DAOFactory factory = DAOFactory.getDAOFactory(GlobalApplication.getAppContext().getString(R.string.database));
        return factory.getWorkDayDAO();
    }
    private static UsersDAO getUsersDAO() {
        DAOFactory factory = DAOFactory.getDAOFactory(GlobalApplication.getAppContext().getString(R.string.database));
        return factory.getUserDAO();
    }
    private static SettingsDAO getSettingsDAO() {
        DAOSettingFactory factory = DAOSettingFactory.getSettingsFactory(GlobalApplication.getAppContext().getString(R.string.settingsDB));
        return factory.getSettinsDAO();
    }
    public static WorkDay getWorkDay(int workId)
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getWorkDay(workId);
    }
    public static ArrayList<Users> getUsers()
    {
        UsersDAO usersDAO = getUsersDAO();
        return usersDAO.getUsers();
    }
     public static void updateUser(Users users)
     {
         getUsersDAO().updateUser(users);
     }
    public static void insertUser(Users users)
    {
        getUsersDAO().insertUser(users);
    }
    public static Users getUser(int userId)
    {
        UsersDAO usersDAO = getUsersDAO();
        return usersDAO.getUser(userId);
    }
    public static boolean setSettingNotify(boolean checked)
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.setNotify(checked);
    }


    public static boolean isSettingNotify()
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getNotify();
    }
    public static Timestamp getSettingsNotifyTime()
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getNotifyTime();
    }
    public static void setSettingsNotifyTime(String time)
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        settingsDAO.setNotifyTime(time);
    }
    public static int getNotifyUser()
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getUserId();
    }
    public static void setNotifyUser(int user_id)
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        settingsDAO.setUserId(user_id);
    }
    public static ArrayList<WorkDay> getWorkDays()
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getWorkDays();
    }



    public static ArrayList<WorkDay> getFuterWorkDays()
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getFuterWorkDays();
    }
    public static ArrayList<WorkDay> getWorkDaysByUser(int user_id)
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getByUser(user_id,false);
    }
    public static ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date afterDate)
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getUserNextWorkDay(user_id,afterDate);
    }
}
