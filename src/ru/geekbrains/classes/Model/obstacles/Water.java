package ru.geekbrains.classes.Model.obstacles;


import ru.geekbrains.classes.Model.Participant;


public class Water
		extends Obstacle
{

  private int length;


  public Water(int length)
  {
	this.length = length;
  }


  @Override
  public void doIt(Participant participant)
  {
	participant.swim(length);
  }

}