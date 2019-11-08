package com.example.workcalendar.DataModel.DAO;

public abstract class DAOFactory {
    private static final String DataBase="SQLLite";

    public abstract UsersDAO getUserDAO();
    public abstract WorkDayDAO getWorkDayDAO();
    public static DAOFactory getDAOFactory(String db_name){
        if (DataBase.contentEquals(db_name))
        {
            return new SQLLiteDAOFactory();
        }else
        {
            return null;
        }
    }
}
