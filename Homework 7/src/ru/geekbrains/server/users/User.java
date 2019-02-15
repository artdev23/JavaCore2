package ru.geekbrains.server.users;


import java.util.Objects;


public class User
{

  private final String login;


  public User(String username)
  {
	login = username;
  }


  public String getLogin()
  {
	return login;
  }


  @Override
  public boolean equals(Object o)
  {
	if (this == o)
	  return true;

	if (o == null || getClass() != o.getClass())
	  return false;

	User user = (User) o;

	return Objects.equals(login, user.login);
  }


  @Override
  public int hashCode()
  {
	return Objects.hash(login);
  }

}