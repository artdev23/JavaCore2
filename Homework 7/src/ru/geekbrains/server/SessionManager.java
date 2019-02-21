package ru.geekbrains.server;


import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.users.Session;
import ru.geekbrains.server.users.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.toList;


public class SessionManager
{

  private final List<Session> sessions = synchronizedList(new ArrayList<>());
  private final ServerSocket ssocket;
  private AuthService authService;


  public SessionManager(ServerSocket socket)
  {
	if (socket.isClosed())
	  throw new ServerSocketException("Server socket is closed");

	ssocket = socket;
  }


  public void start()
  throws IOException
  {
	authService = new AuthService(this);

	while (!currentThread().isInterrupted())
	{
	  Socket socket = ssocket.accept();
	  out.println("New client connected!");

	  authService.startForNewConnection(socket);
	}
  }


  private boolean isConnected(User user)
  {
	return sessions.stream()
				   .anyMatch(o -> o.getUser().equals(user));
  }


  public void connect(User user, Socket socket)
  throws IOException
  {
	if (isConnected(user))
	  return;

	Session session = new Session(this, socket, user);
	session.start();
	sessions.add(session);
	broadcastUserList();
  }


  public void disconnect(Session s)
  throws IOException
  {
	sessions.remove(s);
	broadcastUserList();
  }


  public void sendMessage(User user, String userTo, String msg)
  throws IOException
  {
	Optional<Session> session = getSessionByLogin(userTo);

	if (!session.isPresent())
	{
	  out.printf("User %s not found. Message from %s is lost.%n",
				 userTo, user.getLogin());
	  return;
	}

	session.get().sendMessage(user, msg);
  }


  private Optional<Session> getSessionByLogin(String login)
  {
	Optional<Session> session =
			sessions.stream()
					.filter(o -> o.getUser().getLogin().equals(login))
					.findFirst();
	return session;
  }


  public void broadcastUserList()
  throws IOException
  {
	List<User> users = getActiveUsers();

	for (Session o : sessions)
	{
	  o.sendUserList(users);
	}
  }


  public List<User> getActiveUsers()
  {
	return sessions.stream()
				   .map(o -> o.getUser())
				   .collect(toList());
  }

}