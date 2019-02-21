package ru.geekbrains.client.ui;


import ru.geekbrains.client.App;
import ru.geekbrains.client.logic.Message;
import ru.geekbrains.client.logic.NetConnection;
import ru.geekbrains.client.logic.UIhandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import static java.awt.BorderLayout.*;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.exit;
import static javax.swing.JOptionPane.*;
import static javax.swing.SwingUtilities.invokeLater;


public class MainWindow
		extends JFrame
		implements UIhandler
{

  private final NetConnection conn;

  private JPanel left;
  private JPanel right;

  private JList<String> userList;
  private DefaultListModel<String> userListModel;
  private JScrollPane scrollUserList;

  private JList<Message> messageList;
  private DefaultListModel<Message> messageListModel;
  private JScrollPane scrollMessageList;

  private JPanel inputPanel;
  private JTextField inputMessage;
  private JButton sendMessage;
  private JMenuBar menu;
  private JMenu menuItems;
  private JMenuItem miSettings;
  private JMenuItem miExit;
  private LoginWindow loginWin;

  private static Dimension minSize = new Dimension(800, 800);
  private static Dimension inputMinSize = new Dimension(MAX_VALUE, 40);

  public static int leftWidth = 200;
  public static Color userListBGColor = new Color(100, 100, 100);
  public static Color messageListBGColor = new Color(200, 200, 200);


  public MainWindow()
  {
	setTitle(App.NAME);

	setDefaultCloseOperation(EXIT_ON_CLOSE);

	setMinimumSize(minSize);
//	setExtendedState(MAXIMIZED_BOTH);

	left = new JPanel(new BorderLayout());
	left.setPreferredSize(new Dimension(leftWidth, getHeight()));
	right = new JPanel(new BorderLayout());
	add(left, WEST);
	add(right, CENTER);

	addElemToLeftPanel();
	addElemToRightPanel();
	addMenu();

	addEventHandlers();

	pack();
	setVisible(true);

	conn = new NetConnection(this);

	loginWin = new LoginWindow(this, conn);
	loginWin.setVisible(true);

	if (!loginWin.isLoginSuccessful())
	{
	  exit(0);
	}

	conn.startReceiveMessages();
  }


  private void addMenu()
  {
	menu = new JMenuBar();
	menuItems = new JMenu("Menu");
	menu.add(menuItems);

	miSettings = new JMenuItem("Settings");
	miExit = new JMenuItem("Exit");
	menuItems.add(miSettings);
	menuItems.addSeparator();
	menuItems.add(miExit);

	setJMenuBar(menu);
  }


  private void addEventHandlers()
  {
	sendMessage.addActionListener(e -> sendMessage());
	inputMessage.addActionListener(e -> sendMessage());

	miExit.addActionListener(e -> exit(0));

	WindowAdapter adapter = new WindowAdapter()
	{
	  @Override
	  public void windowClosing(WindowEvent e)
	  {
		try
		{
		  if (conn != null)
			conn.close();
		}
		catch (IOException ex)
		{
		  ex.printStackTrace();
		}
		super.windowClosing(e);
	  }
	};
	addWindowListener(adapter);
  }


  private void addElemToLeftPanel()
  {
	userListModel = new DefaultListModel<>();
	userList = new JList<>(userListModel);
	userList.setCellRenderer(new UserCellRenderer());
	userList.setBackground(userListBGColor);
	scrollUserList = new JScrollPane(userList);

	left.add(scrollUserList);
  }


  private void addElemToRightPanel()
  {
	messageListModel = new DefaultListModel<>();
	messageList = new JList<>(messageListModel);
	messageList.setCellRenderer(new MessageCellRenderer());
	messageList.setBackground(messageListBGColor);

	scrollMessageList = new JScrollPane(messageList);

	inputPanel = new JPanel(new BorderLayout());
	inputMessage = new JTextField();
	sendMessage = new JButton("Send");
	inputPanel.setPreferredSize(inputMinSize);
	inputPanel.add(inputMessage, CENTER);
	inputPanel.add(sendMessage, EAST);

	right.add(scrollMessageList, CENTER);
	right.add(inputPanel, SOUTH);
  }


  private void sendMessage()
  {
	String userTo = userList.getSelectedValue();

	if (userTo == null)
	{
	  showMessageDialog(this, "Не указан получатель", "Отправка сообщения",
						INFORMATION_MESSAGE);
	  return;
	}

	String inText = inputMessage.getText().trim();

	if (inText.isEmpty())
	  return;

	Message msg = new Message(conn.getUsername(), userTo, inText);
	addMessage(msg);

	inputMessage.setText(null);
	inputMessage.requestFocus();

	try
	{
	  conn.sendMessage(msg);
	}
	catch (IOException e)
	{
	  e.printStackTrace();
	}
  }


  @Override
  public void addMessage(Message msg)
  {
	invokeLater(() ->
				{
				  messageListModel.addElement(msg);
				  int lastIndex = messageListModel.size() - 1;
				  messageList.ensureIndexIsVisible(lastIndex);
				});
  }


  @Override
  public void setUserList(List<String> usernames)
  {
	invokeLater(() ->
				{
				  userListModel.clear();
				  usernames.forEach(o -> userListModel.addElement(o));
				});
  }

}