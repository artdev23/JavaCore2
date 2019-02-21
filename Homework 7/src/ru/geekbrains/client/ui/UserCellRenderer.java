package ru.geekbrains.client.ui;


import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;
import static java.awt.Font.BOLD;
import static ru.geekbrains.client.ui.MainWindow.*;


public class UserCellRenderer
		extends JPanel
		implements ListCellRenderer<String>
{

  private final JLabel username;
  private final JLabel avatar;

  private static final int WIDTH = leftWidth - 4;
  private static final int HEIGHT = 40;
  private static final String FONT_NAME = "serif";
  private static final int FONT_SIZE = 17;


  public UserCellRenderer()
  {
	super();

	setOpaque(true);
	setBackground(userListBGColor);
	setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setLayout(new BorderLayout());

	avatar = new JLabel();
	avatar.setPreferredSize(new Dimension(HEIGHT, HEIGHT));

	username = new JLabel();
	int w = WIDTH - avatar.getWidth();
	username.setPreferredSize(new Dimension(w, HEIGHT));
	username.setFont(new Font(FONT_NAME, BOLD, FONT_SIZE));
	username.setForeground(messageListBGColor);

	add(avatar, WEST);
	add(username, EAST);
  }


  @Override
  public Component getListCellRendererComponent(JList<? extends String> list,
												String value,
												int index, boolean isSelected,
												boolean cellHasFocus)
  {
	username.setText(value);

	if (isSelected)
	{
	  setBackground(messageListBGColor);
	  username.setForeground(userListBGColor);
	}
	else
	{
	  setBackground(userListBGColor);
	  username.setForeground(messageListBGColor);
	}

	return this;
  }

}