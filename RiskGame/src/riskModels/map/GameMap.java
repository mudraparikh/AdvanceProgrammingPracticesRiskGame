package riskModels.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import riskModels.continent.Continent;
import riskModels.country.Country;
/**
 * This class holds the properties to create graph from map file
 * @author prashantp95
 *
 */
public class GameMap {
	
	public HashMap<Country, List<Country>> countryAndNeighborsMap = new HashMap<>();
	public List<Continent> continentList = new ArrayList<>();
	public HashMap<Country, List<Country>> getCountryAndNeighborsMap()
	{
		return countryAndNeighborsMap;
	}
	public void setCountryAndNeighborsMap(HashMap<Country, List<Country>> countryAndNeighborsMap)
	{
		this.countryAndNeighborsMap = countryAndNeighborsMap;
	}
	public List<Continent> getContinentList()
	{
		return continentList;
	}
	public void setContinentList(List<Continent> continentList) 
	{
		this.continentList = continentList;
	}
}
