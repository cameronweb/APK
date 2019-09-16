package com.example.workcalendar.DAO;

public abstract class DAOSettingFactory {
    private static final String DATABASE="SharedPreference";

    public abstract SettingsDAO getSettinsDAO();
    public static DAOSettingFactory getSettingsFactory(String db_name)
    {
        if (DATABASE.contentEquals(db_name))
        {
            return new SHPrefereceFactory();
        }else
        {
            return null;
        }
    }
}
