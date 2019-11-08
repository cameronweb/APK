package com.example.workcalendar.DataModel.DAO;

import com.example.workcalendar.DataModel.DataModel;
import com.example.workcalendar.DataModel.FireBase.FireBaseServer;


public abstract class DAOServerFactory {
    private static final String DataBase="Firebase";
    public abstract ServerDAO getServerDAO();
    public static ServerDAO getServer(String dbName)
    {
        if (DataBase.equals(dbName))
        {
            return new FireBaseServer();
        }else return null;
    }
}
