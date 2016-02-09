package com.raushan.lab1;

import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
public class UserRegister extends JFrame implements ActionListener {
	JFrame frm;
	JLabel lbluname;
	JTextField txtuname;
	JLabel lblupassword;
	JPasswordField txtupassword;
	JButton btnreg,btnlogin;
	Font font=new Font("Consolas", Font.BOLD, 14);
	
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	public UserRegister()
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
		
		btnreg=new JButton("Register");
		btnreg.setBounds(180, 110, 100, 40);
		btnreg.setFont(font);
		frm.add(btnreg);
		
		btnreg.addActionListener(this);
		
		btnlogin=new JButton("Login");
		btnlogin.setBounds(285, 110, 90, 40);
		btnlogin.setFont(font);
		frm.add(btnlogin);
		
		btnlogin.addActionListener(this);
		
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
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		//JOptionPane.showMessageDialog(null, "Hello");
		if(evt.getSource()==btnreg)
		{
			if(validateuser())
			{
				JOptionPane.showMessageDialog(null, "User Already Exist");
			}
			else
			{
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
				usrregister();
			}
		}
		else if(evt.getSource()==btnlogin)
		{
			new Login();
			frm.hide();
		}
	}
	public boolean validateuser()
	{
		//JOptionPane.showMessageDialog(null, "Step0");
		try
		{
			String query="select uname from testlogin";
			st=con.createStatement();
			rs=st.executeQuery(query);
			//JOptionPane.showMessageDialog(null, "Step1");
			if(rs.next())
			{
				do
				{
					//JOptionPane.showMessageDialog(null, "Step2");
					if(rs.getString(1).equals(txtuname.getText()))
					{
						//JOptionPane.showMessageDialog(null, "Step3");
						return true;
					}
				}while(rs.next());
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
		//JOptionPane.showMessageDialog(null, "Step4");
		return false;
	}
	public void usrregister()
	{
		try
		{
			String qry = String.format("insert into testlogin values ('%s','%s')", txtuname.getText(),txtupassword.getText());
			st=con.createStatement();
			//JOptionPane.showMessageDialog(null, "Step1");
			int x = st.executeUpdate(qry);
			if(x == 1){
				JOptionPane.showMessageDialog(null, "User Successfull Registered");
				new Login();
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
	public static void main(String args[])
	{
		UserRegister ur=new UserRegister();
	}
	
}
