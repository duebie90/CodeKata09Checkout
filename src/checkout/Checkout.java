package checkout;

import java.io.FileNotFoundException;
import java.util.HashMap;
import item.*;
import rules.*;


public class Checkout {	
	/**Checkout: Implements the checkout. 
	*The constructor expects a filename pointing to one of the .json files under src/RuleConfigFiles
	If no filename is given it prints an error message and won't do anything
	**/
	private PricingRulesManager rulesManager;
	private HashMap<Item, Integer> mapItems2Amount = new HashMap<Item, Integer>();
	
	public Checkout(String pricingRulesFilename) {
		// Preparing the PricingRulesManager 
		updatePricingRulesManager(pricingRulesFilename);
	}
	
	public void setPricingRules(String pricingRulesFilename) {
		// The pricing rules can be set later on but checkout will be reseted
		reset();
		updatePricingRulesManager(pricingRulesFilename);
	}
	
	private void updatePricingRulesManager(String pricingRulesFilename) {
		// Create the PricingRulesManager with the given filename
		try {
			rulesManager = new PricingRulesManager(pricingRulesFilename);
		} catch (FileNotFoundException e) {
			System.err.println("Sorry. Pricing rules file '" + pricingRulesFilename + "' not found. Checkout won't work.");			
		}	
	}
	
	private void incrementPurchasedCounter(Item item) {
		// Increments the counter of (distinct) scanned items
		int count = this.mapItems2Amount.get(item);
		count++;
		this.mapItems2Amount.put(item, count);
	}
	
	public void scan(Item item) {
		// "Scanns" an item. Checkout will remember the item and amount purchased	
		if(mapItems2Amount.containsKey(item)) {
			//increment the stored count of scanned units of the item			
			incrementPurchasedCounter(item);
		} else {
			// Check if there is an already scanned item with the same name (but a different instance of the item)
			for(Item purchasedItem: this.mapItems2Amount.keySet()) {
				if(purchasedItem.getName().equals(item.getName())) {
					incrementPurchasedCounter(purchasedItem);
					return;
				}
			}
			// Items like that have not been purchased so far
			this.mapItems2Amount.put(item, 1);
		} 								
	}
	
	public float total() {
		//This method can be called at every time to get the total price of all item scanned
		if(rulesManager == null) {
			System.err.println("Pricing rules not set. Please load pricing rules file first.");
			return (float) 0.0;
		}		
		int i = 0;
		// So far only a test
		float sum = 0;				
		for(Item item: this.mapItems2Amount.keySet()) {			
			String itemName = item.getName();
			int amountScanned = this.mapItems2Amount.get(item);
			float price = this.rulesManager.getUnitPriceByAmount(itemName, amountScanned);
			System.out.println("Item "+  i + ": " + item.getName() + " was purchased " + amountScanned + " times: " + price);		
			
			sum += price;			
			i ++;
		}		
		return sum;
	}		
	public void reset() {
		// reset one cashier session, resets total price and already scanned items	
		System.out.println("Resetting checkout process.");
		this.mapItems2Amount.clear();
	}	
}
