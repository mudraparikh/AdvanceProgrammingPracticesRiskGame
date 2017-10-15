package riskModels.continent;

import riskModels.country.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Continent Bean class that will hold the properties of continent.
 *
 * @author prashantp95
 */
public class Continent {

    public  String continentName;
    public List<Country> memberCountriesList = new ArrayList<Country>();
    public int numberOfTerritories;

    @Override
    public boolean equals(Object continentObject) {
        String continentName = ((Continent) continentObject).getContinentName();
        return continentName.equals(this.getContinentName());
    }

    @Override
    public int hashCode() {
        return this.continentName.hashCode();
    }
    
    public Continent(String continentName) {
        this.continentName = continentName;
    }

    public Continent() {
        //default constructor
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

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

}
