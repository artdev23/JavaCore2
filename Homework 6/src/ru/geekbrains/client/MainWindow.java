package ru.geekbrains.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.System.exit;


public class MainWindow
		extends JFrame
{

  private ServerConnection conn;

  private JPanel left;
  private JPanel right;

  private JList<String> chatList;
  private DefaultListModel<String> chatListModel;
  private JScrollPane scrollChatList;

  private JList<String> messageList;
  private DefaultListModel<String> messageListModel;
  private JScrollPane scrollMessageList;

  private JPanel inputPanel;
  private JTextField inputMessage;
  private JButton sendMessage;
  private JMenuBar menu;
  private JMenu menuItems;
  private JMenuItem miSettings;
  private JMenuItem miExit;

  private static Dimension minSize = new Dimension(800, 800);
  private static int leftWidth = 400;
  private static Color chatListBGColor = new Color(100, 100, 100);
  private static Color messageListBGColor = new Color(200, 200, 200);
  private static Dimension inputMinSize = new Dimension(MAX_VALUE, 40);


  public MainWindow()
  {
	setTitle(Client.NAME);

	setDefaultCloseOperation(EXIT_ON_CLOSE);

	setMinimumSize(minSize);
	setExtendedState(MAXIMIZED_BOTH);

	left = new JPanel(new BorderLayout());
	left.setPreferredSize(new Dimension(leftWidth, getHeight()));
	right = new JPanel(new BorderLayout());
	add(left, BorderLayout.WEST);
	add(right, BorderLayout.CENTER);

	addElemToLeftPanel();
	addElemToRightPanel();
	addMenu();

	addEventHandlers();

	pack();
	setVisible(true);

	conn = new ServerConnection();
	conn.startInputThread(messageListModel);
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
		super.windowClosing(e);
		conn.close();
	  }
	});
  }


  private void sendMessage()
  {
	String msg = inputMessage.getText().trim();

	if (msg.isEmpty())
	  return;

	messageListModel.addElement(msg);
	inputMessage.setText("");

	conn.sendMessage(msg);
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
	messageList.setBackground(messageListBGColor);
	scrollMessageList = new JScrollPane(messageList);

	inputPanel = new JPanel(new BorderLayout());
	inputMessage = new JTextField();
	sendMessage = new JButton("Send");
	inputPanel.setPreferredSize(inputMinSize);
	inputPanel.add(inputMessage, BorderLayout.CENTER);
	inputPanel.add(sendMessage, BorderLayout.EAST);

	right.add(scrollMessageList, BorderLayout.CENTER);
	right.add(inputPanel, BorderLayout.SOUTH);
  }

}