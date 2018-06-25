package discompanydatcompany.vendingmachine;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import discompanydatcompany.vendingmachine.entities.Admin;
import discompanydatcompany.vendingmachine.entities.BottledWater;
import discompanydatcompany.vendingmachine.entities.Gum;
import discompanydatcompany.vendingmachine.entities.Item;
import discompanydatcompany.vendingmachine.entities.Snack;
import discompanydatcompany.vendingmachine.entities.StockItem;
import discompanydatcompany.vendingmachine.entities.User;
import discompanydatcompany.vendingmachine.entities.UserList;
import discompanydatcompany.vendingmachine.entities.VendingMachine;
import discompanydatcompany.vendingmachine.entities.VendingMachineList;
import discompanydatcompany.vendingmachine.utilities.Container;
import discompanydatcompany.vendingmachine.utilities.Input;
import discompanydatcompany.vendingmachine.utilities.SaveFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App {
	private static Logger logger = LogManager.getLogger();
	public static final String SAVE_FILE_NAME = "DATSAV";
    private boolean isSaveFilePresent = false;
    
    
    public String getGreeting() {
        return "\"Hello world.\" -- Feral Vending Machine (No Owner)";
    }

    public static void main(String[] args) {
    	App app = new App();
    	Input input = new Input();
    	SaveFile save = new SaveFile();
    	VendingMachine vend;
    	VendingMachineList vendingMachineList;
    	UserList userList;
    	User activeUser;
    	
    	
    	if (new File(SAVE_FILE_NAME).isFile()) {
    		save.loadFromFile(SAVE_FILE_NAME);
    		vendingMachineList = save.getVendingMachineList();
    		userList = save.getUserList();
    		
    		if (vendingMachineList != null || userList != null || vendingMachineList.size() != 0 || userList.size() != 0) {
    			app.isSaveFilePresent = true;
    		}
    	}
    	
    	// No save is present
    	if (!app.isSaveFilePresent) {
    		String name;
    		String password;
    		
    		vend = new VendingMachine("Feral Vending Machine", "No Owner", "" ,"An arbitrary forest in Northern New England. This vending machine is comfortably tucked under a rocky alcove. 1368 A.D.");
    		System.out.println(vend.toString());
    		System.out.println(app.getGreeting());
    		System.out.println("You are Admin. You have no choice. -- " + vend.getVendingMachineName() + "(" + vend.getAdminName() + ")");
    		System.out.println("What's your name -- " + vend.getVendingMachineName() + "(" + vend.getAdminName() + ")");
    		
    		do {
    			name = String.valueOf(input.passNext());
    		} while(name == null || name == "");
    		
    		System.out.println("Tell me something about yourself. -- " + vend.getVendingMachineName() + "(" + vend.getAdminName() + ")");
    		String aboutMe = String.valueOf(input.passNext());
    		
    		System.out.println("Enter a password and tell me no lies. -- " + vend.getVendingMachineName() + "(" + vend.getAdminName() + ")");
    		
    		do {
    			password = String.valueOf(input.passNext());
    		} while (password == null || password == "");
    		
    		Admin admin = new Admin(name, password, aboutMe);
    		vend.setAdminName(admin.getName());
    		// System.out.println("Admin name:" + admin.getName());
    		// System.out.println(vend.getVendingMachineName() + " " + vend.getVendingMachineId());
    		
    		vendingMachineList = new VendingMachineList();
    		vendingMachineList.addVendingMachine(vend);
    		
    		userList = new UserList();
    		userList.addUser(admin);  
    		
    		admin.setLocation(vend.getVendingMachineId());
    		
    		save.setUserList(userList);
    		save.setVendingMachineList(vendingMachineList);
    		
    		try {
				save.writeToFile(SAVE_FILE_NAME);
			} catch (IOException e) {
				System.out.println("Failed to save!");
				e.printStackTrace();
			}
    		
    		activeUser = admin;
    		// System.out.println("User: " + activeUser.getName());
    		// System.out.println("User location: " + activeUser.getLocation() );
    		
    	
    	} else {
    		// Save is present
    		save.loadFromFile(SAVE_FILE_NAME);
    		String password;
    		String username;
    		HashMap<String, String> keyRing = new HashMap<String, String>();
    		
    		// retrieve save data 
    		userList = save.getUserList();
    		vendingMachineList = save.getVendingMachineList();
    		
    		// login
    		User loginSuccess = null;
    		
    		while(loginSuccess == null) {
    			System.out.println("Enter username");
    			username = String.valueOf(input.passNext());
    			password = String.valueOf(input.passNext());
    			loginSuccess = userList.getUserCredentials(username, password);
    		}
    		
    		activeUser = loginSuccess;
    		// System.out.println("User: " + activeUser.getName());
    		// System.out.println("User location: " + activeUser.getLocation() );
    		// System.out.println("Vending Machine" + vendingMachineList.getVendingMachine(activeUser.getLocation()).getVendingMachineName());
    		
    	}
    
	    // vend = new VendingMachine("Feral Vending Machine", "No Owner", "An arbitrary forest in Northern New England. This vending machine is comfortably tucked under a rocky alcove. 1368 A.D.");
		vendingMachineList = save.getVendingMachineList();
		userList = save.getUserList();
    	vend = vendingMachineList.getVendingMachine(activeUser.getLocation());
		// System.out.println("vend login" + activeUser.getLocation() + "list size "+ vendingMachineList.size() );
		Container textInput = new Container(new ArrayList<String>());
		Container staticContent = new Container(new ArrayList<String>());
		//Container textOutput = new Container(new ArrayList<String>());
		String userInput = "";
		//System.out.println(consoleContainer.toString());
		shell:
		while ((userInput = input.next()) != "close") {
			String location;
			System.out.println("Location" + vendingMachineList.getVendingMachine(activeUser.getLocation()).getVendingMachineId());
			System.out.println(vend.toString());
			System.out.println(activeUser.toString());
		    switch(userInput) {
				default:
					// textInput.add("=>" + userInput);
					// User Info
					// Inventory
					System.out.println(vend.getVendingMachineName() + ": Do Something (enter help for options)!");
					break;
				case "balance":
					System.out.println("You have " + activeUser.getCash() + " units left to spend. Good for you.");
					break;
				case "buy":
		        	System.out.println("Enter a a value A-D, 1-5. Like D3 or C5.");
		        	location = String.valueOf(input.passNext());
		        	StockItem stockItem = vend.getInventory().getStockItemAt(location);
		        	if (stockItem != null) {
		        		int cost = stockItem.getQuote(1);
		        		if (activeUser.getCash() > cost) {
		        			activeUser.addItem(stockItem);
		        			activeUser.setCash(-1 * cost);
		        			stockItem.takeFromStock(1);
		        		}
		        	}
					break;
				case "login":
					System.out.println("Command not implemented");
					break;
				case "help":
					System.out.println(vend.getVendingMachineName() + " says ...");
					System.out.println("Available commands are ..");
					System.out.println("balance -- Check your remaining balance.");
					System.out.println("buy -- purchase an item from the vending machine.");
					System.out.println("login -- Login to another account.");
					System.out.println("exit -- Exit the application.");
					System.out.println("history -- Review your 20 most previous queries.");
					System.out.println("help -- See this menu.");
					System.out.println("rename -- rename the vending machine");
					System.out.println("status -- Check your status effects.");
					System.out.println("stock -- add gum, water, and snacks to the vending machine.");
					System.out.println("use -- use an item from your inventory");
					System.out.println(": Do Something (enter help for options)!");
					break;
		        case "history":
		        	input.printHistory();
		        	break;
		        case "exit":
		        	break shell;
		        case "rename":
		        	System.out.println("Enter a new name for the vending machine");
		        	vend.setVendingMachineName(String.valueOf(input.passNext()));
		        case "save":
		        	try {
		        		save.setUserList(userList);
		        		save.setVendingMachineList(vendingMachineList);
						save.writeToFile(SAVE_FILE_NAME);
						System.out.println("Saved!");
					} catch (IOException e) {
						System.out.println("Failed to save!");
						e.printStackTrace();
					}
		        	break;
		        case "status":
		        	activeUser.getStatus();
		        	break;
		        case "stock":
		        	System.out.println("Enter an item. Like gum, water or snacks.");
		        	String item = String.valueOf(input.passNext());
		        	System.out.println("Enter the quantity to add greater than zero please!");
		        	int quantity = Integer.valueOf(input.passNext());
		        	System.out.println("Enter a a value A-D, 1-5. Like D3 or C5.");
		        	location = String.valueOf(input.passNext());
		        	switch (item) {
		        		case "gum":
		        			Item gum = new Gum();
		        			StockItem moreGum = new StockItem(gum, quantity);
		        			vend.getInventory().put(location, moreGum);
		        			break;
		        		case "water":
		        			Item water = new BottledWater();
		        			StockItem moreWater = new StockItem(water, quantity);
		        			vend.getInventory().put(location, moreWater);
		        			break;
		        		case "snacks":
		        			Item snacks= new Snack();
		        			StockItem moreSnacks = new StockItem(snacks, quantity);
		        			vend.getInventory().put(location, moreSnacks);
		        			break;
		        	}
		        	break;
		        case "use":
		        	System.out.println("Use an item from your inventory.");
		        	System.out.println("Enter a number 1 through 5.");
		        	int selection = Integer.valueOf(input.passNext());
		        	if (selection  > 0 && selection < 6) {
		        		activeUser.useItem(selection);
		        	}
		        	break;
		    }
		}
    }
}
