package com.example.workcalendar.Adapters;

import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.Service.SQLLite.DataBase;
import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Provides
    public DataBase getDataBase()
    {
        return new DataBase(GlobalApplication.getAppContext());
    }
}
