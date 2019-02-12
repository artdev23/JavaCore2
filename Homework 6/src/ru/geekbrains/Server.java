package ru.geekbrains;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;
import static java.util.Collections.synchronizedList;


public class Server
{

  private ServerSocket socket;
  private List<DataOutputStream> outStreams;

  public static final int PORT = 7777;


  public Server()
  {
	try
	{
	  socket = new ServerSocket(PORT);
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
	outStreams = synchronizedList(new ArrayList<>());
  }


  public void start()
  {
	out.println("Server started!");

	startInputThread(socket);
	startOutputThread();
  }


  private void startInputThread(ServerSocket socket)
  {
	Runnable input = () ->
	{
	  try
	  {
		while (true)
		{
		  acceptConnections(socket);
		}
	  }
	  catch (IOException e)
	  {
		e.printStackTrace();
	  }
	};

	Thread thrIn = new Thread(input);
	thrIn.start();
  }


  private void startOutputThread()
  {
	Runnable output = () ->
	{
	  Scanner sc = new Scanner(in);
	  while (true)
	  {
		String msg = sc.nextLine();
		outStreams.forEach(x -> sendMessage(msg, x));
	  }
	};

	Thread thrIn = new Thread(output);
	thrIn.start();
  }


  private void sendMessage(String msg, DataOutputStream stream)
  {
	try
	{
	  stream.writeUTF(msg);
	  stream.flush();
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
  }


  private void acceptConnections(ServerSocket serverSocket)
  throws IOException
  {
	Socket socket = serverSocket.accept();
	out.println("Client connected!");

	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	outStreams.add(out);

	createSession(socket);
  }


  private void createSession(Socket socket)
  {
	Thread thr = new Thread(() ->
							{
							  try
							  {
								recieveMessages(socket);
							  }
							  catch (IOException e)
							  {
								e.printStackTrace();
							  }
							});

	thr.start();
  }


  private void recieveMessages(Socket socket)
  throws IOException
  {
	try (DataInputStream in = new DataInputStream(socket.getInputStream()))
	{
	  while (true)
	  {
		String msg = in.readUTF();
		out.println("Message: " + msg);
	  }
	}
  }


  public static void main(String[] args)
  {
	new Server().start();
  }

}