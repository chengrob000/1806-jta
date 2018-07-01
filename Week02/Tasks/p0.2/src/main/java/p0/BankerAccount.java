package p0;

import p0.beans.Banker;

public class BankerAccount extends AccountClass
{
	private Banker bAcc;
	
	/**
	 * Constructor used for the banker class, does little more than call the super constructor
	 * since there are no more variables than required for a generic account on creation.
	 */
	public BankerAccount(String name, String uname, String pword, double i)
	{
		bAcc = new Banker (name, uname, pword, i);
	}
	
	/**
	 * Displays all active players and their current bank account balances
	 */
	public void viewActive()
	{
		pgm.dumpIn(pgm);
		pgm.clearScreen();
		int count = 1;
		System.out.println("List of currently active members");
		for(PlayerAccount p: pgm.Active.getList())
		{
			System.out.println(count + ". " + p.getAccountInfo().getuName() + " Account Balance: " + p.getPlayerInfo().getBankBalance());
		}
	}
	
	/**
	 * Displays current interest rate and asks banker for a new offer, storing that as the interest
	 * rate for all player accounts.
	 */
	public void adjustInterestRates()
	{
		pgm.dumpIn(pgm);
		pgm.clearScreen();
		System.out.println("The current interest rate is (Format 00.00 times)" + bAcc.getInterest());
		System.out.print("What should the new interest rate be?: ");
		bAcc.setInterest(pgm.in.nextDouble());
		
		System.out.println("The new interest rate is: " + bAcc.getInterest());
	}
	
	/**
	 * method shared by each Account extender, view comment on parent class for explination.
	 */
	@Override
	public void menu() {
		int selection = 0;
		while(selection != 3)
		{
			pgm.dumpIn(pgm);
			pgm.clearScreen();
			System.out.println("Welcome Banker " + getAccountInfo().getName() + "\n");
			System.out.println("What would you like to do today?");
			int count = 1;
			System.out.println(count + ". View active accounts");
			count++;
			System.out.println(count + ". Adjust interest rates");
			count++;
			System.out.println(count + ". Logout");
			count++;
			selection = pgm.in.nextInt();
			
			switch (selection) {
			case 1: viewActive();
					break;
			case 2: adjustInterestRates();
					break;
			case 3: logout();
					break;
			}
		}
	}
	
	public Banker getBankInfo() {
		return bAcc;
	}
}
