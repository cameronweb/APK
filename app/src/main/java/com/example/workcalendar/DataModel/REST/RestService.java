package com.example.workcalendar.DataModel.REST;

import android.util.Log;
import com.example.workcalendar.DataModel.DAO.ServerDAO;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.*;

public class RestService implements ServerDAO {
    private String serverURL;
    private String usersServerURL;
    private String workDaysServerURL;
    private static final String POST="POST";
    private static final String GET="GET";
    private static final String PUT="PUT";
    private static final String DELETE="DELETE";
    private static final String HTTP_OK="200";
    private static final String HTTP_NOT_FOUND="404";
    private static final String RESPONSE_CODE_EXIST="exist";
    private static final String RESPONSE_CODE_USER_NOT_FOUND="usernotfound";
    RestService(){
        serverURL=GlobalApplication.getAppContext().getResources().getString(R.string.serverURL);
        usersServerURL=GlobalApplication.getAppContext().getResources().getString(R.string.usersServerURL);
        workDaysServerURL=GlobalApplication.getAppContext().getResources().getString(R.string.workDaysServerURL);
    }

    @Override
    public void sendUser(Users user) throws IOException {
        if (!user.getServerID().isEmpty()){
            insertUser(user);
        }else {

        }

    }

    public String updateUser(Users users) throws IOException {
        String jsonUser=new Gson().toJson(users);
        String url=usersServerURL+"/"+users.getServerID();
        HttpURLConnection httpURLConnection=getConnection(url,PUT);
        httpURLConnection.setDoOutput(true);
        OutputStream outputStream=httpURLConnection.getOutputStream();
        byte[] bytes=jsonUser.getBytes("UTF-8");
        outputStream.write(bytes,0,bytes.length);
        outputStream.flush();
        outputStream.close();
        String result="";
        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            result=getStringFromStream(inputStream);
            inputStream.close();
        }
        httpURLConnection.disconnect();
        return result;
    }
    public String getStringFromStream(InputStream inputStream) throws IOException {
        byte[] bts = new byte[inputStream.available()];
        inputStream.read(bts);
        return new String(bts);
    }
    private String insertUser(Users user) throws IOException {
        Gson Json= new Gson();
        String jsonUser= Json.toJson(user);
        HttpURLConnection httpURLConnection=getConnection(usersServerURL,POST);
        httpURLConnection.setDoOutput(true);
        OutputStream os=httpURLConnection.getOutputStream();
        os.write(jsonUser.getBytes("UTF-8"),0,jsonUser.getBytes("UTF-8").length);
        os.flush();
        os.close();
        String result="";
        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            result=getStringFromStream(inputStream);
            inputStream.close();
        }
        httpURLConnection.disconnect();
        return  result;
    }
    private HttpURLConnection getConnection(String url, String httpMethod) throws IOException {
        URL servUrl=new URL(url);
        HttpURLConnection httpURLConnection=(HttpURLConnection) servUrl.openConnection();
        httpURLConnection.setRequestMethod(httpMethod);
        httpURLConnection.setRequestProperty("Content-Type", "application/juson; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        return  httpURLConnection;
    }

    @Override
    public void receiveUser(Users user) throws IOException {
        String url=usersServerURL+"/"+user.getServerID();
        HttpURLConnection httpURLConnection=getConnection(url,GET);
        String result="";
        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            result=getStringFromStream(inputStream);
            inputStream.close();
        }
        httpURLConnection.disconnect();
        Gson gson=new Gson();
        Users serverUser=gson.fromJson(result,Users.class);
    }

    @Override
    public void startOnDataChange() {

    }

    @Override
    public void stopOnDataChange() {

    }

    @Override
    public void sendWorkDate(WorkDay workDay) throws IOException {
        if(!workDay.getServerId().isEmpty()){
          String resposeCode= insertWorkDay(workDay);
          if (!resposeCode.isEmpty() && !resposeCode.contains(RESPONSE_CODE_EXIST) && !resposeCode.contains(RESPONSE_CODE_USER_NOT_FOUND))
          {
              workDay.setServerId(resposeCode);
              GlobalApplication.getPresenter().updateWorkDay(workDay);
          }
        }else {
            String httpStatus=updateWorkDay(workDay);
        }
    }

    private String insertWorkDay(WorkDay workDay) throws IOException {
        String jsonWorkDay= new Gson().toJson(workDay);
        String url=workDaysServerURL;
        HttpURLConnection httpURLConnection=getConnection(url,POST);
        httpURLConnection.setDoOutput(true);
        OutputStream os=httpURLConnection.getOutputStream();
        os.write(jsonWorkDay.getBytes("UTF-8"),0,jsonWorkDay.getBytes("UTF-8").length);
        os.flush();
        os.close();
        String result="";
        // int resposeCode=httpURLConnection.getResponseCode();
        InputStream inputStream=httpURLConnection.getInputStream();
        if (inputStream!=null) {
            result = getStringFromStream(inputStream);
            inputStream.close();
        }
        httpURLConnection.disconnect();
        return result;
    }
    private String updateWorkDay(WorkDay workDay) throws IOException {
        Gson Json= new Gson();
        String jsonWorkDay= Json.toJson(workDay);
        String url=serverURL+"workdays/"+workDay.getServerId();
        HttpURLConnection httpURLConnection=getConnection(url,PUT);
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
            resultCode= getStringFromStream(inputStream);
            inputStream.close();
        }else {
            resultCode=String.valueOf(responseCode);
        }
        httpURLConnection.disconnect();
        return resultCode;
    }

    @Override
    public WorkDay receiveWorkDate(String serverID) throws IOException {
        String url=workDaysServerURL+"/"+serverID;
        HttpURLConnection httpURLConnection=getConnection(url,GET);
        String result="";
        WorkDay workDay=null;
        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream inputStream=httpURLConnection.getInputStream();
            result=getStringFromStream(inputStream);
            inputStream.close();
            workDay=new Gson().fromJson(result,WorkDay.class);
        }
        httpURLConnection.disconnect();
        return workDay;
    }

    @Override
    public void removeUser(int userId) {

    }

    @Override
    public void removeWorkDate(int workDateId) {

    }

    @Override
    public void goOnLine() {

    }

    @Override
    public void goOffLine() {

    }
}
