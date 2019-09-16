package com.example.workcalendar.Service.SQLLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.workcalendar.DAO.WorkDayDAO;
import com.example.workcalendar.Entity.Users;
import com.example.workcalendar.Entity.WorkDay;
import com.example.workcalendar.GlobalApplication;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class SQLLite_WorkDays implements WorkDayDAO {

    @Override
    public void insertWorkDay(WorkDay workDay) {
        Context context =GlobalApplication.getAppContext();
        DataBase dataBase =new DataBase(context);
        SQLiteDatabase db=dataBase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("DAY",workDay.get_day().getTime());
        values.put("SATURDAY",workDay.isSaturday());
        values.put("USERID",workDay.getUserId());
        db.insert(DataBase.WorkTable,null,values);
        db.close();
    }


    @Override
    public void updateWorkDay(WorkDay workDay) {
        Context context =GlobalApplication.getAppContext();
        DataBase dataBase =new DataBase(context);
        SQLiteDatabase db=dataBase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("DAY",workDay.get_day().getTime());
        values.put("SATURDAY",workDay.isSaturday());
        values.put("USERID",workDay.getUserId());
        db.update(DataBase.WorkTable,values,"ID=?",new String[]{String.valueOf(workDay.getId())});
        db.close();
    }

    @Override
    public int deleteWorkDay(int id) {
        Context context =GlobalApplication.getAppContext();
        DataBase dataBase =new DataBase(context);
        SQLiteDatabase db=dataBase.getWritableDatabase();
        int delete = db.delete(DataBase.WorkTable, "ID=?", new String[]{String.valueOf(id)});
        db.close();
        return delete;
    }

    @Override
    public WorkDay getWorkDay(int id) {

        Context context = GlobalApplication.getAppContext();
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase db=dataBase.getReadableDatabase();
        Cursor cursor = db.query(DataBase.WorkTable, new String[]{"ID", "DAY", "SATURDAY", "USERID"}, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        WorkDay day=new WorkDay();
        if(cursor.moveToFirst())
        {
            day.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            long _date = cursor.getLong(cursor.getColumnIndex("DAY"));
            day.setDay(new Date(_date));
            day.setSaturday(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("SATURDAY"))));
            int user_id=cursor.getInt(cursor.getColumnIndex("USERID"));
            day.setUserId(user_id);
            day.setUser(getUser(db,user_id));
        }
        dataBase.close();
        return day;
    }

    @Override
    public ArrayList<WorkDay> getWorkDays()
    {
       return _getWorkDays(0,false,false);
    }

    @Override
    public ArrayList<WorkDay> getFuterWorkDays() {
        return _getWorkDays(0,false,true);
    }

    private ArrayList<WorkDay> _getWorkDays(int user_id, boolean byUser, Boolean sortedCurrentDate) {
        Context context = GlobalApplication.getAppContext();
        DataBase dataBase = new DataBase(context);
        ArrayList<WorkDay> workDays = new ArrayList<WorkDay>();
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor cursor;
        String selections="";
        ArrayList<String> selectionsArgs=new ArrayList<String>();
        if (byUser)
        {
            selections="USERID=?";
            selectionsArgs.add(String.valueOf(user_id));
        }
        if(sortedCurrentDate)
        {
            if (!selections.isEmpty()) {
                selections += "and DAY>=?";
            }else
            {
                selections = "DAY>=?";
            }
            Timestamp currendDay=new Timestamp(System.currentTimeMillis());
            currendDay.setHours(0);
            currendDay.setMinutes(0);
            currendDay.setSeconds(0);
            selectionsArgs.add(String.valueOf(currendDay.getTime()));
        }
         String[] args=new String[selectionsArgs.size()];
         args=selectionsArgs.toArray(args);
         cursor = db.query(DataBase.WorkTable, new String[]{"ID", "DAY", "SATURDAY", "USERID"}, selections, args, null, null, "DAY");

        while (cursor.moveToNext()) {
            WorkDay _day = new WorkDay();
            _day.setUserId(cursor.getInt(cursor.getColumnIndex("USERID")));
            _day.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            Date date = new Date(cursor.getLong(cursor.getColumnIndex("DAY")));
            _day.setDay(date);
            int is_saturday=(cursor.getInt(cursor.getColumnIndex("SATURDAY")));
            _day.setSaturday(intToBool(is_saturday));
            _day.setUser(getUser(db, _day.getUserId()));
            workDays.add(_day);
        }
        dataBase.close();
        return  workDays;
    }
    private boolean intToBool(int value)
    {
        if (value==1)
        {
            return true;
        }else
        {
            return false;

        }
    }

  private Users getUser(SQLiteDatabase db, int user_id)
  {
      Cursor cursor=db.query(DataBase.UsersTable,new String[]{"USERID","NAME","PHONE","BIRTHDAY"},"USERID=?",new String[]{String.valueOf(user_id)},null,null,null);
      Users user=new Users();
      if (cursor.moveToFirst()) {
          user.setId(user_id);
          user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
          user.setPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
          long _date = cursor.getLong(cursor.getColumnIndex("BIRTHDAY"));
          user.setDateOfBirth(new Date((_date)));
      }
      cursor.close();
      return  user;
  }

    @Override
    public ArrayList<WorkDay> getByUser(int userId,Boolean sorted) {
        return _getWorkDays(userId,true,sorted);
    }

    @Override
    public ArrayList<WorkDay> getUserNextWorkDay(int user_id, Date afterDate) {
        Context context = GlobalApplication.getAppContext();
        DataBase dataBase = new DataBase(context);
        WorkDay nextWorkDay;
        SQLiteDatabase db = dataBase.getReadableDatabase();
        String[] selecArgs=new String[2];
        selecArgs[0]=String.valueOf(user_id);
        selecArgs[1]=String.valueOf(afterDate.getTime());
        String orderBy="DAY";
        ArrayList<WorkDay> workDays=new ArrayList<WorkDay>();
        Cursor cursor = db.query(DataBase.WorkTable, new String[]{"ID", "DAY", "SATURDAY", "USERID"}, "USERID=? and DAY>?", selecArgs, null, null,orderBy);
        if (cursor.moveToNext())
        {
            nextWorkDay= new WorkDay();
            nextWorkDay.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            long _date = cursor.getLong(cursor.getColumnIndex("DAY"));
            nextWorkDay.setDay(new Date(_date));
            nextWorkDay.setSaturday(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("SATURDAY"))));
            nextWorkDay.setUserId(user_id);
            nextWorkDay.setUser(getUser(db,user_id));
            workDays.add(nextWorkDay);
        }
        return workDays;
    }
}
