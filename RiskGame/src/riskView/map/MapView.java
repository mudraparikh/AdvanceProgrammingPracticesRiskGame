package riskView.map;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;

public class MapView extends JPanel 
{


public static void main(String[] args) {
//	MapView h=new MapView();
	
	MapModel mapmodel = new MapModel();
	GameMap gameMapDetails = mapmodel.readMapFile("C:\\CanadaMap.txt");
	for(Continent continent: gameMapDetails.getContinentList()) 
    {
   	 System.out.println("Name"+continent.getContinentName()+"--"+continent.getNumberOfTerritories());
    }
    Iterator it = gameMapDetails.getCountryAndNeighborsMap().entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        Country country=(Country) pair.getKey();
	        List<Country> neighbours = (List<Country>) pair.getValue();
	        System.out.println("------"+country.getCountryName()+"-----"+country.getBelongsToContinet());
	        for(Country neighbour:neighbours) 
	        {
	        	System.out.println(neighbour.getCountryName()+" "+neighbour.getBelongsToContinet());
	        }
	        
	    }
}

}
