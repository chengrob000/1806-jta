package p0;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class Launch 
{
	final static Logger logger = Logger.getLogger(Launch.class);
	Scanner in = new Scanner(System.in);
	Random rng = new Random();
	AccountList Active;
	WaitingList Waiting;
	final String VERSION_NUM = "0.1";
	
	public static void main (String[] args)
	{
		Launch pgm = new Launch();
		pgm.mainMenu(pgm);
		
	}
	
	public void logStuff(String message)
	{
		logger.trace(message);
		logger.debug(message);
		logger.info(message);
		logger.warn(message);
		logger.error(message);
		logger.fatal(message);
	}
	
	public void mainMenu(Launch pgm)
	{
		boolean cont = true;
		pgm.in.reset();

		System.out.println("Game loaded, press Enter to continue...");
		while(cont)
		{
			
			pgm.load(pgm);
			pgm.dumpIn(pgm);
			System.out.println("Welcome to the Aeva Areana Simulator");
			System.out.println("Please select an option");
			System.out.println();
			System.out.println("1. Login");
			System.out.println("2. Request Account");
			System.out.println("3. Quit Application");
			System.out.println();

			int selection = pgm.in.nextInt();
			switch(selection) {
			case 1: pgm.login(pgm);
					break;
			case 2: pgm.newAccount(pgm);
					break;
			case 3: System.out.println("Goodbye user, have a nice day");
					cont = false;
					break;
			default:System.out.println("That is not a valid selection, please select again");
			}
			pgm.save(pgm);
		}
	}
	
	public void login(Launch pgm)
	{
		Player log = null;
		boolean successU = false;
		boolean successP = false;
		while(!successP)
		{
			System.out.print("Input Username (-1 to return to main menu): ");
			String tempUname = pgm.in.next();
			successP = false;
			successU = false;
			if(pgm.Active.getAdmin().getuName().equals(tempUname))
			{

				successU = true;
				successP = pWordTest(pgm.Active.getAdmin(), pgm); 
			}
			else if(pgm.Active.getBanker().getuName().equals(tempUname))
			{

				successU = true;
				successP = pWordTest(pgm.Active.getBanker(), pgm); 
			}
			else if(pgm.Active.getLoaner().getuName().equals(tempUname))
			{

				successU = true;
				successP = pWordTest(pgm.Active.getLoaner(), pgm); 
			}
			else 
			{
				for(Account a: pgm.Active.getList())
				{

					if(a.getuName().equals(tempUname))
					{
						successU = true;
						successP = pWordTest(a, pgm); 
					}
				}
			}
			if(tempUname.equals("-1"))
			{
				break;
			}
			else if (!successU)
			{
				boolean test = false;
				for(Player p: pgm.Waiting.getList())
				{
					if(p.getuName().equals(tempUname))
						test = true;
				}
				if(test)
				{
					System.out.println("That username is for a waiting account, please try again.");
				}
				else
				{
					System.out.println("Not a valid username, would you like to \n"
							+ "1. Make a new account or \n"
							+ "2. Attempt to sign in again");
					int selection = pgm.in.nextInt();
					boolean valid = false;
					while(!valid)
					{
						switch (selection){
						case 1: newAccount(pgm);
						valid = true;
						successP = true;
						break;
						case 2: valid = true;
						break;
						default: System.out.println("Not a valid selction, would you like to \n"
								+ "1. Make a new account or \n"
								+ "2. Attempt to sign in again");
						selection = pgm.in.nextInt();
						}
					}
				}
			}
		}
	}
	
	public boolean pWordTest(Account a, Launch pgm)
	{
		System.out.print("Input Password: ");
		String tempPword = pgm.in.next();
		if(a.getuPass().equals(tempPword))
		{
			a.setPgm(pgm);
			a.menu();
			return true;
		}
		else
		{
			System.out.println("Password Incorrect, restarting login.");
			return false;
		}
	}
	
	public void load(Launch pgm)
	{
		if(pgm.Active == null)
		{
			pgm.Active = new AccountList();
			pgm.Waiting = new WaitingList();
		}
		try
		{
			ObjectInputStream ois = new ObjectInputStream(
										new FileInputStream("Active.ser"));
			pgm.Active = (AccountList) ois.readObject();
			ois.close();
			
		}
		catch(IOException e)
		{
			System.out.println("There wasn't a vailid user list, starting new world list... \n");
			pgm.generateWorld(pgm);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			ObjectInputStream ois = new ObjectInputStream(
										new FileInputStream("Waiting.ser"));
			pgm.Waiting = (WaitingList) ois.readObject();
			ois.close();
		}
		catch(IOException e)
		{
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		if(pgm.Active.getAdmin() == null)
		{

			pgm.Active = new AccountList();
			System.out.println("World not available, generating new world. \n");
			pgm.generateWorld(pgm);
		}
		
	}
	
	public void save(Launch pgm)
	{
		pgm.Active.updateList();
		pgm.Waiting.updateList();
		try{
			ObjectOutputStream oos = new ObjectOutputStream(
										new FileOutputStream("Active.ser"));
			oos.writeObject(pgm.Active);
			oos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			ObjectOutputStream oos = new ObjectOutputStream(
										new FileOutputStream("Waiting.ser"));
			oos.writeObject(pgm.Waiting); 
			oos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("Successfully Saved");
	}

	public void newAccount(Launch pgm)
	{
		pgm.dumpIn(pgm);
		System.out.print("Please choose a user name for the account: ");
		String uName = pgm.in.nextLine();
		System.out.print("Now choose a password for the account: ");
		String pWord = pgm.in.nextLine();
		System.out.println("What shall we call you?: ");
		String Name = pgm.in.nextLine();
		
		pgm.Waiting.add(new Player(Name, uName, pWord, 100, 0, 0, pgm));
		
	}
	
	public void generateWorld(Launch pgm)
	{

		pgm.dumpIn(pgm);
		System.out.println("Welcome to the Aeva Arena Simulater version: " + VERSION_NUM + "\n");
		System.out.print("Please choose a user name for the administrator: ");
		String uName = pgm.in.nextLine();
		System.out.print("Now choose a password for the administator account: ");
		String pWord = pgm.in.nextLine();
		System.out.println("Finally what shall we call the administrator?: ");
		String Name = pgm.in.nextLine();
		Administrator tempA =new Administrator(Name,uName,pWord, pgm);

		System.out.print("Please choose a user name for the banker: ");
		uName = pgm.in.nextLine();
		System.out.print("Now choose a password for the banker account: ");
		pWord = pgm.in.nextLine();
		System.out.println("Finally what shall we call the banker?: ");
		Name = pgm.in.nextLine();
		Banker tempB =new Banker(Name,uName,pWord, pgm);

		System.out.print("Please choose a user name for the loaner: ");
		uName = pgm.in.nextLine();
		System.out.print("Now choose a password for the loaner account: ");
		pWord = pgm.in.nextLine();
		System.out.println("Finally what shall we call the loaner?: ");
		Name = pgm.in.nextLine();
		Loaner tempL =new Loaner(Name,uName,pWord, pgm);
		
		System.out.println("Accounts created, generating world.");
		pgm.Active = new AccountList(new ArrayList<Player>(), tempA, tempB, tempL);
	}

	public void dumpIn(Launch pgm)
	{
		if(pgm.in.hasNextLine()) {
			String dump = pgm.in.nextLine();
		}
	}
}
