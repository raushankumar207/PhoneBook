package com.raushan.lab1;
import javax.swing.*;

import java.awt.event.*;
public class MainForm implements ActionListener {
	JFrame frm;
	JMenuBar mb;
	JMenu file,view,edit,utility;
	JMenuItem Save_Contact,Display_Contact,Family_Contact,Office_Contact,Other_Contact,Friend_Contact,Update_Contact,Change_Password,Logg_Off,Delete_Contact;
	public static String fmcontact="";
	public MainForm()
	{
		frm=new JFrame("Main Form");
		frm.setLayout(null);
		frm.setSize(430, 400);
		frm.setLocation(500, 250);
		frm.setResizable(false);
		
		mb=new JMenuBar();
		mb.setBounds(1, 1, 420, 40);
		
		file=new JMenu("File");
		file.setBounds(1, 1, 100, 40);
		mb.add(file);
		
		view=new JMenu("View");
		view.setBounds(102, 1, 100, 40);
		mb.add(view);
		
		edit=new JMenu("Edit");
		edit.setBounds(202, 1, 100, 40);
		mb.add(edit);
		
		utility=new JMenu("Utility");
		utility.setBounds(302, 1, 100, 40);
		mb.add(utility);
		
		Save_Contact=new JMenuItem("Save Contact");
		Save_Contact.addActionListener(this);
		
		Display_Contact=new JMenuItem("Display Contact");
		Display_Contact.addActionListener(this);
		
		Family_Contact=new JMenuItem("Family Contact");
		Family_Contact.addActionListener(this);
		
		Office_Contact=new JMenuItem("Office Contact");
		Office_Contact.addActionListener(this);
		
		Friend_Contact=new JMenuItem("Friend Contact");
		Friend_Contact.addActionListener(this);
		
		Other_Contact=new JMenuItem("Other Contact");
		Other_Contact.addActionListener(this);
		
		Update_Contact=new JMenuItem("Update Contact");
		Update_Contact.addActionListener(this);
		
		Delete_Contact=new JMenuItem("Delete Contact");
		Delete_Contact.addActionListener(this);
		
		Change_Password=new JMenuItem("Change Password");
		Change_Password.addActionListener(this);
		
		Logg_Off=new JMenuItem("Logg Off");
		Logg_Off.addActionListener(this);
		
		file.add(Save_Contact);
		view.add(Display_Contact);
		view.add(Family_Contact);
		view.add(Office_Contact);
		view.add(Friend_Contact);
		view.add(Other_Contact);
		edit.add(Update_Contact);
		edit.add(Delete_Contact);
		utility.add(Change_Password);
		utility.add(Logg_Off);
		frm.add(mb);
		
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[])
	{
		new MainForm();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==Save_Contact)
		{
			new SaveContact();
		}
		else if(e.getSource()==Display_Contact)
		{
			fmcontact="vall";
			new ShowRecord();
		}
		else if(e.getSource()==Family_Contact)
		{
			fmcontact="Family";
			new ShowRecord();
		}
		else if(e.getSource()==Office_Contact)
		{
			fmcontact="Office";
			new ShowRecord();
		}
		else if(e.getSource()==Friend_Contact)
		{
			fmcontact="Friend";
			new ShowRecord();
		}
		else if(e.getSource()==Other_Contact)
		{
			fmcontact="Other";
			new ShowRecord();
		}
		else if(e.getSource()==Update_Contact)
		{
			new UpdateContact();
		}
		else if(e.getSource()==Delete_Contact)
		{
			new DeleteContact();
		}
		else if(e.getSource()==Change_Password)
		{
			new ChangePassword();
		}
		else if(e.getSource()==Logg_Off)
		{
			frm.hide();
			new Login();
		}
	}

}
