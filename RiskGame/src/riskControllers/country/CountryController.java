package riskControllers.country;

import riskModels.country.Country;
import riskModels.country.CountryModel;

import java.io.BufferedReader;
import java.util.List;

/**
 * This class will handle events between view and model during any event related to country
 * @author hnath
 *
 */
public class CountryController {
    CountryModel model;

    public CountryController() {
        this.model = new CountryModel();
    }

}
