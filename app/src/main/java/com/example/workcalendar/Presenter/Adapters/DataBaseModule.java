package com.example.workcalendar.Presenter.Adapters;



import com.example.workcalendar.DataModel.DAO.DB;
import com.example.workcalendar.DataModel.DataModel;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.Presenter.Notify.Notify;
import com.example.workcalendar.Presenter.Presenter;
import com.example.workcalendar.DataModel.SQLLite.DataBase;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;

@Module
public class DataBaseModule {
    @Provides
    public DataBase getDataBase() {
        return new DataBase(GlobalApplication.getAppContext());
    }

    @Provides
    public ArrayList<WorkDay> GetListWorkDays() {
        return new ArrayList<WorkDay>();
    }

    @Singleton
    @Provides
    public Presenter getPresenter() {
        return new Presenter();
    }

    @Singleton
    @Provides
    public DataModel getDataModel() {
        return new DB();
    }

    @Provides
    public Notify getNotify() {
        return new Notify();
    }

}
