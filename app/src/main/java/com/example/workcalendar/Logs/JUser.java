package com.example.workcalendar.Logs;

import com.example.workcalendar.Entity.Users;

import java.util.ArrayList;

public class JUser {

    private ChangeType change_type;

    public int getUsr_id() {
        return usr_id;
    }

    public ChangeType getChange_type() {
        return change_type;
    }

    public enum ChangeType {ADDED, EDITED, DELETED}// типы операций
    private int usr_id;
    private ArrayList<UserFields> userFields;
    public  JUser(Users new_user, Users old_user)
    {
        userFields= new ArrayList<UserFields>();
        if (new_user==null)
        {
            userFields.add(new UserFields(UserFields.FieldName.NAME,old_user.getName(),""));
            userFields.add(new UserFields(UserFields.FieldName.PHONE,old_user.getPhone(),""));
            userFields.add(new UserFields(UserFields.FieldName.BIRTHDAY,old_user.getDateOfBirth().toString(),""));
            usr_id=old_user.getId();
            change_type=ChangeType.DELETED;
        }else if (old_user==null)
        {
            userFields.add(new UserFields(UserFields.FieldName.NAME,"",new_user.getName()));
            userFields.add(new UserFields(UserFields.FieldName.PHONE,"",new_user.getPhone()));
            userFields.add(new UserFields(UserFields.FieldName.BIRTHDAY,"",new_user.getDateOfBirth().toString()));
            usr_id=new_user.getId();
            change_type=ChangeType.ADDED;
        }else
        {
            change_type=ChangeType.EDITED;
            usr_id=old_user.getId();
            if (old_user.getName()!=new_user.getName())
            {
                userFields.add(new UserFields(UserFields.FieldName.NAME,old_user.getName(),new_user.getName()));
            }
            if (old_user.getPhone()!=new_user.getPhone())
            {
                userFields.add(new UserFields(UserFields.FieldName.PHONE,old_user.getPhone(),new_user.getPhone()));
            }
            if (old_user.getDateOfBirth()!=new_user.getDateOfBirth())
            {
                userFields.add(new UserFields(UserFields.FieldName.BIRTHDAY,old_user.getDateOfBirth().toString(),new_user.getDateOfBirth().toString()));
            }
        }

    }
    public ArrayList<UserFields> getUserFields()
    {
        return userFields;
    }




}
