package com.example.workcalendar.DataModel.DAO;

import com.example.workcalendar.DataModel.DataModel;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class DB implements DataModel {
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
    @Override
    public  WorkDay getWorkDay(int workId)
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getWorkDay(workId);
    }
    @Override
    public  ArrayList<Users> getUsers()
    {
        UsersDAO usersDAO = getUsersDAO();
        return usersDAO.getUsers();
    }
    @Override
     public  void updateUser(Users users)
     {
         getUsersDAO().updateUser(users);
     }
    @Override
    public  void insertUser(Users users)
    {
        getUsersDAO().insertUser(users);
    }
    @Override
    public  Users getUser(int userId)
    {
        UsersDAO usersDAO = getUsersDAO();
        return usersDAO.getUser(userId);
    }
    @Override
    public  boolean setSettingNotify(boolean checked)
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.setNotify(checked);
    }

    @Override
    public  boolean isSettingNotify()
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getNotify();
    }

    @Override
    public boolean setWifiSync(boolean checked) {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.setWifiSync(checked);
    }

    @Override
    public boolean isWifiSync() {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getWifiSync();
    }

    @Override
    public  Timestamp getSettingsNotifyTime()
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getNotifyTime();
    }
    @Override
    public  void setSettingsNotifyTime(String time)
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        settingsDAO.setNotifyTime(time);
    }
    @Override
    public  int getNotifyUser()
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        return settingsDAO.getUserId();
    }
    @Override
    public  void setNotifyUser(int user_id)
    {
        SettingsDAO settingsDAO = getSettingsDAO();
        settingsDAO.setUserId(user_id);
    }
    @Override
    public  ArrayList<WorkDay> getWorkDays()
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getWorkDays();
    }

    public  ArrayList<WorkDay> getFuterWorkDays()
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getFuterWorkDays();
    }
    public  ArrayList<WorkDay> getWorkDaysByUser(int user_id)
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getByUser(user_id,false);
    }
    public  ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date afterDate)
    {
        WorkDayDAO workDayDAO = getWorkDayDAO();
        return workDayDAO.getUserNextWorkDay(user_id,afterDate);
    }

    @Override
    public void deleteUser(int userId) {
        getUsersDAO().deleteUser(userId);
    }

    @Override
    public void deleteWorkDay(int wotkDayId) {
        getWorkDayDAO().deleteWorkDay(wotkDayId);
    }

    @Override
    public void insertWorkDay(WorkDay workDay) {
        getWorkDayDAO().insertWorkDay(workDay);
    }

    @Override
    public void updateWorkDay(WorkDay workDay) {
        getWorkDayDAO().updateWorkDay(workDay);
    }

    @Override
    public boolean checkExists(Date date) {
        return getWorkDayDAO().checkExists(date);
    }
}
