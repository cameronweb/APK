package com.example.workcalendar.DataModel.REST;

import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestServiceTest {
    private static final String serverURL="http://localhost:8080/WebCalendar_war/";
    Users users;
    WorkDay workDay;
    @Before
    public void init() throws IOException, ParseException {
        String url=serverURL+"users/064673d5-30b5-4bff-8f6e-60d87f83f09d";
        URL servUrl=new URL(url);
        HttpURLConnection httpURLConnection=(HttpURLConnection) servUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        String result="";
        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            result+=new String(bytes);
            inputStream.close();
        }
        httpURLConnection.disconnect();
        System.out.println(result);
        Gson gson=new Gson();
        users=gson.fromJson(result,Users.class);
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        Date date=format.parse("24.11.2019");
        workDay=new WorkDay();
        workDay.setWorkdate(date.getTime());
        workDay.setSaturday(true);
        workDay.setUserId(users.getId());
        workDay.setUser(users);
        workDay.setServerId("f69b36c9-125c-45a2-8e65-caebed976");
    }

    @Test
    public void sendUser() {
    }

    @Test
    public void insertUser() throws IOException {

        Gson Json= new Gson();
        String jsonUser= Json.toJson(users);
        String url=serverURL+"users/"+users.getServerID();
        URL servUrl=new URL(url);
        HttpURLConnection httpURLConnection=(HttpURLConnection) servUrl.openConnection();
        System.out.println(jsonUser);
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        OutputStream os=httpURLConnection.getOutputStream();
        os.write(jsonUser.getBytes("UTF-8"),0,jsonUser.getBytes("UTF-8").length);
        os.flush();
        os.close();
        String result="Result is: ";
        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            result+=new String(bytes);
            inputStream.close();
        }
        httpURLConnection.disconnect();
        System.out.println(result);
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
    public void sendWorkDate() throws IOException {
        Gson Json= new Gson();
        String jsonWorkDay= Json.toJson(workDay);
        String url=serverURL+"workdays/"+workDay.getServerId();
        URL servUrl=new URL(url);
        HttpURLConnection httpURLConnection=(HttpURLConnection) servUrl.openConnection();
        System.out.println(jsonWorkDay);
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        OutputStream os=httpURLConnection.getOutputStream();
        os.write(jsonWorkDay.getBytes("UTF-8"),0,jsonWorkDay.getBytes("UTF-8").length);
        os.flush();
        os.close();
        String resultCode="";
        int responseCode=httpURLConnection.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            resultCode= new String(bytes);
            inputStream.close();
        }else {
            resultCode=String.valueOf(responseCode);
        }
        httpURLConnection.disconnect();
        System.out.println(resultCode);
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