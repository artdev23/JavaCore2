package ru.geekbrains.classes.Model.obstacles;


import ru.geekbrains.classes.Model.Participant;


public class Cross
		extends Obstacle
{

  private int length;


  public Cross(int length)
  {
	this.length = length;
  }


  @Override
  public void doIt(Participant animal)
  {
	animal.run(length);
  }

}