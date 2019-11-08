package com.example.workcalendar.DataModel.FireBase;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.GlobalApplication;
import com.google.firebase.database.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FireBaseServerTest {
    String value="Данные клиента";
    String keys="";

    @Test
    public void sendUser() throws InterruptedException, ParseException {
        CountDownLatch count=new CountDownLatch(1);

        count.await(10, TimeUnit.SECONDS);
        Assert.assertEquals(value,"Данные клиента,");
    }

    @Test
    public void checkUserId() throws InterruptedException {


    }

    @Test
    public void receiveUser() {
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
}