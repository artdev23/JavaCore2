package ru.geekbrains.classes.Model.obstacles;


import ru.geekbrains.classes.Model.Participant;


public class Wall
		extends Obstacle
{

  private int height;


  public Wall(int height)
  {
	this.height = height;
  }


  @Override
  public void doIt(Participant participant)
  {
	participant.jump(height);
  }

}