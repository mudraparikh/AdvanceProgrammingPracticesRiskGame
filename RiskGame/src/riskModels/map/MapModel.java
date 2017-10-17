
package riskModels.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.country.CountryConstants;
import util.RiskGameUtil;

public class MapModel {
    /**
     * This method will assignContinent to Neighbor countries
     *
     * @param countryAndNeighbors It is same List returned while reading [Territories] part
     * @return HashMap ,Which will have Country as a key and List of Country (neighbors) as a Value.it will help to create graph
     */
    public static HashMap<Country, List<Country>> assignContinentToNeighbors(List<Country> countryAndNeighbors) {
        HashMap<String, String> countryContinentMap = new HashMap<String, String>();
        HashMap<Country, List<Country>> countryAndNeighbours = new HashMap<Country, List<Country>>();

        for (Country country : countryAndNeighbors) {
            countryContinentMap.put(country.getCountryName(), country.getBelongsToContinent());
        }
        for (Country country : countryAndNeighbors) {
            List<Country> neighbourList = new ArrayList<Country>();
            for (Country neighbour : country.getNeighborNodes()) {
                String continentName = countryContinentMap.get(neighbour.getCountryName());
                neighbour.setBelongsToContinent(continentName);
                neighbourList.add(neighbour);
            }
            countryAndNeighbours.put(country, neighbourList);
        }
        return countryAndNeighbours;
    }

    /**
     * This method will read [Territories] part of map file
     *
     * @param bufferReaderForFile
     * @return List of Country.Every single object of country will have countryname,continentname,start/end pixels;
     */
    private static List<Country> readTerritories(BufferedReader bufferReaderForFile) {
        String Territories;
        List<Country> countryList = new ArrayList<Country>();
        try {
            while ((Territories = bufferReaderForFile.readLine()) != null && !Territories.startsWith("[")) {
                if (RiskGameUtil.checkNullString(Territories)) {
                    String countryName, continentName = null;
                    int startPixel, endPixel = 0;
                    List<Country> neighbourNodes = new ArrayList<Country>();
                    String[] terrProperties = Territories.split(",");
                    countryName = terrProperties[0];
                    continentName = terrProperties[3];
                    startPixel = Integer.parseInt(terrProperties[1]);
                    endPixel = Integer.parseInt(terrProperties[2]);
                    Country country = new Country(countryName, startPixel, endPixel, continentName);

                    for (int i = 4; i <= terrProperties.length - 1; i++) {
                        String neighbourCountryName = terrProperties[i];
                        Country neighbour = new Country(neighbourCountryName);
                        neighbourNodes.add(neighbour);
                    }
                    country.setNeighborNodes(neighbourNodes);
                    countryList.add(country);
                }

            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();

        }
        return countryList;
    }

    /**
     * This method will read the continet part of map file
     *
     * @param bufferReaderForFile BufferReader object that read the .map file for continent
     * @return List of Continent. every single object of the list contains continentName and number of countries it hold.
     */
    public static List<Continent> readContinents(BufferedReader bufferReaderForFile) {
        String Continents;
        List<Continent> continentsList = new ArrayList<Continent>();
        try {
            while ((Continents = bufferReaderForFile.readLine()) != null && !Continents.startsWith("[")) {
                if (RiskGameUtil.checkNullString(Continents)) {
                    Continent continents = new Continent();
                    List<Integer> allContinents = new ArrayList<Integer>();
                    String[] ConProperties = Continents.split("=");
                    continents.setContinentName(ConProperties[0].trim());
                    continents.setNumberOfTerritories(Integer.parseInt(ConProperties[1].trim()));
                    continentsList.add(continents);
                    System.out.println(ConProperties[1]);
                }
                bufferReaderForFile.mark(0);
            }
            bufferReaderForFile.reset();
        } catch (NumberFormatException | IOException e) {

            e.printStackTrace();
        }
        return continentsList;
    }

    /**
     * This method will read the mapfile and provide data to creategraph
     *
     *
     * @return Function will return the map details obj
     */
    public int mapFileInputParse(){
        GameMap t = readMapFile("/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/src/riskModels/map/canada.map");
        System.out.println(""+t);
        return 1;
    }
    public GameMap readMapFile(String filePath) {
        BufferedReader bufferReaderForFile = null;
         boolean isMAPpresent=false; //to check [MAP] is available in file or not
         boolean isContinentPresent=false;//to check [Continent] is available in file or not
         boolean isTerritoryPresent=false;//to check [Territory] is available in file or not
        GameMap mapDetails = new GameMap();
        try {
            File file = new File(filePath);
            System.out.println(file.exists());
            System.out.println(new File(".").getAbsoluteFile());
            System.out.println(System.getProperty("user.dir"));
            String validationMessage=validateFile(file);
            if(!validationMessage.equalsIgnoreCase("Valid File")) {
            	mapDetails.setCorrectMap(false);
            	mapDetails.setErrorMessage(validationMessage);
            	return mapDetails;
            }
            bufferReaderForFile = new BufferedReader(new FileReader(file));
            String st, maps, Continents, Territories;
            while ((st = bufferReaderForFile.readLine()) != null) {
                if (st.startsWith("[")) {
                	HashMap<String,String> mapDetail = new HashMap<>();
                    String id = st.substring(st.indexOf("[") + 1, st.indexOf("]"));
                    if (id.equalsIgnoreCase("Map")) {
                    	isMAPpresent=true;
                        while ((maps = bufferReaderForFile.readLine()) != null && !maps.startsWith("[")) {
                            if (RiskGameUtil.checkNullString(maps)) {
                                System.out.println(maps);
                                String[] mapsEntry = maps.split("=");
                                mapDetail.put(mapsEntry[0],mapsEntry[1]);
                                bufferReaderForFile.mark(0);
                            }
                        }
                        bufferReaderForFile.reset();
                        mapDetails.setMapDetail(mapDetail);
                    }
                    if (id.equalsIgnoreCase("Continents")) {
                    	isContinentPresent=true;
                    	if(isMAPpresent) {
                    		 List<Continent> listOfContinents = MapModel.readContinents(bufferReaderForFile);
                             mapDetails.setContinentList(listOfContinents);
                             System.out.println("Reading of Continents Completed");
                    	}
                       
                    }

                    if (id.equalsIgnoreCase("Territories")) {
                    	isTerritoryPresent=true;
                    	if(isMAPpresent && isTerritoryPresent) {
                        List<Country> countryAndNeighbor = MapModel.readTerritories(bufferReaderForFile);
                        HashMap<Country, List<Country>> graphReadyMap = MapModel.assignContinentToNeighbors(countryAndNeighbor);
                        System.out.println("Reading of Territories Completed");
                        for (Object o : graphReadyMap.entrySet()) {
                            Map.Entry pair = (Map.Entry) o;
                            Country country = (Country) pair.getKey();
                            List<Country> neighbours = (List<Country>) pair.getValue();
                        }
                        mapDetails.setCountryAndNeighborsMap(graphReadyMap);
                    }
                    }
                }
            }
            
            if(isMAPpresent && isContinentPresent && isTerritoryPresent){
            	System.out.println("Map Continents and Territories tags are present");
            }
            else {
            	mapDetails.setCorrectMap(false);
            	mapDetails.setErrorMessage("MAP or Continents or Territories tags not present");
            	return mapDetails;
            }
            validateMap(mapDetails);
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return mapDetails;

    }
    /**
     * This method will do validation on the details we parsed from the .map file
     * @param mapDetails mapDetials that we have parsed from .map file 
     * @return mapDetails with correct error message.
     */
    public  GameMap validateMap(GameMap mapDetails) {
    	Iterator it = mapDetails.getCountryAndNeighborsMap().entrySet().iterator();
    	while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Country country=(Country) pair.getKey();
	        List<Country> neighbours = (List<Country>) pair.getValue();
	        if(neighbours.isEmpty()||neighbours==null) {
	        	mapDetails.setCorrectMap(false);
	        	mapDetails.setErrorMessage(country.getCountryName()+" does not have any neighbor nodes");
	        }
	     }
    	return mapDetails;
    }	
	

	/**
     * This method will perform validation of provided input file 
     * @param file 
     * @return error/success Message 
     */
public String validateFile(File file) {
		
		//check if file is present or not
		 if(!file.exists()){
			return "File does not exists";
		}
		 String name = file.getName();
		 String extension= name.substring(name.lastIndexOf(".") + 1);
		 //check if user has selected  file with .map extension 
		 if(!extension.equalsIgnoreCase("map")){
			 return "Invalid extension of  File Please provide correct file";
		 }
		 //check if file selected by user is empty or not
		 if(file.length()==0){
			 return "File is empty please select correct file";
		 }
		return "Valid File" ;
		   
		
	}
/**
 * This method will remove the country
 * @param country - Country that to be removed
 * @param gameMap - Existing GameMap From where country will be removed
 */
public void removeCountry(String country,GameMap gameMap) {
		Country  countryToRemove = new Country(country);
		List<Country> neiborCountryList = gameMap.getCountryAndNeighborsMap().get(countryToRemove);
		// removing country from the neighbor list of other countries.
		for(Country neiborCountry: neiborCountryList){
		List<Country> removeCountyNeiborList=gameMap.getCountryAndNeighborsMap().get(neiborCountry);
		List<Country> updatedNeiborList = new ArrayList<>();
		for(Country countryRemoveFromNeibor:removeCountyNeiborList) {
			if(!countryRemoveFromNeibor.getCountryName().equalsIgnoreCase(country)) {
				updatedNeiborList.add(countryRemoveFromNeibor);
			}
		}
		gameMap.getCountryAndNeighborsMap().put(neiborCountry, updatedNeiborList); // this will replace existing  key-value pair. 
	}
		// finally removing country from the map details
	gameMap.getCountryAndNeighborsMap().remove(countryToRemove);
}
/**
 * This method will add country in existing Map
 * @param country the country that you want to add
 * @param gameMap  current map details
 * @param neighbor List of neighborCountry
 */
 public void addCountry(Country country,GameMap gameMap,List<Country> neighbor){
	  if(country!=null && !neighbor.isEmpty()) {
		 if( gameMap.getCountryAndNeighborsMap().containsKey(country)) {
			 gameMap.getCountryAndNeighborsMap().put(country, neighbor); // country added to existing game details map
		 }
	  }
 }
 /**
  * This method will add neighbor to Country
  * @param countryName country name where neighbors to be added
  * @param gameMap current map details
  * @param neighborCountry neighbor country to be added.
  */
 public void addNeighbor(String countryName,GameMap gameMap,Country neighborCountry) {
	 if(RiskGameUtil.checkNullString(countryName)) {
		 Country country = new Country(countryName);
		 if(gameMap.getCountryAndNeighborsMap().containsKey(country)){
			 gameMap.getCountryAndNeighborsMap().get(country).add(neighborCountry);
		 }
	 }
 }
 
/**
 * This method will create .map file based on input provided from user
 * @param graphMap Details provided from the user 
 * @param filename Map file name that user wants to give
 */
 public void writeMap(GameMap graphMap, String filename) {
		String maps = "[Map]\n";
		for(Map.Entry<String, String> entry:graphMap.getMapDetail().entrySet()){
			maps = maps + entry.getKey() + "=" + entry.getValue() +"\n";
		}
		maps = maps + "\n";

		String continents = "[Continents]\n";
	    System.out.println("this is the size of continents:"+graphMap.getContinentList().size());
	    for (Continent continent : graphMap.getContinentList()) {
			continents = continents + continent.continentName + "=" + continent.numberOfTerritories + "\n";
		}
		continents = continents + "\n";
		
		String territories = "[Territories]\n";
		
		// loop of graphMap
		Iterator<Map.Entry<Country, List<Country>>> it = graphMap.getCountryAndNeighborsMap().entrySet().iterator();
		int startPixel=45;
		int endPixel=60;
		while (it.hasNext()) {
			Map.Entry<Country, List<Country>> pair = it.next();
			// get country object
			Country keyCountry = (Country) pair.getKey();
			// get list of the neighbors
			List<Country> neiCountryList = (List<Country>) pair.getValue();

			// index of the country from the all countries of all continents
			// list
			startPixel+=10;endPixel+=10;
			// get values of each country object
			territories = territories + keyCountry.countryName + "," + startPixel + ","
					+ endPixel + "," + keyCountry.getBelongsToContinent();

			// get the index value of the neighbor
			for (Country c : neiCountryList) {
				territories = territories + "," + c.countryName;
			}
			territories = territories + "\n";
		}

		String result = maps + continents + territories;

		String dotMapFile = filename + ".map";
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(dotMapFile)));
			out.print(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}
}
