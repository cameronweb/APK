package com.example.workcalendar.DataModel.Entity;


import javax.inject.Inject;
import java.util.Date;


public class Users {
    private int id;
    private String name;
    private String phone;
    private long dateLong;
    private String serverID;

    @Inject
    public Users() {
    }

    public void setDateLong(long dateLong) {
        this.dateLong = dateLong;
    }

    public long getDateLong() {
        return dateLong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return new Date(dateLong);
    }

    public void setBirthDate(Date date) {
        this.dateLong = date.getTime();
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

}
