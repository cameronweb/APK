package com.example.workcalendar.DAO;

import com.example.workcalendar.SharedPreference.SHPreferenceSettings;

public class SHPrefereceFactory extends DAOSettingFactory {
    @Override
    public SettingsDAO getSettinsDAO() {
        return new SHPreferenceSettings();
    }
}
