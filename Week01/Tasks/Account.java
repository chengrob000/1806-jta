import java.io.*;
import java.util.*;

public abstract class Account implements ConsoleReference, Serializable {
	HashMap<String, AccountAttribute> attributes = new HashMap<>();

 	Account() {}

 	public void addAttribute(String field, AccountAttribute aa) {
  		attributes.put(field, aa);
 	}

	public Integer getID(){
		Integer index = attributes.get("Username").getID() + 
				attributes.get("Password").getID();
		return index.hashCode();
	}

 	public void print(){
		for(AccountAttribute aa : attributes.values())
			aa.print();
 	}

	public static Integer signIn(){
		Integer username = console.readLine("Username: ").hashCode();
		Integer password = String.valueOf(console.readPassword("Password: ")).hashCode();
		Integer index = username + password;

		return index.hashCode();	
	}
}
