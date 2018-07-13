package projectzero.entities;

import java.util.HashMap;

import projectzero.entities.StockItem;
import projectzero.entities.Item;

public class Inventory {

    private HashMap<String, StockItem> inventory;
    
    public Inventory() {
	this.inventory = new HashMap<String, StockItem>();
    }

    public Inventory(HashMap<String, StockItem> inventory) {
	this.inventory = inventory;
    }

    public boolean addItem(String key, StockItem stockItem) {

	boolean isSuccess = false;

	if (this.inventory.containsKey(key) == true ) {
	    return isSuccess;
	}

	isSuccess = true;
	this.inventory.put(key, stockItem);

	return isSuccess;
    }

    public HashMap<String, StockItem> getInventory() {
	return this.inventory;
    }

    public void setInventory(HashMap<String, StockItem> inventory) {
	this.inventory = inventory;
    }

    public void put(String key, StockItem stockItem) {
	if (stockItem != null) {
	    this.inventory.put(key, stockItem);
	}
    }
}