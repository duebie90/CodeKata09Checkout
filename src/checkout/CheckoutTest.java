package checkout;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import checkout.Checkout;
import item.Item;

public class CheckoutTest {

	float price(String goods) {
		// Helper method for testing to get a total price from a string like "AAB" 
		String pricing_rules = "kataRules.json";
		Checkout co = new Checkout(pricing_rules);		
		for(char c: goods.toCharArray()) {
			Item item = new Item(Character.toString(c));
			co.scan(item);
		}
		float result = co.total(); 
		return result;
	}
	
	@Test
	public void testTotals() {
		assertEquals(0, price(""));
		assertEquals( 50, price("A"));
	    assertEquals( 80, price("AB"));
	    assertEquals(115, price("CDBA"));

	    assertEquals(100, price("AA"));
	    assertEquals(130, price("AAA"));
	    assertEquals(180, price("AAAA"));
	    assertEquals(230, price("AAAAA"));
	    assertEquals(260, price("AAAAAA"));

	    assertEquals(160, price("AAAB"));
	    assertEquals(175, price("AAABB"));
	    assertEquals(190, price("AAABBD"));
	    assertEquals(190, price("DABABA"));
	}

	@Test
	public void testNullOnUnknownItemName() {
		assertEquals(0, price("X"));
		assertEquals(0, price("XY"));
		assertEquals(0, price("XYZ"));		
		assertEquals( 50, price("AX"));
		assertEquals( 50, price("AXY"));
	}
	
	
	@Test
	public void testCheckout() {
		String pricing_rules = "kataRules.json";
		Checkout co = new Checkout(pricing_rules);				
	}
	
	@Test
	public void testFileNotFound() {
		// A missing file should not lead to an exception. It is catched before.
		// There should be an error message only.
		String pricing_rules = "xyz.json";
		Checkout co = new Checkout(pricing_rules);		
	}
	
	@Test
	public void testMalformedJson() {
		// A missing file should not lead to an exception. It is catched before.
		// There should be an error message only.
		String pricing_rules = "malformed.json";
		Checkout co = new Checkout(pricing_rules);		
	}
	@Test
	public void testDifferentNumberTypesJson(){		
		// Checkout and thus PricingRulesMananger should be able to cope with different kinds of pricings in the json
		//(strin, long,  double) ("5", 5, 5.0)
		String pricing_rules = "kataRulesDifferentNumberTypes.json";
		Checkout co = new Checkout(pricing_rules);		
	}

}
