package codeKataCheckout;

import checkout.Checkout;

public class Application {

	public static void main(String[] args) {
		String pricing_rules = "kataRules.txt";
		Checkout co = new Checkout(pricing_rules);		
		System.out.println(co.total());
		
	}

}
