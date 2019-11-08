package com.example.workcalendar.DataModel.FireBase;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.workcalendar.DataModel.DAO.ServerDAO;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;
import com.google.firebase.database.*;

import java.io.IOException;

public class FireBaseServer implements ServerDAO {
    private DatabaseReference usersRef;
    private DatabaseReference workDatesRef;
    private ChildEventListener usersListener, workDateListener;
    private long userExistsId=0;

    public FireBaseServer() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        usersRef= FirebaseDatabase.getInstance().getReference("users");
        workDatesRef=FirebaseDatabase.getInstance().getReference("workdays");

        if(GlobalApplication.getPresenter().isWyfiSync())
        {
            if(GlobalApplication.isWifiConnected()) FirebaseDatabase.getInstance().goOnline();
            else FirebaseDatabase.getInstance().goOffline();
        }else
        {
           FirebaseDatabase.getInstance().goOnline();
        }
        startOnDataChange();
    }


    @Override
    public void sendUser(Users user) throws IOException {

    }

    @Override
    public void receiveUser(Users user) throws IOException {

    }

    @Override
    public void startOnDataChange() {

    }

    @Override
    public void stopOnDataChange() {

    }

    @Override
    public void sendWorkDate(WorkDay workDay) throws IOException {

    }

    @Override
    public WorkDay receiveWorkDate(String serverID) throws IOException {
        return null;
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
