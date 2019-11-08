package com.example.workcalendar.DataModel.Entity;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;

import javax.inject.Inject;
import java.util.Date;

public class WorkDay {
    private int id;
    private long workdate;
    private boolean saturday;
    private int userId;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    private String serverId;
    private Users user;
    @Inject
    public WorkDay() {
    }

    public long getWorkdate() {
        return workdate;
    }

    public void setWorkdate(long date) {
        this.workdate = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public Date getDate() {
        return new Date(workdate);
    }

    public void setDate(@NonNull  Date _day) {
        this.workdate = _day.getTime();
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean _saturday) {
        this.saturday = _saturday;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int _user_id) {
        this.userId = _user_id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users _user) {
        this.user = _user;
    }

}
