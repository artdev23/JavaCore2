package ru.geekbrains.client;


import ru.geekbrains.client.logic.ServerConnectException;
import ru.geekbrains.client.ui.MainWindow;

import javax.swing.*;

import static javax.swing.JOptionPane.*;


public class App
{

  private static MainWindow win;

  public static final String NAME = "HyperChat";


  public static void main(String... args)
  {
	Runnable doRun = () ->
	{
	  try
	  {
		win = new MainWindow();
	  }
	  catch (ServerConnectException e)
	  {
		showMessageDialog(win, e, "Error", ERROR_MESSAGE);
	  }
	};

	SwingUtilities.invokeLater(doRun);
  }


}