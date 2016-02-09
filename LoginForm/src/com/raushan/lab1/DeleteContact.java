package com.raushan.lab1;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.event.*;
public class DeleteContact implements ActionListener {
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
	JButton btndelete,btnsearch;
	
	Font font=new Font("Consolas", Font.BOLD, 14);
	String group[]={"Family","Friend","Office","Other"};
		
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	
	int slno;
	String fullname=null;
	StringBuilder fullname1=new StringBuilder();

	public DeleteContact()
	{
		frm=new JFrame("Delete Contact");
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
		
		btndelete=new JButton("Delete");
		btndelete.setBounds(180,280,100,40);
		btndelete.setFont(font);
		btndelete.setEnabled(false);
		frm.add(btndelete);
		btndelete.addActionListener(this);
		
		
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
					btndelete.setEnabled(true);
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
	public static void main(String args[])
	{
		new DeleteContact();
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource()==btndelete)
		{
			contactdelete();
		}
		else if(evt.getSource()==btnsearch)
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
				btnsearch.setText("Search");
				btndelete.setEnabled(false);
				txtfname.grabFocus();
			}
		}
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
	public void updateSlNo()
	{
		try
		{
			int counter=1;
			//String query1="select count(*) from contact where user_name='" + Login.uname + "'";
			String query1="select rowid from contact where user_name='" + Login.uname + "'";
			st=con.createStatement();
			rs=st.executeQuery(query1);
				while(rs.next())
				{
					String qry = "update contact set sl_no=" + counter + " where user_name='" + Login.uname + "' and rowid='" + rs.getString(1) + "'";
					st=con.createStatement();
					st.executeUpdate(qry);
					counter++;
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
	public void contactdelete()
	{
		try
		{
				int status=JOptionPane.showConfirmDialog(null, "Do you really want to delete","Delete",JOptionPane.YES_NO_OPTION);
				if(status==0)
				{
					String qry="delete from contact where user_name='" + Login.uname + "' and sl_no=" + Integer.parseInt(txtslno.getText()) + "";
					st=con.createStatement();
					int x = st.executeUpdate(qry);
					if(x == 1){
						JOptionPane.showMessageDialog(null, "Contact Delete Successfully");
						ctrlclear();
						txtslno.setText("");
						txtslno.grabFocus();
						btndelete.setEnabled(false);
						btnsearch.setText("Search");
						updateSlNo();
					}
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

}
