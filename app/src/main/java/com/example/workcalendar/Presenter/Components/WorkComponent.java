package com.example.workcalendar.Presenter.Components;


import com.example.workcalendar.DataModel.DataModel;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.Presenter.Adapters.DataBaseModule;
import com.example.workcalendar.MainActivity;
import com.example.workcalendar.Presenter.Notify.Notify;
import com.example.workcalendar.Presenter.Presenter;
import com.example.workcalendar.DataModel.SQLLite.DataBase;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DataBaseModule.class)
public interface WorkComponent {
    Users getUser();
    WorkDay getWorkDay();
    DataBase getDataBase();
    Presenter getPresenter();
    Notify getNotify();
    DataModel getDataModel();
    void injectMain(MainActivity mainActivity);
    void injectPresenter(Presenter presenter);

}
