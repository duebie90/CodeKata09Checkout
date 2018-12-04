package rules;

import java.util.Collections;
import java.util.HashMap;

/*
 * PricingRules: Stores multiple pricing rules for an article, which consist of an amount--> Price matching.
 */
public class PricingRules implements Rule {
	//PricingRules represents all pricing rules of an article, taking the purchased amount under account
	HashMap<Integer, Float> pricings = new HashMap<Integer, Float>(); 
	
	public void addRule(int amount, float price) {
		this.pricings.put(amount, price);
	}
	
	
	@Override
	public float getPriceByAmount(int amount) {
		// With a given amount of purchased articles, the total (eventually discounted) price is returned.
		// It's assumed to be possible, to buy a discounted article multiple times.
		float price = (float) 0.0;
		if(this.pricings.containsKey(amount)) {
			// The bought amount of articles has a fixed price
			price = this.pricings.get(amount);
		}else {
			// the price is calculated from the possible discounts and single-piece prices
			int maxAmount = getMaximumPossibleDiscountAmount(amount);
			if(maxAmount > 1) {
				float priceDiscounted = this.pricings.get(maxAmount);
				int amountDiscounted = amount/ maxAmount;				
				price = (amount - amountDiscounted*maxAmount)*this.pricings.get(1) + amountDiscounted*priceDiscounted;
			}else {
				// we will just use the single price
				price = this.pricings.get(1)*amount;
			}			
		}
		return price;	
	}
	
	private int getMaximumPossibleDiscountAmount(int amountPurchased) {
		// Goes through known pricings finds the highest possible amount
		int maxAmountKnown = 0;		
		for(int amountKnown: this.pricings.keySet()) {
			if(amountKnown > maxAmountKnown && amountPurchased > amountKnown) {
				maxAmountKnown = amountKnown;
				
			}				
		}
		return maxAmountKnown;
		
	}
	
	
}
