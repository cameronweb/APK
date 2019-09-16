package com.example.workcalendar.Service.SQLLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

public class DataBase extends SQLiteOpenHelper {
    public static final String db_name = "WorkCalendarDB";
    public static final int db_ver = 1;
    public static final String WorkTable = "WORKDAYS";
    public static final String UsersTable = "USERSTABLE";
    public static final String JournalUsr = "JOURNAKUSR";
    public static final String JournalWrd = "JOURNAKWRD";
    public DataBase(Context context) {
        super(context, db_name, null, db_ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userQuery = String.format("CREATE TABLE %s (USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PHONE TEXT,BIRTHDAY NUMERIC)", UsersTable);
        db.execSQL(userQuery);
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,DAY NUMERIC,SATURDAY NUMERIC, USERID INTEGER)", WorkTable);
        db.execSQL(query);
        String journalUsers = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIELDNAME TEXT, TEXT OLDVALUE, NEWVALUE TEXT, CHANGEDATE NUMERIC, USERID INTEGER, CHANGETYPE TEXT)", JournalUsr);
        db.execSQL(journalUsers);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
