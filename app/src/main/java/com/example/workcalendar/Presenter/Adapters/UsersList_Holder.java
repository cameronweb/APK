package com.example.workcalendar.Presenter.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UsersList_Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    private TextView id_view, fio_view, phone_view, birthday_view;
    private View item;
    public UsersList_Holder(@NonNull View itemView) {
        super(itemView);
        id_view=itemView.findViewById(R.id.users_id_view);
        fio_view=itemView.findViewById(R.id.users_fio_view);
        phone_view=itemView.findViewById(R.id.users_phone_view);
        birthday_view=itemView.findViewById(R.id.users_birthday_view);
        item=itemView;
        itemView.setOnCreateContextMenuListener(this);
    }


    public void bind(Users user) //передаем холдеру данные для отображения
    {
        if (user!=null)
        {
            String id=String.valueOf(user.getId());
            String name=user.getName();
            String phone=user.getPhone();
            Date date=user.getBirthDate();
            SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
            id_view.setText(id);
            fio_view.setText(name);
            phone_view.setText(phone);
            birthday_view.setText(format.format(date));
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        TextView id_view=v.findViewById(R.id.users_id_view);
        String id=String.valueOf(id_view.getText());
        menu.setHeaderTitle("Выбирите дейстивие");
        menu.add(0,Integer.parseInt(id),0,"Редактировать");
        menu.add(1,Integer.parseInt(id),0,"Удалить");
    }
}
