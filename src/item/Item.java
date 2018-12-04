package item;
import java.util.ArrayList;
import rules.PricingRules;

/*
 * Item: Represents one Item, that can be taken in by Checkout.
 * It mainly contains its own name.
 */


public class Item {
	private String name;	

	public Item(String name) {
		this.name = name;		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
