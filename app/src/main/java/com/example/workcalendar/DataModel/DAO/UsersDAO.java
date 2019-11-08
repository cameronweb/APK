package com.example.workcalendar.DataModel.DAO;

import com.example.workcalendar.DataModel.Entity.Users;

import java.util.ArrayList;

public interface  UsersDAO {
 public Users getUser(int id);
 public ArrayList<Users> getUsers();
 public int insertUser(Users user);
 public void deleteUser(int userId);
 public void updateUser(Users user);

}
