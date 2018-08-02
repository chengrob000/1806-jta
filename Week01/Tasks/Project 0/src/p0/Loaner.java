package p0;

import java.io.Serializable;
import java.util.ArrayList;

public class Loaner extends Account implements Serializable
{
	private int balance = 10000;
	private double interestRate = 1.30;
	private ArrayList<Player> waiting = new ArrayList();
	private ArrayList<Player> active = new ArrayList();
	
	public Loaner(String name, String uname, String pword, Launch pgm)
	{
		super(name, uname, pword, pgm);
	}
	
	/**
	 * Displays all players with an active loan and how much that loan's worth is.
	 */
	public void manageActiveLoans()
	{
		pgm.dumpIn(pgm);
		pgm.clearScreen();
		int selection = 0;
		updateLists();
		while(selection != -1)
		{
			if(active.size() >0)
			{
				int count = 1;
				for(Player p: active)
				{
					if(p.getHasLoan())
					{
						System.out.println(count + ". " + p.getuName() + ", " + p.getlBalance());
						count++;
					}
				}
				pgm.in.next();
			}
			else
			{
				System.out.println("No waiting accounts at this moment. \n");
				selection = -1;
			}
			pgm.save(pgm);
		}
	}
	
	/**
	 * Displays all players with a pending loan and the value of the loan, allowing the loaner
	 * to approve or decline the loan amount.
	 */
	public void manageWaitingLoans()
	{
		pgm.dumpIn(pgm);
		pgm.clearScreen();
		int selection = 0;
		updateLists();
		while(selection != -1)
		{
			if(waiting.size() >0)
			{
				int count = 1;
				for(Player p: waiting)
				{
					if(p.getLoanWaiting())
					{
						System.out.println(count + ". " + p.getuName());
						count++;
					}
				}
				System.out.print("\n" + "Select an account to manage or -1 to exit: ");
				selection = pgm.in.nextInt();
				if(selection != -1)
				{
					Player temp = (Player)waiting.get(selection-1);
					int selection2 = 0;
					count = 1;
					System.out.println("Account info");
					System.out.println("User Name: " + temp.getuName());
					System.out.println("Loan Ammount: " + temp.getlBalance());
					System.out.println("");
					System.out.println(count + ". Approve account");
					count++;
					System.out.println(count + ". Decline account");
					count++;
					System.out.println(count + ". Decide later");
					selection2 = pgm.in.nextInt();
					switch (selection2)
					{
					case 1: temp.setLoanWaiting(false);
							temp.setHasLoan(true);
							balance -= temp.getlBalance();
							break;
					case 2: temp.setLoanWaiting(false);
					case 3: break;
					default: System.out.println("Invalid choice, try again.");
				}
				}
			}
			else
			{
				System.out.println("No waiting accounts at this moment.");
				selection = -1;
			}
			pgm.save(pgm);
		}
	}
	
	/**
	 * Pings for a scan of all player accounts, adding those with waiting loans to the waiting
	 * list and those with active loans to the active list.
	 */
	public void updateLists()
	{
		active = new ArrayList();
		waiting = new ArrayList();
		for(Player p: pgm.Active.getList()) 
		{
			if(p.getHasLoan())
			{
				active.add(p);
			}
			if(p.getLoanWaiting()) 
			{
				waiting.add(p);
			}
		}
	}
	
	/**
	 * Allows the Loaner to adjust the ammount of interest charged each time a player's 
	 * time increments.
	 */
	public void adjustInterestRates()
	{
		pgm.dumpIn(pgm);
		pgm.clearScreen();
		System.out.println("The current interest rate is (Format 00.00 times)" + interestRate);
		System.out.print("What should the new interest rate be?: ");
		interestRate = pgm.in.nextDouble();
		
		System.out.println("The new interest rate is: " + interestRate);
	}
	
	public double getInterest()
	{
		return interestRate;
	}
	
	/**
	 * method shared by each Account extender, view comment on parent class for explination.
	 */
	@Override
	public void menu() 
	{
		pgm.dumpIn(pgm);
		pgm.clearScreen();
		int selection = 0;
		System.out.println("Welcome Loaner " + this.Name + "\n");
		while(selection != 4)
		{
			System.out.println("What would you like to do today?");
			int count = 1;
			System.out.println(count + ". View active accounts");
			count++;
			System.out.println(count + ". View waiting accounts");
			count++;
			System.out.println(count + ". Adjust interest rates");
			count++;
			System.out.println(count + ". Logout");
			count++;
			selection = pgm.in.nextInt();
			
			switch (selection) 
			{
			case 1: manageActiveLoans();
					break;
			case 2: manageWaitingLoans();
					break;
			case 3: adjustInterestRates();
					break;
			case 4: logout();
					break;
			}
		}
	}
}