package codeKataCheckout;

import checkout.Checkout;
import item.*;
/**
 * 
 * Main Class to run Checkout.
 *
 */


public class Application {
	
	public static void main(String[] args) {
		// A filename for a .json file with pricing rules is expected by Checkout
		String pricing_rules = "kataRules.json";		
		Checkout co = new Checkout(pricing_rules);
		
		// create some items
		Item i0 = new Item("A");
		Item i1 = new Item("B");
		Item i2 = new Item("C");
		Item i3 = new Item("Beer");
		
		// Scan them with the checkout
		co.scan(i0);		
		co.scan(i1);		
		co.scan(i1);
		// Checkout process can be reseted
		co.reset();
		co.scan(i1);
		co.scan(i2);
		co.scan(i0);		
		co.scan(i0);
		co.scan(i0);
		co.scan(i0);
		
		// In the end display the total amount		
		System.out.println("Total amount due: " + co.total());
		
	}

}
