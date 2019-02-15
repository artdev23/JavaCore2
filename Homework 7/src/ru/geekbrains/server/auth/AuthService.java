package ru.geekbrains.server.auth;


import ru.geekbrains.server.users.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthService
{

  private Map<String, String> userAccounts;
  private static final Pattern AUTH_PATTERN = Pattern.compile("^/auth (.+) (.+)$");


  public AuthService()
  {
    userAccounts = new HashMap<>();
    userAccounts.put("ivan", "123");
    userAccounts.put("petr", "345");
    userAccounts.put("julia", "789");
  }


  public User authUser(String authMessage)
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

}