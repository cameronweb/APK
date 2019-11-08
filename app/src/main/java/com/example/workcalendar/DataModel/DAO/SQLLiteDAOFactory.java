package com.example.workcalendar.DataModel.DAO;


import com.example.workcalendar.DataModel.SQLLite.SQLLite_Users;
import com.example.workcalendar.DataModel.SQLLite.SQLLite_WorkDays;

public class SQLLiteDAOFactory extends DAOFactory {
    @Override
    public UsersDAO getUserDAO() {
        return new SQLLite_Users();
    }

    @Override
    public WorkDayDAO getWorkDayDAO() {
        return new SQLLite_WorkDays();
    }

}
