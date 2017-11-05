package riskModels.continent;

import riskModels.country.Country;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Continent Bean class that will hold the properties of continent.
 *
 * @author prashantp95
 */
public class Continent {

    public String continentName;
    public List<Country> memberCountriesList = new ArrayList<Country>();
    public int numberOfTerritories;
    public Color color;
    public int controlValue;
    
    /**
     * This constructor will create continent object based provided continent name
     * @param continentName name of the continent
     */
    public Continent(String continentName) {
        this.continentName = continentName;
        this.controlValue = 1;
    }

    /**
     * default constructor
     */
    public Continent() {
      
    }

    /**
	 * Over ride equals method in order to compare compare objects based on continent name not continent objects
	 */
    @Override
    public boolean equals(Object continentObject) {
        String continentName = ((Continent) continentObject).getContinentName();
        return continentName.equals(this.getContinentName());
    }

    /**
     * Overriding hashCode method.
     * 
     */
    @Override
    public int hashCode() {
        return this.continentName.hashCode();
    }
	/**
	 * This method will return continent name
	 * @return name of the continent (String format)
	 */
    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * getter method to get the list of the countries belong to that particular continent
     * @return memberCountriesList list of countries
     */
    public List<Country> getMemberCountriesList() {
        return memberCountriesList;
    }

    public void setMemberCountriesList(List<Country> memberCountriesList) {
        this.memberCountriesList = memberCountriesList;
    }

    public int getNumberOfTerritories() {
        return numberOfTerritories;
    }

    public void setNumberOfTerritories(int numberOfTerritories) {
        this.numberOfTerritories = numberOfTerritories;
    }

    public int getControlValue() {
        return controlValue;
    }

    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }
    
    /**
     * getter method for player who own the continent
     * @return color color of the continent player occupied
     */
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
