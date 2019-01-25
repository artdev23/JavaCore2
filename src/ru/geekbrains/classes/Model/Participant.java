package ru.geekbrains.classes.Model;


public interface Participant
{

  boolean isOnDistance();

  void run(int distance);

  void jump(int height);

  void swim(int distance);

}
