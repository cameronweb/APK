package com.example.workcalendar.Components;

import android.content.Context;
import com.example.workcalendar.Adapters.DataBaseModule;
import com.example.workcalendar.Entity.Users;
import com.example.workcalendar.Entity.WorkDay;
import com.example.workcalendar.Service.SQLLite.DataBase;
import dagger.Component;
import org.xml.sax.ContentHandler;

import javax.inject.Singleton;

@Singleton
@Component(modules = DataBaseModule.class)
public interface WorkComponent {
    Users getUser();
    WorkDay getWorkDay();
    DataBase getDataBase();
}
