package riskModels.continent;

import java.util.ArrayList;
import java.util.List;

import riskModels.country.Country;
/**
 * Continent Bean class that will hold the properties of continent.
 * @author prashantp95
 *
 */
public class Continent {
	
	public static String continentName;
	public List<Country> memberCountriesList = new ArrayList<Country>();
	public int numberOfTerritories;
	
	public static String getContinentName() 
	{
		return continentName;
	}
	public void setContinentName(String continentName) 
	{
		this.continentName = continentName;
	}
	public List<Country> getMemberCountriesList()
	{
		return memberCountriesList;
	}
	public void setMemberCountriesList(List<Country> memberCountriesList)
	{
		this.memberCountriesList = memberCountriesList;
	}
	public int getNumberOfTerritories() {
		return numberOfTerritories;
	}
	public void setNumberOfTerritories(int nummberOfTerritories) {
		this.numberOfTerritories = nummberOfTerritories;
	}
	
}
