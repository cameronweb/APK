package com.example.workcalendar.DAO;

import com.example.workcalendar.Entity.WorkDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface WorkDayDAO {
    public void insertWorkDay(WorkDay workDay);
    public void updateWorkDay(WorkDay workDay);
    public int deleteWorkDay(int id);
    public WorkDay getWorkDay(int id);
    public ArrayList<WorkDay> getWorkDays();

    public ArrayList<WorkDay> getByUser(int userId, Boolean sorted);

    public ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date afterDate);
    public ArrayList<WorkDay> getFuterWorkDays();
}
