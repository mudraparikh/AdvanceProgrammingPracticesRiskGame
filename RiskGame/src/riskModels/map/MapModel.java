package riskModels.map;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.country.CountryConstants;
import util.RiskGameUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
            while ((Territories = bufferReaderForFile.readLine()) != null && !Territories.startsWith(CountryConstants.bracket)) {
                if (RiskGameUtil.checkNullString(Territories)) {
                    String countryName, continentName = null;
                    int startPixel, endPixel = 0;
                    List<Country> neighbourNodes = new ArrayList<Country>();
                    String[] terrProperties = Territories.split(CountryConstants.comma);
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
     * This mehod will read the continet part of map file
     *
     * @param bufferReaderForFile
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
                    continents.setContinentName(ConProperties[0]);
                    continents.setNumberOfTerritories(Integer.parseInt(ConProperties[1]));
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
     * @param filePath
     * @return Function will return the map details obj
     */
    public GameMap readMapFile(String filePath) {
        BufferedReader bufferReaderForFile = null;
        GameMap mapDetails = new GameMap();
        try {
            File file = new File(filePath);
            bufferReaderForFile = new BufferedReader(new FileReader(file));
            String st, maps, Continents, Territories;
            while ((st = bufferReaderForFile.readLine()) != null) {
                if (st.startsWith("[")) {

                    String id = st.substring(st.indexOf("[") + 1, st.indexOf("]"));
                    if (id.equalsIgnoreCase("Map")) {
                        while ((maps = bufferReaderForFile.readLine()) != null && !maps.startsWith("[")) {
                            if (RiskGameUtil.checkNullString(maps)) {
                                System.out.println(maps);
                                bufferReaderForFile.mark(0);
                            }
                        }
                        bufferReaderForFile.reset();

                    }
                    if (id.equalsIgnoreCase("Continents")) {
                        List<Continent> listOfContinents = MapModel.readContinents(bufferReaderForFile);
                        mapDetails.setContinentList(listOfContinents);
                        System.out.println("Reading of Continents Completed");
                    }

                    if (id.equalsIgnoreCase("Territories")) {
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


        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return mapDetails;

    }
}
