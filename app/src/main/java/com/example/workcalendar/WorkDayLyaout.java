package com.example.workcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.workcalendar.Adapters.Users_adapter;
import com.example.workcalendar.DAO.DAOFactory;
import com.example.workcalendar.DAO.DB;
import com.example.workcalendar.DAO.WorkDayDAO;
import com.example.workcalendar.Entity.WorkDay;
import com.example.workcalendar.Notify.Notify;
import com.example.workcalendar.RxJavaValidation.RxTextEditor;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.*;

public class WorkDayLyaout extends AppCompatActivity {

    private Spinner spinner;
    private Button btn_save;
    private CalendarView calendar;
    private Date selected_work_date;
    private int selectedWorkDayId;
    private boolean is_weekend=false;
    private int selected_user_id;
    private boolean isEdit;
    private TextView error_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_day_lyaout);
        init();
        loadUsers();
    }
    public void loadUsers()
    {
        Users_adapter users_adapter=new Users_adapter(this,DB.getUsers());
        spinner.setAdapter(users_adapter);
        if (isEdit)
        {
            loadWorkDay(selectedWorkDayId);
        }
    }
    public void init()
    {
        spinner=(Spinner)findViewById(R.id.spinner);
        btn_save=(Button)findViewById(R.id.save_button);
        calendar=findViewById(R.id.work_date_calendar);
        error_text=findViewById(R.id.work_date_error_view);
        Observable<Pair<Date,String>> pairObservable=RxTextEditor.getObservableWorkDay(calendar);
        pairObservable.subscribe(getObserver());
        Intent intent =getIntent();
        isEdit=intent.getBooleanExtra("isEdit",false);
        if(isEdit) {
            selectedWorkDayId = intent.getIntExtra("WorkDayID", 0);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView user_id_view=view.findViewById(R.id.id_view);
                selected_user_id=Integer.parseInt(user_id_view.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_user_id=0;
            }
        });
    }


    private Observer<Pair<Date,String>> getObserver() {
        Observer<Pair<Date, String>> observer =new Observer<Pair<Date, String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Pair<Date, String> dateStringPair) {
                selected_work_date =dateStringPair.first;

                if (!dateStringPair.second.isEmpty()){
                    btn_save.setEnabled(false);
                    error_text.setText(dateStringPair.second);
                    error_text.setVisibility(View.VISIBLE);
                }else
                {
                    btn_save.setEnabled(true);
                    error_text.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        return observer;
    }
    public void OnClickBtn(View v)
    {
        if (v.getId()==R.id.save_button)
        {
            SaveDay();
        }else
        {
            showActivity(false);
        }
    }
    public void SaveDay()
    {
        WorkDay workDay=new WorkDay();
        workDay.setDay(selected_work_date);
        workDay.setUserId(selected_user_id);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(selected_work_date);
        int dayOfweek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (dayOfweek== 6 | dayOfweek==0)
        {
            workDay.setSaturday(true);
        }else
        {
            workDay.setSaturday(false);
        }
        saveToDbWorkDay(workDay);
        showActivity(true);
        finish();
    }

    private void showActivity(boolean afterUpdate) {
        Intent intent = new Intent(WorkDayLyaout.this, MainActivity.class);
        intent.putExtra("afterUpdate",afterUpdate);
        startActivity(intent);
    }

    public void saveToDbWorkDay(WorkDay workDay)
    {
        DAOFactory factory=DAOFactory.getDAOFactory(getString(R.string.database));
        WorkDayDAO workDayDAO=factory.getWorkDayDAO();
        if(isEdit) {
            workDayDAO.updateWorkDay(workDay);
        }else
        {
            workDayDAO.insertWorkDay(workDay);
        }
        new Notify().checkWorkDayForUserNotify(workDay);
    }
    public void loadWorkDay(int workid){
        WorkDay workDay= DB.getWorkDay(workid);
        int userid=workDay.getUserId();
        Users_adapter adapter=(Users_adapter) spinner.getAdapter();
        int position=adapter.getPosition(userid);
        spinner.setSelection(position);
        calendar.setDate(workDay.get_day().getTime());
    }
}
