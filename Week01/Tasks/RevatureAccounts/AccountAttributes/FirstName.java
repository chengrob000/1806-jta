package Tasks.RevatureAccounts.AccountAttributes;;

import java.io.*;
import Tasks.RevatureAccounts.*;

public class FirstName extends AccountAttribute implements Serializable{
	private String firstname;
	
	public FirstName(AdminAccount aa) {
		super(aa);
		firstname = "admin";
		aa.addAttribute("Firstname", this);
	}
	
	public FirstName(UserAccount ua) {
		super(ua);
		firstname = askUser();
		ua.addAttribute("Firstname", this);
	}

	@Override
	public String askUser() {
		return console.readLine("Firstname: ");
	}
	
	@Override
	public void print() {
		System.out.println("Firstname: " + firstname);
	}

	@Override
	public Integer getID() {
		return firstname.hashCode();
	}
}