package org.jsp.atm;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;

public class Account {
	String account_holder_name;
	String dob;
	String pin;
	double money;
	long ano;
	public Account(String account_holder_name, String dob, String pin, double money, long ano) {
		super();
		this.account_holder_name = account_holder_name;
		this.dob = dob;
		this.pin = pin;
		this.money = money;
		this.ano = ano;
	}
	
	public void accountDetails() {
		System.out.println("Name : "+account_holder_name);
		System.out.println("DOB : "+dob);
		System.out.println("Pin : "+pin);
		System.out.println("balance : "+money);
		System.out.println("Account Number :"+ano);
	}
}



