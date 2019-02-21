package ru.geekbrains.client.logic;


import java.time.LocalDate;


public class Message
{

  private final String userFrom;
  private final String userTo;
  private final String text;
  private final LocalDate date;


  public Message(String userFrom, String userTo, String text)
  {
	this.userFrom = userFrom;
	this.userTo = userTo;
	this.text = text;
	this.date = LocalDate.now();
  }


  public String getUserFrom()
  {
	return userFrom;
  }


  public String getUserTo()
  {
	return userTo;
  }


  public String getText()
  {
	return text;
  }


  public LocalDate getDate()
  {
	return date;
  }

}