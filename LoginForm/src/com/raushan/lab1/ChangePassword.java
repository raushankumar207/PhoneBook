package com.raushan.lab1;

import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
public class ChangePassword implements ActionListener {
	JFrame frm;
	JLabel lbluname;
	JTextField txtuname;
	JLabel lblupassword;
	JPasswordField txtupassword;
	JButton btnchpasswd;
	Font font=new Font("Consolas", Font.BOLD, 14);
	
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	public ChangePassword()
	{
		
		frm=new JFrame("User Registration");
		frm.setLayout(null);
		frm.setSize(430, 220);
		frm.setLocation(500, 250);
		frm.setResizable(false);
		
		
		lbluname=new JLabel("User Name");
		lbluname.setBounds(30, 30, 100, 30);
		lbluname.setFont(font);
		frm.add(lbluname);
		
		txtuname=new JTextField(Login.uname);
		txtuname.setBounds(180, 30, 200, 30);
		txtuname.setFont(font);
		txtuname.setEditable(false);
		frm.add(txtuname);
		
		lblupassword=new JLabel("User Password");
		lblupassword.setBounds(30, 70, 120, 30);
		lblupassword.setFont(font);
		frm.add(lblupassword);
		
		txtupassword=new JPasswordField();
		txtupassword.setBounds(180, 70, 200, 30);
		txtupassword.setFont(font);
		frm.add(txtupassword);
		
		btnchpasswd=new JButton("Change Password");
		btnchpasswd.setBounds(180, 110, 200, 40);
		btnchpasswd.setFont(font);
		frm.add(btnchpasswd);
		
		btnchpasswd.addActionListener(this);
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
		}
		catch(Exception exp)
		{
			
		}
		
		frm.setVisible(true);
	}
	public static void main(String args[])
	{
		new ChangePassword();
	}
	public void passwordupdate()
	{
		try
		{
				String qry="update testlogin set upassword='" + txtupassword.getText() + "' where uname='" + Login.uname + "'";
				st=con.createStatement();
				int x = st.executeUpdate(qry);
				if(x == 1){
					JOptionPane.showMessageDialog(null, "Password Update Successfully");
					frm.hide();
				}
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog(null, exp.toString());
		}
		finally
		{
			try
			{
				
				/*if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (con != null)
					con.close();*/
			}
			catch(Exception exp)
			{
				
			}
		}
	
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(txtupassword.getText().length()==0)
		{
			JOptionPane.showMessageDialog(null,"Enter User Password");
			txtupassword.grabFocus();
			return;
		}
		passwordupdate();
	}
}
