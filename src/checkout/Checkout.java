package checkout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import item.*;
import rules.*;


public class Checkout {	
	//This class implements the checkout. 
	//The constructor expects a filename pointing to one of the config files under src/RuleConfigFiles 
	private PricingRulesManager rulesManager;
	private ArrayList<Item> itemsScanned = new ArrayList<Item>();
	
	public Checkout(String pricingRulesFilename) {
		try {
			rulesManager = new PricingRulesManager(pricingRulesFilename);
		} catch (FileNotFoundException e) {
			System.err.println("Sorry. Pricing rules file '" + pricingRulesFilename + "' not found. Checkout won't work.");
			
		}		
	}	
	
	public void scan(Item item) {
		// "Scanns" an item. Checkout will remember the item and amount purchased	
		itemsScanned.add(item);
	}
	
	public float total() {
		//This method can be called at every time to get the total price of all item scanned
		// ToDo: catch if rulesManager is null 
		if(rulesManager == null) {
			System.err.println("Pricing rules not set. Please load pricing rules file first.");
			return (float) 0.0;
		}		
		int i = 0;
		// So far only a test
		for(Item item: itemsScanned) {			
			String itemName = item.getName();
			System.out.println("Item "+  i + ": " + item.getName());
			float price = this.rulesManager.getUnitPriceByAmount(itemName, 1);
			System.out.println("Price for 1: " + price);
//			price = this.rulesManager.getUnitPriceByAmount(itemName, 2);
//			System.out.println("Price for 2: " + price);
//			price = this.rulesManager.getUnitPriceByAmount(itemName, 3);
//			System.out.println("Price for 3: " + price);
//			float price = this.rulesManager.getUnitPriceByAmount(itemName, 4);
//			System.out.println("Price for 4: " + price);
			i ++;
		}
		float totalPrice = (float) 0.0;
		System.out.println("Total Price: " + totalPrice);
		return totalPrice;
	}		
	public void reset() {
		// reset one cashier session, resets total price and already scanned items		
	}	
}
