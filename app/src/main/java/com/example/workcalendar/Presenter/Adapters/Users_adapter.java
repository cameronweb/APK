package com.example.workcalendar.Presenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Users_adapter extends BaseAdapter {
    private ArrayList<Users> users;
    private LayoutInflater layoutInflater;

    public Users_adapter(Context context,ArrayList<Users> users) {
        this.users = users;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return Init(position,convertView,parent);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public int getPosition(int userId)
    {
        int position=0;
        for (int i=0; i<=users.size()-1 ;i++) {
            if (users.get(i).getId()==userId)
            {
                position=i;
                break;
            }
        }
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return Init(position,convertView,parent);
    }
    private View Init(int position, View convertView, ViewGroup parent)
    {
        View view=convertView;
        if(view==null)
        {
            view=layoutInflater.inflate(R.layout.users_row,parent,false);
        }
        TextView fio_view=(TextView)view.findViewById(R.id.fio_view);
        TextView birtday_view=(TextView)view.findViewById(R.id.bitrhday_view);
        TextView phone_view=(TextView)view.findViewById(R.id.phoneview);
        TextView id_view=(TextView)view.findViewById(R.id.id_view);

        String user_id=String.valueOf(users.get(position).getId());
        fio_view.setText(users.get(position).getName());
        Date date=users.get(position).getBirthDate();
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        birtday_view.setText(format.format(date));
        phone_view.setText(users.get(position).getPhone());
        id_view.setText(user_id);
        return view;
    }
}
