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

public class PricingRulesManager {
	HashMap<String, PricingRules>pricingRules = new HashMap<String, PricingRules>();

	public PricingRulesManager(String pricingRulesFilename) throws FileNotFoundException{
		File file = new File("RuleConfigFiles\\" + pricingRulesFilename);
		FileReader fileReader = new FileReader("RuleConfigFiles\\" + pricingRulesFilename);	
		updatePricingRulesFromJson(fileReader);
	}
	
	private void updatePricingRulesFromJson(FileReader fileReader) {
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
				for( Object amountPurchased: pricesJson.keySet().toArray()) {
					// Sadly detour via string seems to be necessary to achieve constant datatype of numbers
					// ToDo: find better way
					Float price = Float.parseFloat(pricesJson.get(amountPurchased).toString());						
					itemPricingRules.addRule(Integer.parseInt((String) amountPurchased), price);					
				}	
				// create the item representation								
				pricingRules.put((String)itemJson.get("name"), itemPricingRules);
				System.out.println("item prices " + itemPricingRules);				
			}		
	
		} catch (IOException e) {
			System.err.println("Could not read from file.");			
		} catch (ParseException e) {
			System.err.println("Could not parse json file. Invalid format.");			
		}		
	}
	
	public float getUnitPriceByAmount(String itemName, int amount) {
		if(this.pricingRules.containsKey(itemName)) {
			PricingRules itemPricingRules = this.pricingRules.get(itemName);
			return itemPricingRules.getPriceByAmount(amount);				
		}else {
			System.out.println("No price information about this items available");
			return (float) 0.0;
		}
	}
}
