package ru.geekbrains.utils;


import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.System.exit;
import static javax.swing.JOptionPane.*;


public interface Utils
{

  static void outUTF(DataOutputStream out, String str)
  throws IOException
  {
	out.writeUTF(str);
	out.flush();
  }


  static void error(Exception e, JFrame parentFrame)
  {
	String msg = e.getMessage();
	String title = "Error";
	showMessageDialog(parentFrame, msg, title, ERROR_MESSAGE);
  }


  static void exitWithError(Exception e, JFrame parentFrame)
  {
	error(e, parentFrame);
	exit(1);
  }

}