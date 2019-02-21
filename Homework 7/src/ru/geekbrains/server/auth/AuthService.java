package ru.geekbrains.server.auth;


import ru.geekbrains.server.SessionManager;
import ru.geekbrains.server.users.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static ru.geekbrains.utils.MessageFormats.*;
import static ru.geekbrains.utils.Utils.outUTF;


public class AuthService
{

  private final Map<String, String> userAccounts;
  private final SessionManager sessionManager;


  public AuthService(SessionManager sm)
  {
    sessionManager = sm;
    userAccounts = new HashMap<>();
    userAccounts.put("ivan", "123");
    userAccounts.put("petr", "345");
    userAccounts.put("julia", "789");
  }


  private User authUser(String authMessage)
  throws AuthException
  {
    Matcher matcher = AUTH_PATTERN.matcher(authMessage);

    if (!matcher.matches())
      throw new AuthException("Incorrect authorization message: " + authMessage);

    String user = matcher.group(1);
    String pass = matcher.group(2);

    String pwd = userAccounts.get(user);

    if(pwd == null || !pwd.equals(pass))
      throw new AuthException("Authorization for user "+user+" failed");

    return new User(user);
  }


  private void authentication(Socket socket)
  {
    DataInputStream inStream = null;
    DataOutputStream outStream = null;
    try
    {
      inStream = new DataInputStream(socket.getInputStream());
      outStream = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    while (!currentThread().isInterrupted())
    {
      try
      {
        String authMessage = inStream.readUTF();
        User user = authUser(authMessage);
        out.printf("Authorization for user %s successful%n", user.getLogin());
        outUTF(outStream, AUTH_SUCCESSFUL_MESSAGE);
        sessionManager.connect(user, socket);
        break;
      }
      catch (AuthException e)
      {
        out.println(e.getMessage());
        try
        {
          outUTF(outStream, AUTH_FAILS_MESSAGE);
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }


  public void startForNewConnection(Socket socket)
  {
    new Thread(() -> authentication(socket)).start();
  }

}