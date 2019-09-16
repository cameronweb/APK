package com.example.workcalendar.Service.SQLLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.workcalendar.DAO.UsersDAO;
import com.example.workcalendar.Entity.Users;
import com.example.workcalendar.GlobalApplication;

import java.util.ArrayList;
import java.util.Date;

public class SQLLite_Users implements UsersDAO {
    @Override
    public Users getUser(int id) {
        Context context= GlobalApplication.getAppContext();
        DataBase dataBase=new DataBase(context);
        SQLiteDatabase db=dataBase.getReadableDatabase();
        Users user=GlobalApplication.workComponent().getUser();
        Cursor cursor=db.query(DataBase.UsersTable,new String[]{"USERID","NAME","PHONE","BIRTHDAY"},"USERID=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor.moveToFirst()) {
            user.setId(id);
            user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
            long _date = cursor.getLong(cursor.getColumnIndex("BIRTHDAY"));
            user.setDateOfBirth(new Date((_date)));
        }
        cursor.close();
        db.close();
        return  user;
    }



    @Override
    public ArrayList<Users> getUsers() {
        DataBase dataBase=GlobalApplication.workComponent().getDataBase();
        ArrayList<Users> _users = new ArrayList<Users>();
        SQLiteDatabase db=dataBase.getReadableDatabase();
        Cursor cursor=db.query(DataBase.UsersTable,new String[]{"USERID","NAME","PHONE","BIRTHDAY"},null,null,null,null,null);
        while(cursor.moveToNext())
        {
            Users user=GlobalApplication.workComponent().getUser();
            user.setId(cursor.getInt(cursor.getColumnIndex("USERID")));
            user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
            long _date = cursor.getLong(cursor.getColumnIndex("BIRTHDAY"));
            user.setDateOfBirth(new Date((_date)));
            _users.add(user);

        }
        cursor.close();
        db.close();
        return _users;
    }

    @Override
    public void insertUser(Users user) {
        Context context =GlobalApplication.getAppContext();
        DataBase dataBase=new DataBase(context);
        SQLiteDatabase db=dataBase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("NAME",user.getName());
        values.put("PHONE",user.getPhone());
        values.put("BIRTHDAY",user.getDateOfBirth().getTime());
        db.insert(DataBase.UsersTable,null,values);
        db.close();
    }

    @Override
    public void DeleteUser(int userId) {
     Context context =GlobalApplication.getAppContext();
     DataBase dataBase=new DataBase(context);
     SQLiteDatabase db=dataBase.getWritableDatabase();
     db.delete(DataBase.UsersTable,"USERID=?",new String[]{String.valueOf(userId)});
     db.close();
    }

    @Override
    public void updateUser(Users user) {
        Context context =GlobalApplication.getAppContext();
        DataBase dataBase=new DataBase(context);
        SQLiteDatabase db=dataBase.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("NAME",user.getName());
        values.put("PHONE",user.getPhone());
        values.put("BIRTHDAY",user.getDateOfBirth().getTime());
        db.update(DataBase.UsersTable,values,"USERID=?",new String[]{String.valueOf(user.getId())});
        db.close();
    }

}
