package com.example.workcalendar.Presenter;


import android.widget.ListView;
import com.example.workcalendar.DataModel.DataModel;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.Presenter.AsyncTasks.AsyncTask_LoadWorDays;
import com.example.workcalendar.ViewModel.ActivityModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class Presenter {

    @Inject
    DataModel db;
    private ActivityModel activityModel;

    public boolean isSettingNotify()
    {
        return db.isSettingNotify();
    }
    public void setNotifyUser(int userId)
    {
        db.setNotifyUser(userId);
    }
    public void setSettingNotify(boolean isCheked)
    {
        db.setSettingNotify(isCheked);
    }
    public void setWifiSync(boolean isChecked)
    {
        db.setWifiSync(isChecked);
    }
    public boolean isWyfiSync()
    {
        return db.isWifiSync();
    }
    public void setSettingsNotifyTime(String time)
    {
        db.setSettingsNotifyTime(time);
    }
    public Timestamp getSettingsNotifyTime()
    {
        return db.getSettingsNotifyTime();
    }
    public void deleteWorkDay(int workDayId)
    {
        db.deleteWorkDay(workDayId);
    }
    public int getNotifyUser()
    {
        return db.getNotifyUser();
    }
    public ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date date)
    {
        return db.getUserNextWorkDay(user_id,date);
    }
    public  void loadWorkDaysAsynk(ListView destenationListView, ArrayList<WorkDay> destWorkDayList)
    {
        AsyncTask_LoadWorDays task_loadWorDays=new AsyncTask_LoadWorDays(destenationListView);
        task_loadWorDays.execute(destWorkDayList);
    }
    public void updateWorkDay(WorkDay workDay){
        db.updateWorkDay(workDay);
    }
    public void setActivityModel(ActivityModel activityModel)
    {
        this.activityModel=activityModel;
    }
    public  Observable<ArrayList<WorkDay>> loadWorkDaysToObservable(){
        Observable<ArrayList<WorkDay>> observable=Observable.fromCallable(new Callable<ArrayList<WorkDay>>() {
            @Override
            public ArrayList<WorkDay> call() throws Exception {
               return db.getFuterWorkDays();
            }
        }).subscribeOn(Schedulers.io());
        return  observable.observeOn(AndroidSchedulers.mainThread());
    }

    public List<WorkDay> loadFuterWorkDays()
    {
        return db.getFuterWorkDays();
    }
    public void loadWorkDay(int workDayId)
    {
         WorkDay workDay = db.getWorkDay(workDayId);
         activityModel.load(workDay);
    }
    public void acceptWorkDay()
    {
        WorkDay workDay=(WorkDay)activityModel.getData();
        if(workDay.getId()>0)
        {
            db.updateWorkDay(workDay);
        }else
        {db.insertWorkDay(workDay);}
        checkForNotifyStart(workDay.getUserId());
    }
    public void updateUser(Users user)
    {
        db.updateUser(user);
    }
    public void acceptUser()
    {
        Users user=(Users) activityModel.getData();
        if(user.getId()>0)
        {
            db.updateUser(user);
        }else
        {
            db.insertUser(user);
        }
    }

    public void deleteUser(int userId){
        db.deleteUser(userId);
    }

    public void loadUser(int userId)
    {
        Users user=db.getUser(userId);
        activityModel.load(user);
    }
    public void loadUsers()
    {
        ArrayList<Users> users=db.getUsers();
        activityModel.loadList(users);
    }
    private void checkForNotifyStart(int userId)
    {
        int notifyUser=db.getNotifyUser();
        if(notifyUser==userId)
        {
            GlobalApplication.workComponent().getNotify().start(false);
        }
    }
    public Users getUser(int userId)
    {
        return db.getUser(userId);
    }
}
