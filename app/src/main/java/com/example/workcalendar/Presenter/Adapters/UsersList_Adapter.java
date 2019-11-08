package com.example.workcalendar.Presenter.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.R;

import java.util.List;

public class UsersList_Adapter extends RecyclerView.Adapter<UsersList_Holder> {

    private int count_of_list;
    private List<Users> users;
    public  UsersList_Adapter(List<Users> usersArrayList)
    {
        count_of_list=usersArrayList.size();
        users=usersArrayList;
    }
    @NonNull
    @Override
    public UsersList_Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context=parent.getContext();
        int layout_id= R.layout.user_list_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(layout_id,parent,false);
        UsersList_Holder usersList_holder=new UsersList_Holder(view);
        return usersList_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersList_Holder usersList_holder, int i) {
        usersList_holder.bind(users.get(i));
    }

    @Override
    public int getItemCount() {
        return  count_of_list;
    }
}
