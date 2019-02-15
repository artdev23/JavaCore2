package ru.geekbrains.server.users;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;


public class Session
{

  private final Socket socket;
  private final User user;
  private final DataInputStream inStream;
  private final DataOutputStream outStream;
  private final Thread thread;

  private static final Pattern MESSAGE_PATTERN = Pattern.compile("^/w (.+) (.+)$");


  public Session(Socket socket, User user)
  throws IOException
  {
	this.socket = socket;
	this.user = user;
	inStream = new DataInputStream(socket.getInputStream());
	outStream = new DataOutputStream(socket.getOutputStream());
	thread = new Thread(() -> recieveMessages());
  }


  private void recieveMessages()
  {
	String login = user.getLogin();

	try
	{
	  while (!currentThread().isInterrupted())
	  {
		String msg = inStream.readUTF();
		out.printf("Message from user %s: %s%n", login, msg);

		// TODO реализовать прием сообщений от клиента и пересылку адресату через сервер
	  }
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
	finally
	{
	  out.printf("Client %s disconnected%n", login);
	  try
	  {
		socket.close();
	  }
	  catch (IOException e)
	  {
		e.printStackTrace();
	  }
	}
  }


  public void start()
  {
	thread.start();
  }


  public User getUser()
  {
	return user;
  }

}