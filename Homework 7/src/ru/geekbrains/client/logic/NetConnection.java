package ru.geekbrains.client.logic;


import ru.geekbrains.client.logic.Authorizer.AuthException;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;
import static ru.geekbrains.server.ChatServer.*;
import static ru.geekbrains.utils.MessageFormats.*;
import static ru.geekbrains.utils.Utils.outUTF;


public class NetConnection
		implements Closeable
{

  private final Socket socket;
  private final DataInputStream inStream;
  private final DataOutputStream outStream;
  private final Authorizer auth;
  private final Thread receiver;
  private final UIhandler uiHandler;


  public NetConnection(UIhandler handler)
  {
	uiHandler = handler;

	try
	{
	  socket = new Socket(HOST, PORT);
	  inStream = new DataInputStream(socket.getInputStream());
	  outStream = new DataOutputStream(socket.getOutputStream());
	}
	catch (IOException e)
	{
	  throw new ServerConnectException(e);
	}

	auth = new Authorizer(inStream, outStream);

	receiver = new Thread(() -> recieveMessages());
	receiver.setDaemon(true);
  }


  public void startReceiveMessages()
  {
	receiver.start();
  }


  private void recieveMessages()
  {
	try
	{
	  while (!currentThread().isInterrupted())
	  {
		String text = inStream.readUTF();
		out.println("New server message " + text);

		if (text.equalsIgnoreCase("/end"))
		{
		  break;
		}

		Matcher matcher = MESSAGE_PATTERN.matcher(text);
		if (matcher.matches())
		{
		  String userFrom = matcher.group(1);
		  String userTo = getUsername();
		  text = matcher.group(2);
		  Message msg = new Message(userFrom, userTo, text);
		  uiHandler.addMessage(msg);
		}
		else if (text.startsWith(USERLIST_PATTERN))
		{
		  text = text.substring(USERLIST_PATTERN.length()).trim();
		  String[] usernames = text.split(",");
		  uiHandler.setUserList(asList(usernames));
		}

	  }
	}
	catch (Exception e)
	{
	  e.printStackTrace();
	}
  }


  public void sendMessage(Message msg)
  throws IOException
  {
	String userTo = msg.getUserTo();
	String text = format(MESSAGE_SEND_PATTERN, userTo, msg.getText());
	outUTF(outStream, text);
  }


  public void login(String user, String pass)
  throws AuthException
  {
	auth.login(user, pass);
  }


  public boolean isAuthorized()
  {
	return auth.isAuthorized();
  }


  public String getUsername()
  {
	return auth.getUsername();
  }


  @Override
  public void close()
  throws IOException
  {
	socket.close();
	receiver.interrupt();
	try
	{
	  receiver.join();
	}
	catch (InterruptedException e)
	{
	  e.printStackTrace();
	}
  }

}