package util;

import riskModels.continent.Continent;
import riskModels.country.Country;

import java.util.*;

public class CreateMap {


    public static void main(String args[]) {
        //AssingPlayerAndCountry();
        System.out.println("How many continent do you want?");
        Scanner sc = new Scanner(System.in);
        int numberOfContinents = sc.nextInt();
        System.out.println("How many Countries do you want ?");
        int numberOfCountries = sc.nextInt();
        List<String> continentNameList = createCountinentList(numberOfContinents);
        List<String> toBeRemoved = new ArrayList<>();
        Collection<String> countryNameList = createCountryList(numberOfCountries);
        List<Country> countryList = new ArrayList<Country>();
        List<Continent> continentList = new ArrayList<Continent>();
        HashMap<String, String> countryContinentMap = new HashMap<>();
        HashMap<Country, List<Country>> countryNeibourMap = new HashMap<>();
        System.out.println("Continent names are");
        for (String continent : continentNameList) {
            System.out.print(continent + " ");
        }
        System.out.println("");
        System.out.println("Country names are ");
        for (String country : countryNameList) {
            System.out.print(country + " ");
        }
        System.out.println("");
        int counter = 0;
        while (counter < countryNameList.size()) {
            String countryName = countryNameList.toArray(new String[countryNameList.size()])[counter];
            System.out.println(" Assign country-->" + countryName + "-->To Continent");
            Scanner sca = new Scanner(System.in);
            String continentName = sca.nextLine();
            if (continentNameList.contains(continentName)) {
                Country singleCountry = new Country(countryName, 0, 0, continentName);
                countryList.add(singleCountry);
                continentList.add(new Continent(continentName));
                countryContinentMap.put(singleCountry.getCountryName(), singleCountry.getBelongsToContinent());
            } else {
                System.out.println("Please enter proper continent name");
                countryNameList.add(countryName);
                toBeRemoved.add(countryName);
            }
            counter++;
        }
        //countryNameList.removeAll(toBeRemoved);
        for (String country : toBeRemoved) {
            countryNameList.remove(country);
        }
        toBeRemoved = new ArrayList<String>();
        counter = 0;
        System.out.println("Country and Continent is -->");
        for (Country country : countryList) {
            System.out.println(country.getCountryName() + "   " + country.getBelongsToContinent());
        }
        System.out.println("Assign neighbors to Country using comma seprated for example c2,c4,c5");
        while (counter < countryNameList.size()) {
            String countryName = countryNameList.toArray(new String[countryNameList.size()])[counter];
            String continentName = countryContinentMap.get(countryName);
            Country country = new Country(countryName, 0, 0, continentName);
            System.out.println(" Assign neighbor for Country-->" + countryName);
            Scanner readNeigbors = new Scanner(System.in);
            String neighborCountries[] = readNeigbors.nextLine().split(",");
            List<Country> neiborNodeList = new ArrayList<Country>();
            for (String neighbor : neighborCountries) {
                if (neighbor.equals(countryName)) {
                    System.out.println("Country Can not be neighbor to it self Please enter correct value");
                    countryNameList.add(countryName);
                    if (!toBeRemoved.contains(countryName)) {
                        toBeRemoved.add(countryName);
                    }
                } else if (!countryNameList.contains(neighbor)) {
                    System.out.println("One of the country you have entered is not part of CountryList Please enter correct value");
                    countryNameList.add(countryName);
                    if (!toBeRemoved.contains(countryName)) {
                        toBeRemoved.add(countryName);
                    }
                } else {
                    Country neighborCountry = new Country(neighbor);
                    neighborCountry.setBelongsToContinent(countryContinentMap.get(neighbor));
                    neiborNodeList.add(neighborCountry);
                    country.setNeighborNodes(neiborNodeList);
                    countryNeibourMap.put(country, neiborNodeList);
                }
            }
            counter++;
        }

        for (String countryToBeRemoved : toBeRemoved) {
            countryNameList.remove(countryToBeRemoved);
        }
        countryNeibourMap = correctNebourNodes(countryNeibourMap);
        Iterator it = countryNeibourMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Country country = (Country) pair.getKey();
            List<Country> neighbours = (List<Country>) pair.getValue();
            System.out.println("------" + country.getCountryName() + "-----" + country.getBelongsToContinent());
            for (Country neighbour : neighbours) {
                System.out.println(neighbour.getCountryName() + " " + neighbour.getBelongsToContinent());

            }

        }
    }


    private static HashMap<Country, List<Country>> correctNebourNodes(HashMap<Country, List<Country>> countryNeibourMap) {

        Iterator it = countryNeibourMap.entrySet().iterator();
        HashMap<Country, List<Country>> updatedCountryNeiborMap = new HashMap<>();
        //updatedCountryNeiborMap.putAll(countryNeibourMap);
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Country country = (Country) pair.getKey();
            List<Country> neighbours = (List<Country>) pair.getValue();
            for (Country neighbor : neighbours) {
                if (countryNeibourMap.containsKey(neighbor)) {
                    List<Country> neiborNodes = countryNeibourMap.get(neighbor);
                    List<Country> countryToBeAdded = new ArrayList<>();
                    countryToBeAdded.addAll(neiborNodes);
                    for (Country neibor : neiborNodes) {
                        if (!neibor.getCountryName().equals(country.getCountryName())) {
                            if (!countryToBeAdded.contains(country)) {
                                countryToBeAdded.add(country);
                                updatedCountryNeiborMap.put(neighbor, countryToBeAdded);
                            }

                        }
                    }


                }
            }
        }
        if (!updatedCountryNeiborMap.isEmpty()) {
            return updatedCountryNeiborMap;
        }
        return countryNeibourMap;
    }

    ;


    private static List<String> createCountryList(int numberOfCountries) {
        // TODO Auto-generated method stub
        List<String> returnCountryList = new ArrayList<String>();
        for (int i = 0; i < numberOfCountries; i++) {
            returnCountryList.add(String.valueOf(i));
        }
        return returnCountryList;

    }

    private static List<String> createCountinentList(int numberOfContinents) {
        // TODO Auto-generated method stub
        List<String> returnListofContinent = new ArrayList<String>();
        for (int i = 0; i < numberOfContinents; i++) {
            returnListofContinent.add("c" + i);
        }
        return returnListofContinent;
    }


}
