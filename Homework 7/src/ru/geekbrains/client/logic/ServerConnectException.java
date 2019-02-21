package ru.geekbrains.client.logic;


public class ServerConnectException
		extends RuntimeException
{

  public ServerConnectException(Throwable cause)
  {
    super("Ошибка подключения к серверу", cause);
  }

}