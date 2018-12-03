package codeKataCheckout;

import checkout.Checkout;
import item.*;

public class Application {

	public static void main(String[] args) {
		String pricing_rules = "kataRules.json";
		Checkout co = new Checkout(pricing_rules);
		
		// create some items
		Item i0 = new Item("A");
		Item i1 = new Item("B");
		Item i2 = new Item("C");
		Item i3 = new Item("Beer");
		
		co.scan(i0);		
		co.scan(i1);		
		co.scan(i1);
		co.scan(i1);
		co.scan(i2);
		co.scan(i0);
		co.reset();
		co.scan(i0);
		
		
		
		System.out.println("Total amount due: " + co.total());
		
	}

}
