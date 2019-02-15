package ru.geekbrains.utils;


import java.io.DataOutputStream;
import java.io.IOException;


public interface Utils
{

  static void outUTFto(DataOutputStream out, String str)
  throws IOException
  {
	out.writeUTF(str);
	out.flush();
  }

}