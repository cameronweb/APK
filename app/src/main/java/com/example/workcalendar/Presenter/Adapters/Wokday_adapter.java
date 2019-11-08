package com.example.workcalendar.Presenter.Adapters;

import com.example.workcalendar.DataModel.DayOfWeeks;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.workcalendar.DataModel.Entity.WorkDay;
import com.example.workcalendar.DataModel.WeekEnds;
import com.example.workcalendar.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Wokday_adapter extends BaseAdapter  {
    private List<WorkDay> work_list;
    private LayoutInflater layoutInflater;

    public Wokday_adapter(Context context,List<WorkDay> work_list) {
        this.work_list = work_list;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return work_list.size();
    }

    @Override
    public WorkDay getItem(int position) {
        return work_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return work_list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view == null)
        {
            view=layoutInflater.inflate(R.layout.list_item,parent,false);
        }
        TextView fio_view=(TextView)view.findViewById(R.id.fioView);
        TextView date_view=(TextView)view.findViewById(R.id.dateView);
        TextView day_name_view=(TextView)view.findViewById(R.id.dayName_view);
        TextView phone_view=(TextView)view.findViewById(R.id.phone_view);
        TextView holyday_view=(TextView)view.findViewById(R.id.is_saturday_view);
        TextView user_id_view=(TextView) view.findViewById(R.id.user_id);
        TextView id_view=view.findViewById(R.id.main_list_item_id_view);


        id_view.setText(String.valueOf(work_list.get(position).getId()));
        fio_view.setText(work_list.get(position).getUser().getName());
        phone_view.setText(work_list.get(position).getUser().getPhone());
        Date date=work_list.get(position).getDate();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek =calendar.get(Calendar.DAY_OF_WEEK)-1;
         DayOfWeeks day_name= DayOfWeeks.values()[dayOfWeek];

        day_name_view.setText(String.valueOf(day_name));

        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        date_view.setText(format.format(date));
        boolean is_holyday=work_list.get(position).isSaturday();
        int holyday_int= is_holyday ? 1 : 0;
        WeekEnds weekEnd=WeekEnds.values()[holyday_int];
        //Log.d("Users",String.valueOf(holyday_int)+ is_holyday);
        holyday_view.setText(String.valueOf(weekEnd));
        user_id_view.setText(String.valueOf(work_list.get(position).getUserId()));
        return view;
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        TextView id_view=v.findViewById(R.id.main_list_item_id_view);
        int id=Integer.parseInt(id_view.getText().toString());
        menu.setHeaderTitle("Выбирите дейстивие "+String.valueOf(id));
        menu.add(0,id,0,"Редактировать");
        menu.add(1,id,0,"Удалить");
    }*/
}
