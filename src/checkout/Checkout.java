package checkout;

import java.io.FileNotFoundException;
import java.util.HashMap;
import item.*;
import rules.*;


public class Checkout {	
	//This class implements the checkout. 
	//The constructor expects a filename pointing to one of the .json files under src/RuleConfigFiles 
	private PricingRulesManager rulesManager;
	private HashMap<Item, Integer> mapItems2Amount = new HashMap<Item, Integer>();
	
	public Checkout(String pricingRulesFilename) {
		try {
			rulesManager = new PricingRulesManager(pricingRulesFilename);
		} catch (FileNotFoundException e) {
			System.err.println("Sorry. Pricing rules file '" + pricingRulesFilename + "' not found. Checkout won't work.");
			
		}		
	}	
	
	public void scan(Item item) {
		// "Scanns" an item. Checkout will remember the item and amount purchased	
		int count = 1;
		if(mapItems2Amount.containsKey(item)) {
			//increment the stored count of scanned units of the item			
			count = this.mapItems2Amount.get(item);
			count++;
		}
		this.mapItems2Amount.put(item, count);		
				
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
