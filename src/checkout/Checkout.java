package checkout;

import rules.*;
import java.io.FileNotFoundException;
import item.*;

public class Checkout {	
	//This class implements the checkout. 
	//The constructor expects a filename pointing to one of the config files under src/RuleConfigFiles 
	private PricingRulesManager rulesManager;
	public Checkout(String pricingRulesFilename) {
		try {
			rulesManager = new PricingRulesManager(pricingRulesFilename);
		} catch (FileNotFoundException e) {
			System.err.println("Sorry. Pricing rules file '" + pricingRulesFilename + "' not found. Checkout won't work.");
			
		}		
	}	
	
	public void scan(Item item) {
		// "Scanns" an item. Checkout will remember the item and amount purchased		
	}
	
	public float total() {
		//This method can be called at every time to get the total price of all item scanned
		// ToDo: catch if rulesManager is null 
		if(rulesManager == null) {
			System.err.println("Pricing rules not set. Please load pricing rules file first.");
			return (float) 0.0;
		}
		return (float) 0.0;
	}		
	public void reset() {
		// reset one cashier session, resets total price and already scanned items		
	}	
}
