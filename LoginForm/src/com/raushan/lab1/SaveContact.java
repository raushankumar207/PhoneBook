package com.raushan.lab1;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
public class SaveContact extends JFrame implements ActionListener,FocusListener {
	JFrame frm;
	JLabel lblslno;
	JTextField txtslno;
	JLabel lblfname;
	JTextField txtfname;
	JLabel lblmname;
	JTextField txtmname;
	JLabel lbllname;
	JTextField txtlname;
	JLabel lblmobno;
	JTextField txtmobno;
	JLabel lblgroup;
	JComboBox cmbgroup;
	JButton btnsave;
	
	Font font=new Font("Consolas", Font.BOLD, 14);
	String group[]={"Family","Friend","Office","Other"};
	
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	int slno;
	String fullname=null;
	StringBuilder fullname1=new StringBuilder();
	public SaveContact()
	{
		frm=new JFrame("Save Contact");
		frm.setLayout(null);
		frm.setSize(430, 370);
		frm.setLocation(500, 250);
		frm.setResizable(false);
		
		//JOptionPane.showMessageDialog(null, Login.uname);
		
		
		lblslno=new JLabel("SL No");
		lblslno.setBounds(30, 30, 100, 30);
		lblslno.setFont(font);
		frm.add(lblslno);
		
		txtslno=new JTextField();
		txtslno.setBounds(180, 30, 100, 30);
		txtslno.setFont(font);
		txtslno.setEditable(false);
		frm.add(txtslno);
		
		lblfname=new JLabel("First Name");
		lblfname.setBounds(30,70,100,30);
		lblfname.setFont(font);
		frm.add(lblfname);
				
		txtfname=new JTextField();
		txtfname.setBounds(180,70,200,30);
		txtfname.setFont(font);
		txtfname.requestFocusInWindow();
		frm.add(txtfname);
		txtfname.addFocusListener(this);

		lblmname=new JLabel("Middle Name");
		lblmname.setBounds(30,110,100,30);
		lblmname.setFont(font);
		frm.add(lblmname);
		
		txtmname=new JTextField();
		txtmname.setBounds(180,110,200,30);
		txtmname.setFont(font);
		frm.add(txtmname);
		txtmname.addFocusListener(this);
		
		lbllname=new JLabel("Last Name");
		lbllname.setBounds(30,150,100,30);
		lbllname.setFont(font);
		frm.add(lbllname);
		
		txtlname=new JTextField();
		txtlname.setBounds(180,150,200,30);
		txtlname.setFont(font);
		frm.add(txtlname);
		txtlname.addFocusListener(this);
		
		lblmobno=new JLabel("Mobile No");
		lblmobno.setBounds(30,190,100,30);
		lblmobno.setFont(font);
		frm.add(lblmobno);
		
		txtmobno=new JTextField();
		txtmobno.setBounds(180,190,200,30);
		txtmobno.setFont(font);
		frm.add(txtmobno);
		txtmobno.addFocusListener(this);
		
		lblgroup=new JLabel("Select Group");
		lblgroup.setBounds(30,230,100,30);
		lblgroup.setFont(font);
		frm.add(lblgroup);
		
		cmbgroup=new JComboBox(group);
		cmbgroup.setBounds(180,230,200,30);
		cmbgroup.setFont(font);
		cmbgroup.setSelectedIndex(-1);
		frm.add(cmbgroup);
		
		btnsave=new JButton("Save");
		btnsave.setBounds(180,280,150,40);
		btnsave.setFont(font);
		frm.add(btnsave);
		btnsave.addActionListener(this);
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
			txtslno.setText(Integer.toString(getslno(Login.uname)));
		}
		catch(Exception exp)
		{
			
		}	
		frm.setVisible(true);
		
	}
	public void focusGained(FocusEvent fevt)
	{
		if(fevt.getSource()==txtfname)
		{
			if(txtfname.getText().length()!=0)
			{
				txtfname.selectAll();
			}
		}
		else if(fevt.getSource()==txtmname)
		{
			if(txtmname.getText().length()!=0)
			{
				txtmname.selectAll();
			}
		}
		else if(fevt.getSource()==txtlname)
		{
			if(txtlname.getText().length()!=0)
			{
				txtlname.selectAll();
			}
		}
		else if(fevt.getSource()==txtmobno)
		{
			if(txtmobno.getText().length()!=0)
			{
				txtmobno.selectAll();
			}
		}
	}
	public void focusLost(FocusEvent fevt)
	{
		
	}
	public int getslno(String username)
	{
		try
		{
			String query="select sl_no from contact where user_name='" + Login.uname + "' order by sl_no desc";
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next())
			{
				slno=rs.getInt(1)+1;
			}
			else
			{
				slno=1;
			}
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog(null, exp.toString());
		}
		return slno;
	}
	public String getfullName(String fname,String mname,String lname)
	{
		fullname1.delete(0, fullname1.length());
		if(fname.length()>0)
		{
			fullname1.append(fname);
			if(mname.length()>0)
			{
				if(lname.length()>0)
				{
					fullname1.append(" " + mname + " ");
				}
				else
				{
					fullname1.append(" " + mname);
				}
			}
			if(lname.length()>0)
			{
				if(mname.length()>0)
				{
					fullname1.append(lname);
				}
				else
				{
					fullname1.append(" " + lname);
				}
					
			}
		}
		
		return fullname1.toString();
	}
	public boolean validatemobileno()
	{
		try
		{
			String query="select mobno from contact where user_name='" + Login.uname + "'";
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next())
			{
				do
				{
					if(rs.getDouble(1)==Double.parseDouble(txtmobno.getText()))
					{
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
			
			}
			catch(Exception exp)
			{
				
			}
		}
		return false;
	}
	public void usrregister()
	{
		try
		{
				String fullname=getfullName(txtfname.getText(), txtmname.getText(), txtlname.getText());
				String qry = String.format("insert into contact values ('%d','%s','%s','%s','%s','%d','%s','%s')",Integer.parseInt(txtslno.getText()),txtfname.getText(),txtmname.getText(),txtlname.getText(),fullname,Long.parseLong(txtmobno.getText()),cmbgroup.getSelectedItem(),Login.uname);
				st=con.createStatement();
				int x = st.executeUpdate(qry);
				if(x == 1){
					JOptionPane.showMessageDialog(null, "Contact Save Successfully");
					txtslno.setText(Integer.toString(Integer.parseInt(txtslno.getText())+1));
					clear();
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
				
			}
			catch(Exception exp)
			{
				
			}
		}
	
	}
	public boolean validate_form()
	{
		if(txtfname.getText().length()==0)
		{
			JOptionPane.showMessageDialog(null, "Enter First Name");
			txtfname.grabFocus();
			return false;
		}
		
		else if(txtmobno.getText().length()==0)
		{
			JOptionPane.showMessageDialog(null, "Enter Mobile Number");
			txtmobno.grabFocus();
			return false;
		}
		else if(cmbgroup.getSelectedIndex()==-1)
		{
			JOptionPane.showMessageDialog(null, "Select Contact Group");
			cmbgroup.grabFocus();
			return false;
		}
		return true;
	}
	public void clear()
	{
		txtfname.setText("");
		txtmname.setText("");
		txtlname.setText("");
		txtmobno.setText("");
		txtfname.grabFocus();
		cmbgroup.setSelectedIndex(-1);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(validate_form())
		{
			if(validatemobileno())
			{
				JOptionPane.showMessageDialog(null, "Number Already Exist");
			}
			else
			{
				usrregister();
			}
		}
	}
	public static void main(String args[])
	{
		SaveContact sc=new SaveContact();
	}
	
	
}
