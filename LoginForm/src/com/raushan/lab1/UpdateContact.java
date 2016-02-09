package com.raushan.lab1;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.event.*;
public class UpdateContact implements ActionListener,FocusListener {
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
	JButton btnupdate,btnsearch;
	
	Font font=new Font("Consolas", Font.BOLD, 14);
	String group[]={"Family","Friend","Office","Other"};
		
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	
	int slno;
	String fullname=null;
	StringBuilder fullname1=new StringBuilder();
	
	public UpdateContact()
	{
		frm=new JFrame("Update Contact");
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
		txtslno.setEditable(true);
		frm.add(txtslno);
		txtslno.addFocusListener(this);
		
		lblfname=new JLabel("First Name");
		lblfname.setBounds(30,70,100,30);
		lblfname.setFont(font);
		frm.add(lblfname);
		
		txtfname=new JTextField();
		txtfname.setBounds(180,70,200,30);
		txtfname.setFont(font);
		txtfname.requestFocusInWindow();
		txtfname.setEditable(false);
		frm.add(txtfname);

		lblmname=new JLabel("Middle Name");
		lblmname.setBounds(30,110,100,30);
		lblmname.setFont(font);
		frm.add(lblmname);
		
		txtmname=new JTextField();
		txtmname.setBounds(180,110,200,30);
		txtmname.setFont(font);
		txtmname.setEditable(false);
		frm.add(txtmname);
		
		lbllname=new JLabel("Last Name");
		lbllname.setBounds(30,150,100,30);
		lbllname.setFont(font);
		frm.add(lbllname);
		
		txtlname=new JTextField();
		txtlname.setBounds(180,150,200,30);
		txtlname.setFont(font);
		txtlname.setEditable(false);
		frm.add(txtlname);
		
		lblmobno=new JLabel("Mobile No");
		lblmobno.setBounds(30,190,100,30);
		lblmobno.setFont(font);
		frm.add(lblmobno);
		
		txtmobno=new JTextField();
		txtmobno.setBounds(180,190,200,30);
		txtmobno.setFont(font);
		txtmobno.setEditable(false);
		frm.add(txtmobno);
		
		lblgroup=new JLabel("Select Group");
		lblgroup.setBounds(30,230,100,30);
		lblgroup.setFont(font);
		frm.add(lblgroup);
		
		cmbgroup=new JComboBox(group);
		cmbgroup.setBounds(180,230,200,30);
		cmbgroup.setFont(font);
		cmbgroup.setEditable(false);
		cmbgroup.setEnabled(false);
		cmbgroup.setSelectedIndex(-1);
		frm.add(cmbgroup);
		
		btnupdate=new JButton("Edit");
		btnupdate.setBounds(180,280,100,40);
		btnupdate.setFont(font);
		btnupdate.setEnabled(false);
		frm.add(btnupdate);
		btnupdate.addActionListener(this);
		
		
		btnsearch=new JButton("Search");
		btnsearch.setBounds(290, 280, 100, 40);
		btnsearch.setFont(font);
		frm.add(btnsearch);
		btnsearch.addActionListener(this);
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");
			//txtslno.setText(Integer.toString(getslno(Login.uname)));
		}
		catch(Exception exp)
		{
			
		}
		
		frm.setVisible(true);
		
	}
	public static void main(String args[])
	{
		UpdateContact sc=new UpdateContact();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnupdate)
		{
			if(btnupdate.getText()=="Edit")
			{
				btnupdate.setText("Update");
				ctrleditabltrue();
			}
			else if(btnupdate.getText()=="Update")
			{
				if(validate_form())
				{
					contactupdate();
					ctrlclear();
					ctrleditablfalse();
					txtslno.setText("");
					btnupdate.setText("Edit");
					btnsearch.setText("Search");
					btnupdate.setEnabled(false);
					txtslno.setEditable(true);
					/*if(validatemobileno())
					{
						JOptionPane.showConfirmDialog(null, "Number Already Exist");
						txtmobno.grabFocus();
					}
					else
					{
						contactupdate();
						ctrlclear();
					}*/
				}
			}
		}
		else if(e.getSource()==btnsearch)
		{
			if(btnsearch.getText().equals("Search"))
			{
				if(txtslno.getText().length()>0)
				{
					searchrecord(Integer.parseInt(txtslno.getText()));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Enter Serial No Then Click On Search Button");
					txtslno.grabFocus();
				}
			}
			else if(btnsearch.getText().equals("Next"))
			{
				ctrlclear();
				ctrleditablfalse();
				btnupdate.setText("Edit");
				btnsearch.setText("Search");
				btnupdate.setEnabled(false);
				txtfname.grabFocus();
			}
		}
		
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	public void searchrecord(int sno)
	{
		try
		{
			int counter,groupindex=0;
			String query="select first_name,middle_name,last_name,mobno,contact_group from contact where sl_no='" + sno + "' and user_name='" + Login.uname + "'";
			st=con.createStatement();
			rs=st.executeQuery(query);
			if(rs.next())
			{
					txtfname.setText(rs.getString(1));
					txtmname.setText(rs.getString(2));
					txtlname.setText(rs.getString(3));
					txtmobno.setText(rs.getString(4));
					for(counter=0;counter<=3;counter++)
					{
						//JOptionPane.showMessageDialog(null, group[counter] + " " + rs.getString(5));
						if(group[counter].equals(rs.getString(5)))
						{
							//JOptionPane.showMessageDialog(null, counter);
							groupindex=counter;
						}
					}
					cmbgroup.setSelectedIndex(groupindex);
					btnupdate.setEnabled(true);
					btnsearch.setText("Next");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Serial No Doest Not Exist");
				btnsearch.setText("Search");
				txtfname.grabFocus();
			}
		}
		catch(Exception exp)
		{
			JOptionPane.showMessageDialog(null, exp.toString());
		}
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
	public void contactupdate()
	{
		try
		{
				String fullname=getfullName(txtfname.getText(), txtmname.getText(), txtlname.getText());
				String qry="update contact set first_name='" + txtfname.getText() + "',middle_name='" + txtmname.getText() + "',last_name='" + txtlname.getText() + "',full_name='" + fullname + "',mobno=" + Long.parseLong(txtmobno.getText()) + " ,contact_group='" + cmbgroup.getSelectedItem() + "' where sl_no=" + Integer.parseInt(txtslno.getText()) + " and user_name='" + Login.uname + "'";
				st=con.createStatement();
				int x = st.executeUpdate(qry);
				if(x == 1){
					JOptionPane.showMessageDialog(null, "Contact Update Successfully Successfully");
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
	public void ctrleditabltrue()
	{
		txtfname.setEditable(true);
		txtmname.setEditable(true);
		txtlname.setEditable(true);
		txtmobno.setEditable(true);
		cmbgroup.setEnabled(true);
		
		txtslno.setEditable(false);
	}
	public void ctrleditablfalse()
	{
		txtfname.setEditable(false);
		txtmname.setEditable(false);
		txtlname.setEditable(false);
		txtmobno.setEditable(false);
		cmbgroup.setEnabled(false);
		
		txtslno.setEditable(true);
	}
	public void ctrlclear()
	{
		txtfname.setText("");
		txtmname.setText("");
		txtlname.setText("");
		txtmobno.setText("");
		cmbgroup.setSelectedIndex(-1);
	}
}
