package com.example.workcalendar.Entity;


import android.support.annotation.Size;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;


public class Users {
    private int _id;
    @NotNull
    private String name;
    @Size(min = 7,max = 12) @Pattern("[+0-9]")
    private String _phone;
    private Date _date_of_birth;
    private ArrayList<WorkDay> _workdays;
    @Inject
    public Users() {
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String _phone) {
        this._phone = _phone;
    }

    public Date getDateOfBirth() {
        return _date_of_birth;
    }

    public void setDateOfBirth(Date _date_of_birth) {
        this._date_of_birth = _date_of_birth;
    }

    public ArrayList<WorkDay> get_workdays() {
        return _workdays;
    }
}
