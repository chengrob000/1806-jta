package com.revature.bank;

import java.io.Serializable;
import org.apache.log4j.Logger;


public class Account implements Serializable
{
	final static Logger logger = Logger.getLogger(Account.class);
	private static final long serialVersionUID = 2022586512565322588L;
	private String userName;
	private String password;
	private int accountNumber;
	private int accountValue;
	private int accountType;
	private int approved;
	
	public Account(int accountNumber, String userName, String password, int accountValue, int accountType, int approved) 
	{
		super();
		this.accountNumber = accountNumber;
		this.userName = userName;
		this.password = password;
		this.accountValue = accountValue;
		this.accountType = accountType;
		this.approved = approved;
		logger.info("New account initialized");
	}
	
	public Account()
	{
		super();
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
		logger.info("Set account name to: " + userName + ".");
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
		logger.info("Set account password to: " + password + ".");
	}

	public int getAccountNumber() 
	{
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) 
	{
		this.accountNumber = accountNumber;
		logger.info("Set account number to: " + accountNumber + ".");
	}

	public int getAccountValue() 
	{
		return accountValue;
	}

	public void setAccountValue(int accountValue) 
	{
		this.accountValue = accountValue;
		logger.info("Set account value to: " + accountValue + ".");
	}
	
	public int getAccountType()
	{
		return accountType;
	}
	
	public void setAccountType(int accountType)
	{
		this.accountType = accountType;
		logger.info("Set account type to: " + accountType + ".");
	}
	
	public int getApproved()
	{
		return approved;
	}
	
	public void setApproved(int approved)
	{
		this.approved = approved;
		logger.info("Set approved to: " + approved + ".");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber != other.accountNumber)
			return false;
		if (accountType != other.accountType)
			return false;
		if (accountValue != other.accountValue)
			return false;
		if (approved != other.approved)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [userName=" + userName + ", password=" + password + ", accountNumber=" + accountNumber
				+ ", accountValue=" + accountValue + ", accountType=" + accountType + ", approved= " + approved + "]";
	}
}