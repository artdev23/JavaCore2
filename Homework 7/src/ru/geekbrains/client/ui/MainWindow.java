package ru.geekbrains.client.ui;


import ru.geekbrains.client.App;
import ru.geekbrains.client.logic.Message;
import ru.geekbrains.client.logic.NetConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static java.awt.BorderLayout.*;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.exit;


public class MainWindow
		extends JFrame
{

  private final NetConnection conn;

  private JPanel left;
  private JPanel right;

  private JList<String> chatList;
  private DefaultListModel<String> chatListModel;
  private JScrollPane scrollChatList;

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
  private static int leftWidth = 400;
  private static Color chatListBGColor = new Color(100, 100, 100);
  private static Color messageListBGColor = new Color(200, 200, 200);
  private static Dimension inputMinSize = new Dimension(MAX_VALUE, 40);


  public MainWindow()
  {
	setTitle(App.NAME);

	setDefaultCloseOperation(EXIT_ON_CLOSE);

	setMinimumSize(minSize);
	setExtendedState(MAXIMIZED_BOTH);

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

	loginWin = new LoginWindow(this);
	loginWin.setVisible(true);

	if (!loginWin.isAuthSuccessful())
	{
	  exit(0);
	}

	conn = new NetConnection();
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

	addWindowListener(new WindowAdapter()
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
	});
  }


  private void addElemToLeftPanel()
  {
	chatListModel = new DefaultListModel<>();
	chatList = new JList<>(chatListModel);
	chatList.setBackground(chatListBGColor);
	scrollChatList = new JScrollPane(chatList);

	left.add(scrollChatList);
	chatListModel.addElement("List of chats (for future)");
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
	String inText = inputMessage.getText().trim();

	if (inText.isEmpty())
	  return;

//	Message msg = new Message(inText);
//
//	messageListModel.addElement(msg);
//	int lastIndex = messageListModel.size() - 1;
//	messageList.ensureIndexIsVisible(lastIndex);
//
//	inputMessage.setText("");
//
//	conn.sendMessage(msg);
  }

}