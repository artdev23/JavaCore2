package ru.geekbrains.classes;


import ru.geekbrains.classes.Model.*;
import ru.geekbrains.classes.Model.animals.Cat;
import ru.geekbrains.classes.Model.animals.Dog;
import ru.geekbrains.classes.Model.obstacles.*;


public class Application
{

  public static void main(String[] args)
  {
	Obstacle[] obstacles = new Obstacle[]{
			new Cross(5),
			new Wall(3),
			new Water(7)
	};
	Course c = new Course(obstacles);

	Participant
			cat1 = new Cat("Барсик", 10, 12, 0),
			dog = new Dog("Дружок", 20, 5, 15),
			cat2 = new Cat("Мурзик", 9, 14, 0),
			robot = new Robot("Вертер", 50, 50, 50);

	Team team = new Team("Heroes", cat1, dog, cat2, robot);

	c.doIt(team);
	team.showResults();
  }

}