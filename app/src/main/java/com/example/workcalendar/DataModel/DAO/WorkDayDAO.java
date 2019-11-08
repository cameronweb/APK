package com.example.workcalendar.DataModel.DAO;

import com.example.workcalendar.DataModel.Entity.WorkDay;

import java.util.ArrayList;
import java.util.Date;

public interface WorkDayDAO {
    public void insertWorkDay(WorkDay workDay);
    public void updateWorkDay(WorkDay workDay);
    public int deleteWorkDay(int id);
    public WorkDay getWorkDay(int id);
    public ArrayList<WorkDay> getWorkDays();

    public ArrayList<WorkDay> getByUser(int userId, Boolean sorted);

    public ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date afterDate);
    public ArrayList<WorkDay> getFuterWorkDays();
    public boolean checkExists(Date date);
}
