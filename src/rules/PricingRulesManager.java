package rules;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.parser.*;

import item.Item;

import org.json.simple.*;
/*
 * PricingRulesManager: Parses the given .json file and if successful creates PricingRules-object for each configured item
 * 
 */
public class PricingRulesManager {
	HashMap<String, PricingRules>pricingRules = new HashMap<String, PricingRules>();

	public PricingRulesManager(String pricingRulesFilename) throws FileNotFoundException{
		File file = new File("RuleConfigFiles\\" + pricingRulesFilename);
		FileReader fileReader = new FileReader("RuleConfigFiles\\" + pricingRulesFilename);	
		updatePricingRulesFromJson(fileReader);
	}
	
	private void updatePricingRulesFromJson(FileReader fileReader) {
		// Parsing the json file and generate PricingRules for each item.
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(fileReader);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray itemsArray = (JSONArray)jsonObject.get("items");
			Iterator<JSONObject> itemsIterator = itemsArray.iterator();
			while(itemsIterator.hasNext()) {
				// Go through all items described in the json file
				JSONObject itemJson = itemsIterator.next();
				JSONObject pricesJson = (JSONObject)itemJson.get("prices");
				// get the individual pricing strategies				
				PricingRules itemPricingRules = new PricingRules();
				for( Object amountPurchased: pricesJson.keySet()) {
					// Make detour via string to be able to reader either double, int or string prices
					float price = Float.parseFloat(pricesJson.get(amountPurchased).toString());										
					itemPricingRules.addRule(Integer.parseInt((String) amountPurchased), price);					
				}	
				// create the item representation								
				pricingRules.put((String)itemJson.get("name"), itemPricingRules);								
			}		
	
		} catch (IOException e) {
			System.err.println("Could not read from file.");			
		} catch (ParseException e) {
			System.err.println("Could not parse json file. Invalid format.");			
		}		
	}
	
	public float getUnitPriceByAmount(String itemName, int amount) {
		// Returns the total price of a certain amount of an article taking pricing rules/ discounts under account.
		// The actual pricing is fetched from the corresponding PricingRules-Object if available otherwise 0.0.
		if(this.pricingRules.containsKey(itemName)) {
			PricingRules itemPricingRules = this.pricingRules.get(itemName);
			return itemPricingRules.getPriceByAmount(amount);				
		}else {
			System.out.println("No price information about this items available");
			return (float) 0.0;
		}
	}
}
