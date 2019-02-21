package ru.geekbrains.client.ui;


import ru.geekbrains.client.logic.Authorizer.AuthException;
import ru.geekbrains.client.logic.NetConnection;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static java.awt.BorderLayout.*;
import static java.awt.Color.GRAY;
import static javax.swing.JOptionPane.*;


public class LoginWindow
		extends JDialog
{

  private final NetConnection conn;
  private JTextField tfUsername;
  private JPasswordField pfPassword;
  private JLabel lbUsername;
  private JLabel lbPassword;
  private JButton btnLogin;
  private JButton btnCancel;
  private JPanel btnPanel;
  private JPanel panel;

  private static final String TITLE = "Login";


  public LoginWindow(Frame parent, NetConnection connection)
  {
	super(parent, TITLE, true);

	panel = new JPanel(new GridBagLayout());
	panel.setBorder(new LineBorder(GRAY));

	addElemsToPanel();
	addPanelWithButtons();

	btnLogin.addActionListener(e -> login());
	btnCancel.addActionListener(e -> dispose());

	Container container = getContentPane();
	container.add(panel, CENTER);
	container.add(btnPanel, PAGE_END);

	pack();
	setResizable(false);
	setLocationRelativeTo(parent);

	conn = connection;
  }


  private void login()
  {
	try
	{
	  String user = tfUsername.getText().trim();
	  String pass = String.valueOf(pfPassword.getPassword());

	  if (user.isEmpty())
	  {
		tfUsername.requestFocus();
		return;
	  }

	  if (pass.isEmpty())
	  {
		pfPassword.requestFocus();
		return;
	  }

	  conn.login(user, pass);
	}
	catch (AuthException ex)
	{
	  tfUsername.setText(null);
	  pfPassword.setText(null);
	  tfUsername.requestFocus();
	  String msg = ex.getMessage();
	  showMessageDialog(this, msg, TITLE, ERROR_MESSAGE);
	  return;
	}

	dispose();
  }


  private void addPanelWithButtons()
  {
	btnLogin = new JButton("Login");
	btnCancel = new JButton("Cancel");

	btnPanel = new JPanel();
	btnPanel.add(btnLogin);
	btnPanel.add(btnCancel);
  }


  private void addElemsToPanel()
  {
	GridBagConstraints cs = new GridBagConstraints();

	cs.fill = GridBagConstraints.HORIZONTAL;

	lbUsername = new JLabel("Username: ");
	cs.gridx = 0;
	cs.gridy = 0;
	cs.gridwidth = 1;
	panel.add(lbUsername, cs);

	tfUsername = new JTextField(20);
	cs.gridx = 1;
	cs.gridy = 0;
	cs.gridwidth = 2;
	panel.add(tfUsername, cs);

	lbPassword = new JLabel("Password: ");
	cs.gridx = 0;
	cs.gridy = 1;
	cs.gridwidth = 1;
	panel.add(lbPassword, cs);

	pfPassword = new JPasswordField(20);
	cs.gridx = 1;
	cs.gridy = 1;
	cs.gridwidth = 2;
	panel.add(pfPassword, cs);
  }


  public boolean isLoginSuccessful()
  {
	return conn.isAuthorized();
  }

}