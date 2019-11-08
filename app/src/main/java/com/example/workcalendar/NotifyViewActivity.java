package com.example.workcalendar;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.ViewModel.ActivityModel;

public class NotifyViewActivity extends AppCompatActivity {

    TextView fioView, dateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_view);
        init();
    }
    public  void init()
    {

        Bundle extras=getIntent().getExtras();
        String workDate=extras.getString("workkDate");
        int userId=GlobalApplication.getPresenter().getNotifyUser();
        Users user=GlobalApplication.getPresenter().getUser(userId);
        fioView=findViewById(R.id.notify_activity_view_fio);
        dateView=findViewById(R.id.notify_activity_view_workdate);
        fioView.setText(user.getName());
        dateView.setText(workDate);
    }

    public void onOkButtonClick(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void onMainButtonClick(View view)
    {
       Intent intent=new Intent(this,MainActivity.class);
       startActivity(intent);
    }
}
