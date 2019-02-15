package ru.geekbrains.client.logic;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.String.*;
import static ru.geekbrains.server.ChatServer.*;
import static ru.geekbrains.utils.Utils.outUTFto;


public class Authorizer
{

  private final Socket socket;
  private final DataInputStream in;
  private final DataOutputStream out;
  private boolean isSuccessAuth;

  private static final String AUTH_PATTERN = "/auth %s %s";


  public Authorizer()
  throws AuthException
  {
	try
	{
	  socket = new Socket(HOST, PORT);
	  in = new DataInputStream(socket.getInputStream());
	  out = new DataOutputStream(socket.getOutputStream());
	  isSuccessAuth = false;
	}
	catch (IOException e)
	{
	  throw new AuthException("Ошибка сети", e);
	}
  }


  public void login(String username, String password)
  throws AuthException
  {
	try
	{
	  setAuthorized(false);

	  String request = format(AUTH_PATTERN, username, password);
	  outUTFto(out, request);
	  String response = in.readUTF();

	  if (response.equals("/auth successful"))
		setAuthorized(true);

	  if (response.equals("/auth fails"))
		throw new AuthException("Ошибка авторизации");
	}
	catch (IOException e)
	{
	  throw new AuthException("Ошибка сети", e);
	}
  }


  private void setAuthorized(boolean isSuccess)
  {
	isSuccessAuth = isSuccess;
  }


  public boolean isAuthorized()
  {
	return isSuccessAuth;
  }


  public class AuthException
		  extends Exception
  {

	public AuthException(String message, Throwable cause)
	{
	  super(message, cause);
	}


	public AuthException(String message)
	{
	  this(message, null);
	}

  }

}