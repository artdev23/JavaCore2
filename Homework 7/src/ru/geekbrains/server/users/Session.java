package ru.geekbrains.server.users;


import ru.geekbrains.server.SessionManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;

import static java.lang.String.*;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.stream.Collectors.toList;
import static ru.geekbrains.utils.MessageFormats.*;
import static ru.geekbrains.utils.Utils.outUTF;


public class Session
{

  private final SessionManager manager;
  private final Socket socket;
  private final User user;
  private final DataInputStream inStream;
  private final DataOutputStream outStream;
  private final Thread thread;


  public Session(SessionManager manager, Socket socket, User user)
  throws IOException
  {
	this.manager = manager;
	this.socket = socket;
	this.user = user;
	inStream = new DataInputStream(socket.getInputStream());
	outStream = new DataOutputStream(socket.getOutputStream());
	thread = new Thread(() -> recieveMessages());
  }


  private void recieveMessages()
  {
	try
	{
	  String login = user.getLogin();

	  while (!currentThread().isInterrupted())
	  {
		String msg = inStream.readUTF();
		out.printf("Message from user %s: %s%n", login, msg);

		if (msg.equals("/end"))
		  return;

		Matcher matcher = MESSAGE_PATTERN.matcher(msg);
		if (matcher.matches())
		{
		  String userTo = matcher.group(1);
		  String message = matcher.group(2);
		  manager.sendMessage(user, userTo, message);
		}
	  }
	}
	catch (SocketException e)
	{
	  out.println("Connection is lost");
	  e.printStackTrace();
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
	finally
	{
	  end();
	}
  }


  private void end()
  {
	String login = user.getLogin();
	out.printf("Client %s disconnected%n", login);
	try
	{
	  socket.close();
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
	finally
	{
	  try
	  {
		manager.disconnect(this);
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


  public void sendMessage(User userFrom, String msg)
  throws IOException
  {
	String login = userFrom.getLogin();
	String str = format(MESSAGE_SEND_PATTERN, login, msg);
	outUTF(outStream, str);
  }


  public void sendUserList(List<User> users)
  throws IOException
  {
	List<String> logins = users.stream()
							   .map(o -> o.getLogin())
							   .filter(o -> !o.equals(user.getLogin()))
							   .collect(toList());

	if (logins.isEmpty())
	  return;

	String str = join(",", logins);
	str = MessageFormat.format("{0} {1}", USERLIST_PATTERN, str);
	outUTF(outStream, str);
  }

}