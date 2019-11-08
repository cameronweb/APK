package com.example.workcalendar.DataModel.DAO;

import com.example.workcalendar.DataModel.SharedPreference.SHPreferenceSettings;
public class SHPrefereceFactory extends DAOSettingFactory {
    @Override
    public SettingsDAO getSettinsDAO() {
        return new SHPreferenceSettings();
    }
}
