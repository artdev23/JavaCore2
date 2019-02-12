package ru.geekbrains.client;


import ru.geekbrains.Server;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerConnection
{

  private final String SERVER_ADDR = "localhost";
  private Socket sock;
  private Scanner in;
  private PrintWriter out;


  public ServerConnection()
  {
	try
	{
	  sock = new Socket(SERVER_ADDR, Server.PORT);
	  in = new Scanner(sock.getInputStream());
	  out = new PrintWriter(sock.getOutputStream());
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
  }


  public void startInputThread(DefaultListModel<String> msgModel)
  {
	Runnable input = () ->
	{
	  try
	  {
		while (true)
		{
		  if (in.hasNext())
		  {
			String msg = in.nextLine();
			if (msg.equalsIgnoreCase("end session")) break;
			msgModel.addElement(msg);
		  }
		}
	  }
	  catch (Exception ignored)
	  {
	  }
	};

	Thread thrIn = new Thread(input);
	thrIn.start();
  }


  public void sendMessage(String msg)
  {
	out.println(msg);
	out.flush();
  }


  public void close()
  {
	try
	{
	  out.println("end");
	  out.flush();
	  sock.close();
	  out.close();
	  in.close();
	}
	catch (IOException ignored)
	{
	}
  }

}