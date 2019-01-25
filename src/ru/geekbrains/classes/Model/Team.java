package ru.geekbrains.classes.Model;


import java.util.stream.Stream;

import static java.lang.System.out;
import static java.util.Arrays.stream;


public class Team
{

  private String name;
  private Participant[] participants;


  public Team(String name, Participant p1, Participant p2, Participant p3, Participant p4)
  {
	this.name = name;
	this.participants = new Participant[]{p1, p2, p3, p4};
  }


  public Stream<Participant> getMembers()
  {
	return stream(participants);
  }


  public void showResults()
  {
	out.println("\nИтоги команды \"" + name + "\":");

	stream(participants)
			.forEach(out::println);
  }


  public void showPastDistanceMembers()
  {
	out.println("Члены команды \"" + name + "\", прошедшие дистанцию:");

	stream(participants)
			.filter(Participant::isOnDistance)
			.forEach(out::println);
  }

}