package com.raushan.lab1;

import java.awt.Color;
import java.awt.Font;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
public class ShowRecord {
	JFrame frm;
	JTable jt;
	
	Font font=new Font("Consolas", Font.BOLD, 14);
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	
	public ShowRecord()
	{
		frm=new JFrame("Display Phone Record");
		frm.setLayout(null);
		frm.setSize(400,500);
		frm.setLocation(500, 250);
		frm.setResizable(false);
		
		jt= new JTable();
		
		Object[] columns={"SL_NO","Name","Phone Number"};
		
		DefaultTableModel model=new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		jt.setModel(model);
		
		jt.setBounds(0,0, 400, 500);
		jt.setFont(font);
		jt.setBackground(Color.darkGray);
		jt.setForeground(Color.white);
		
		frm.add(jt);
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
		}
		catch(Exception exp)
		{
			
		}
		
		if(MainForm.fmcontact!="")
		{
			if(MainForm.fmcontact=="vall")
			{
				showRecord();
			}
			else if(MainForm.fmcontact=="Family")
			{
				showRecord();
			}
			else if(MainForm.fmcontact=="Office")
			{
				showRecord();
			}
			else if(MainForm.fmcontact=="Friend")
			{
				showRecord();
			}
			else if(MainForm.fmcontact=="Other")
			{
				showRecord();
			}
		}
		
		frm.setVisible(true);	
	}
	public void showRecord()
	{
		try
		{
			String query="";
			if(MainForm.fmcontact=="vall")
			{
					query="select sl_no,full_name,mobno from contact where user_name='" + Login.uname + "' order by sl_no asc";	
			}
			else if(MainForm.fmcontact=="Family" || MainForm.fmcontact=="Office" || MainForm.fmcontact=="Friend" || MainForm.fmcontact=="Other")
			{
				query="select sl_no,full_name,mobno from contact where user_name='" + Login.uname + "' and contact_group='" + MainForm.fmcontact + "'  order by sl_no asc";
			}
			st=con.createStatement();
			rs=st.executeQuery(query);
			jt.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			st.close();
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog(null, exp.toString());
		}
		finally
		{
		}
	}
	public static void main(String args[])
	{
		new ShowRecord();
	}
}
