package com.example.workcalendar.Entity;

import android.app.usage.UsageEvents;
import android.support.annotation.NonNull;
import com.example.workcalendar.Entity.Users;

import javax.inject.Inject;
import java.util.Date;

public class WorkDay {
    private int _id;

    private Date _day;
    private boolean _saturday;
    private int _user_id;
    private Users _user;
    @Inject
    public WorkDay() {
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Date get_day() {
        return _day;
    }

    public void setDay(@NonNull  Date _day) {
        this._day = _day;
    }

    public boolean isSaturday() {
        return _saturday;
    }

    public void setSaturday(boolean _saturday) {
        this._saturday = _saturday;
    }

    public int getUserId() {
        return _user_id;
    }

    public void setUserId(int _user_id) {
        this._user_id = _user_id;
    }

    public Users getUser() {
        return _user;
    }

    public void setUser(Users _user) {
        this._user = _user;
    }
    public static enum DayOfWeeks{
        Воскресенье, Понедельник, Вторник, Среда, Четверг,Пятница, Суббота
    }
    public  static enum WeekEnd{
        Будни, Выходной
    }
}
