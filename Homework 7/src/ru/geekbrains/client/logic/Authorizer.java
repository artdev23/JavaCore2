package ru.geekbrains.client.logic;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.String.format;
import static ru.geekbrains.utils.MessageFormats.*;
import static ru.geekbrains.utils.Utils.outUTF;


public class Authorizer
{

  private boolean isSuccessAuth;
  private String username;
  private final DataInputStream in;
  private final DataOutputStream out;


  public Authorizer(DataInputStream in, DataOutputStream out)
  {
	isSuccessAuth = false;
	username = null;
	this.in = in;
	this.out = out;
  }


  public void login(String username, String password)
  throws AuthException
  {
	try
	{
	  setAuthorized(false);

	  String request = format(AUTH_PATTERN_MESSAGE, username, password);
	  outUTF(out, request);
	  String response = in.readUTF();

	  if (response.equals(AUTH_SUCCESSFUL_MESSAGE))
	  {
		setAuthorized(true);
		this.username = username;
	  }
	  else if (response.equals(AUTH_FAILS_MESSAGE))
	  {
		throw new AuthException("Ошибка авторизации");
	  }
	  else
	  {
		throw new AuthException("Incorrect server response: " + response);
	  }
	}
	catch (IOException e)
	{
	  throw new RuntimeException(e);
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


  public String getUsername()
  {
	return username;
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