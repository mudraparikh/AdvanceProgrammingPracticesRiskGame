package riskModels.country;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import riskControllers.country.*;
import util.RiskGameUtil;
import riskModels.country.CountryConstants;
/**
 * Class
 * @author prashantp95
 *
 */
public class CountryModel  {
	/**
	 * This method will  read all lines under [territory] from map and return list of countries with in it. 
	 * @param textBuffer :text buffer to be read
	 * @return List of Country Objects
	 */
	public  List<Country> readCountries(BufferedReader textBuffer){
		String Territories;
		List <Country> countryList = new  ArrayList<Country>();
		try {
			while((Territories=textBuffer.readLine())!=null && !Territories.startsWith(CountryConstants.bracket))
			 {
					if(RiskGameUtil.checkNullString(Territories)) {
					String countryName,continentName=null;
					int startPixel,endPixel=0;
					List<Country> neighbourNodes = new ArrayList<Country>();
					String [] terrProperties = Territories.split(CountryConstants.comma);
					countryName=terrProperties[0];
					continentName=terrProperties[3];
					startPixel=Integer.parseInt(terrProperties[1]);
					endPixel=Integer.parseInt(terrProperties[2]);
					Country country = new Country(countryName,startPixel,endPixel,continentName);
				
				for(int i=4;i<=terrProperties.length-1;i++) 
				{
					String neighbourCountryName =terrProperties[i];
					Country neighbour = new Country(neighbourCountryName);
					neighbourNodes.add(neighbour);
				}
			country.setNeighborNodes(neighbourNodes); 
			countryList.add(country);
			}
			
		   } 
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return countryList;
}

}
