package riskControllers.country;

import java.io.BufferedReader;
import java.util.List;
import riskModels.country.Country;
import riskModels.country.CountryModel;


public class CountryController {
	CountryModel model;
	
	public CountryController() {
		this.model = new CountryModel();
	}

	public List<Country> readCountries(BufferedReader br){
		return model.readCountries(br);
	}
}
