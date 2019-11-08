package com.example.workcalendar.DataModel.DAO;

import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;

public interface ServerDAO {
    void sendUser(Users user) throws IOException;//Запись в базу сервера
    void receiveUser(Users user) throws IOException;// Запись в локальную базу полученого пользователя с сервера
    void startOnDataChange();
    void stopOnDataChange();
    void sendWorkDate(WorkDay workDay) throws IOException;
    WorkDay receiveWorkDate(String serverID) throws IOException;
    void removeUser(int userId);
    void removeWorkDate(int workDateId);
    void goOnLine();
    void goOffLine();

}