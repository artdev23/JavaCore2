package ru.geekbrains.server;


import ru.geekbrains.server.auth.AuthException;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.users.Session;
import ru.geekbrains.server.users.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static java.util.Collections.synchronizedList;
import static ru.geekbrains.utils.Utils.outUTFto;


public class SessionManager
{

  private List<Session> sessions = synchronizedList(new ArrayList<>());
  private final ServerSocket ssocket;
  private final AuthService authService;


  public SessionManager(ServerSocket socket)
  {
	if (socket.isClosed())
	  throw new ServerSocketException("Server socket is closed");

	ssocket = socket;
	authService = new AuthService();
  }


  public void start()
  throws IOException
  {
	while (true)
	{
	  Socket socket = ssocket.accept();
	  out.println("New client connected!");

	  DataInputStream inStream = new DataInputStream(socket.getInputStream());
	  DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

	  String authMessage = inStream.readUTF();

	  try
	  {
		User user = authService.authUser(authMessage);
		out.printf("Authorization for user %s successful%n", user.getLogin());
		outUTFto(outStream, "/auth successful");

		if (!isConnected(user))
		{
		  connect(new Session(socket, user));
		}
	  }
	  catch (AuthException e)
	  {
		out.println(e.getMessage());
		outUTFto(outStream, "/auth fails");

		socket.close();
	  }
	}
  }


  private boolean isConnected(User user)
  {
	return sessions.stream()
				   .anyMatch(o -> o.getUser().equals(user));
  }


  private void connect(Session s)
  {
	sessions.add(s);
  }


  private void disconnect(Session s)
  {
	sessions.remove(s);
  }

}