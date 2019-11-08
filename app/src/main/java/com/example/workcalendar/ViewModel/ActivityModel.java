package com.example.workcalendar.ViewModel;

import com.example.workcalendar.DataModel.Entity.Users;

import java.util.ArrayList;
import java.util.List;

public interface ActivityModel {
    void loadList(ArrayList<Users> list);
    void load(Object oneObject);
    Object getData();
}

