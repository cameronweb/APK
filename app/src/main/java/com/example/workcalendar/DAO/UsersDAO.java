package com.example.workcalendar.DAO;

import com.example.workcalendar.Entity.Users;

import java.util.ArrayList;

public interface  UsersDAO {
 public Users getUser(int id);
 public ArrayList<Users> getUsers();
 public void insertUser(Users user);
 public void DeleteUser(int userId);
 public void updateUser(Users user);

}
