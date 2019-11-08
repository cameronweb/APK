package com.example.workcalendar.DataModel.Logs;

import android.content.ContentValues;
import com.example.workcalendar.DataModel.SQLLite.DataBase;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class Users_Journal {
    private DataBase db;
    private  static final String JournalUsr="JOURNAKUSR";
    public  Users_Journal(DataBase dataBase)
    {
        db=dataBase;
    }



    public void AddToJournal(JUser jUser)
    {
        SQLiteDatabase database =db.getWritableDatabase();
        database.beginTransaction();
        for (UserFields field: jUser.getUserFields())
        {
            Date date=new Date();
            ContentValues values=new ContentValues();
            values.put("FILEDNAME",String.valueOf(field.getFieldName()));
            values.put("OLDVALUE",field.getField_old_value());
            values.put("NEWVALUE",field.getField_new_value());
            values.put("USERID",jUser.getUsr_id());
            values.put("CHANGEDATE",date.getTime());
            values.put("CHANGETYPE",String.valueOf(jUser.getChange_type()));
            database.insert(JournalUsr,null,values);
        }
        database.endTransaction();

    }

}
