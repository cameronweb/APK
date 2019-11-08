package com.example.workcalendar.Presenter.AsyncTasks;

import android.os.AsyncTask;
import android.widget.ListView;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.Presenter.Adapters.Wokday_adapter;
import com.example.workcalendar.GlobalApplication;

import java.util.List;

public class AsyncTask_LoadWorDays extends AsyncTask<List<WorkDay>,Integer,List<WorkDay>> {
    ListView listView;
    public AsyncTask_LoadWorDays(ListView listView) {
        super();
        this.listView=listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<WorkDay> workDays) {
        super.onPostExecute(workDays);
        Wokday_adapter adapter=new Wokday_adapter(GlobalApplication.getAppContext(),workDays);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<WorkDay> doInBackground(List<WorkDay>... workDays) {
        return GlobalApplication.getPresenter().loadFuterWorkDays();
    }
}
