package com.example.workcalendar.DataModel.REST;

import android.util.Log;
import com.example.workcalendar.DataModel.Entity.Users;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class RestServiceTest {
    Users users;
    @Before
    public void init()
    {
        users=new Users();
        users.setId(3);
        users.setName("Федоров Анатолий Юрьевич");
        users.setPhone("+79273978402");
        users.setServerID("064673d5-30b5-4bff-8f6e-60d87f83f09d");
        TimeZone tz= TimeZone.getTimeZone("Europe/Moscow");
        Calendar cal = Calendar.getInstance(tz);
        cal.set(1981,8,24);
        users.setDateLong(cal.getTimeInMillis());
    }
    @Test
    public void sendUser() {
    }

    @Test
    public void insertUser() throws IOException {

    }
    @Test
    public void updateUser() throws  IOException{
        RestService restService=new RestService();
        String result= restService.updateUser(users);
        Log.d("WorkCalendar",result);
    }
    @Test
    public void receiveUser() {
    }

    @Test
    public void startOnDataChange() {
    }

    @Test
    public void stopOnDataChange() {
    }

    @Test
    public void sendWorkDate() {
    }

    @Test
    public void receiveWorkDate() {
    }

    @Test
    public void removeUser() {
    }

    @Test
    public void removeWorkDate() {
    }

    @Test
    public void goOnLine() {
    }

    @Test
    public void goOffLine() {
    }
}