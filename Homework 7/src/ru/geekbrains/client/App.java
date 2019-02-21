package ru.geekbrains.client;


import ru.geekbrains.client.logic.ServerConnectException;
import ru.geekbrains.client.ui.MainWindow;

import static javax.swing.SwingUtilities.invokeLater;
import static ru.geekbrains.utils.Utils.exitWithError;


public class App
{

  private static MainWindow win;

  public static final String NAME = "HyperChat";


  public static void main(String... args)
  {
	invokeLater(() ->
				{
				  try
				  {
					win = new MainWindow();
				  }
				  catch (ServerConnectException e)
				  {
					exitWithError(e, win);
				  }
				});
  }


}