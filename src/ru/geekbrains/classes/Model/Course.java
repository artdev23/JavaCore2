package ru.geekbrains.classes.Model;


import ru.geekbrains.classes.Model.obstacles.Obstacle;


public class Course
{

  private Obstacle[] obstacles;


  public Course(Obstacle[] obstacles)
  {
	this.obstacles = obstacles;
  }


  public void doIt(Team team)
  {
	team.getMembers()
		.forEach(x ->
				 {
				   for (Obstacle obstacle : obstacles)
				   {
					 obstacle.doIt(x);
					 if (!x.isOnDistance()) break;
				   }
				 });
  }

}