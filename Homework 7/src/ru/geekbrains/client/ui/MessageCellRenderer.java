package ru.geekbrains.client.ui;


import ru.geekbrains.client.logic.Message;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;
import static java.awt.Font.BOLD;


public class MessageCellRenderer
		extends JPanel
		implements ListCellRenderer<Message>
{

  private final JLabel userName;
  private final JLabel message;


  public MessageCellRenderer()
  {
	super();

	setLayout(new BorderLayout());

	userName = new JLabel();
	Font f = userName.getFont();
	Font font = f.deriveFont(f.getStyle() | BOLD);
	userName.setFont(font);

	message = new JLabel();

	add(userName, NORTH);
	add(message, SOUTH);
  }


  @Override
  public Component getListCellRendererComponent(JList<? extends Message> list,
												Message value,
												int index, boolean isSelected,
												boolean cellHasFocus)
  {
	setBackground(list.getBackground());
	userName.setOpaque(true);
	userName.setText(value.getUserFrom());
	message.setText(value.getText());

	return this;
  }

}