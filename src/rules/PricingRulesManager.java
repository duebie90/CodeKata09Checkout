package rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PricingRulesManager {
	public PricingRulesManager(String pricingRulesFilename) throws FileNotFoundException{
		File file = new File(pricingRulesFilename);
		Scanner input = new Scanner(file);		
	}
	
	
}
