package com.raushan.lab1;

import java.awt.Font;
import javax.swing.*;

import java.awt.event.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
public class Login {
	JFrame frm;
	JLabel lbluname;
	JTextField txtuname;
	JLabel lblupassword;
	JPasswordField txtupassword;
	JButton btnlogin,btnnewuser;
	Font font=new Font("Consolas", Font.BOLD, 14);
	
	public static String uname=null;
	
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	
	Boolean flag=false;
	public Login()
	{
		frm=new JFrame("Login");
		frm.setLayout(null);
		frm.setSize(430, 220);
		frm.setLocation(500, 250);
		frm.setResizable(false);
		
		
		lbluname=new JLabel("User Name");
		lbluname.setBounds(30, 30, 100, 30);
		lbluname.setFont(font);
		frm.add(lbluname);
		
		txtuname=new JTextField();
		txtuname.setBounds(180, 30, 200, 30);
		txtuname.setFont(font);
		frm.add(txtuname);
		
		lblupassword=new JLabel("User Password");
		lblupassword.setBounds(30, 70, 120, 30);
		lblupassword.setFont(font);
		frm.add(lblupassword);
		
		txtupassword=new JPasswordField();
		txtupassword.setBounds(180, 70, 200, 30);
		txtupassword.setFont(font);
		frm.add(txtupassword);
		
		btnlogin=new JButton("Login");
		btnlogin.setBounds(180, 110, 90, 40);
		btnlogin.setFont(font);
		frm.add(btnlogin);
		
		btnlogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(txtuname.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Enter User Name");
					txtuname.grabFocus();
					return;
				}
				else if(txtupassword.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Enter User Password");
					txtupassword.grabFocus();
					return;
				}
				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection(
							"jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
					String query="select *from testlogin";
					st=con.createStatement();
					rs=st.executeQuery(query);
					flag=false;
					if(rs.next())
					{
						do
						{
							if(rs.getString(1).equals(txtuname.getText()) && rs.getString(2).equals(txtupassword.getText()))
							{
								flag=true;
							}
						}while(rs.next());
					}
					if(flag)
					{
						uname=txtuname.getText();
						JOptionPane.showMessageDialog(null,"Login Successfull");
						new MainForm();
						frm.hide();
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Login Un-Successfull");
					}
				}
				catch(Exception exp)
				{
					JOptionPane.showMessageDialog(null,exp.getMessage());
				}
				finally
				{
					try
					{
						
						if (rs != null)
							rs.close();
						if (st != null)
							st.close();
						if (con != null)
							con.close();
					}
					catch(Exception exp)
					{
						
					}
				}
				//String s=txtuname.getText();
				
			}
		});
		
		btnnewuser=new JButton("New User");
		btnnewuser.setBounds(275, 110, 100, 40);
		btnnewuser.setFont(font);
		frm.add(btnnewuser);
		
		btnnewuser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new UserRegister();
				frm.hide();
			}
		});
		
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[])
	{
		Login lg=new Login();
	}

}
