package checkout;

import rules.*;
import item.*;

public class Checkout {	
	//This class implements the checkout. 
	//The constructor expects a filename pointing to one of the config files under src/RuleConfigFiles 
	private PricingRulesManager rulesManager;
	public Checkout(String pricingRulesFilename) {
		rulesManager = new PricingRulesManager(pricingRulesFilename);
		System.out.println("Checkout created with rules filename " + pricingRulesFilename);
		
	}	
	
	public void scan(Item item) {
		// "Scanns" an item. Checkout will remember the item and amount purchased		
	}
	
	public float total() {
		//This method can be called at every time to get the total price of all item scanned
		return (float) 0.0;
	}		
	public void reset() {
		// reset one cashier session, resets total price and already scanned items		
	}	
}
