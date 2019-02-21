package ru.geekbrains.client.logic;


import java.util.List;


public interface UIhandler
{

  void addMessage(Message msg);

  void setUserList(List<String> usernames);

}