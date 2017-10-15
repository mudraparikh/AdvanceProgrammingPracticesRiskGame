package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;

public class CreateMap {


    public static void main(String args[]) {
        
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
        List<String> centers = new ArrayList<>();
        while (counter < countryNameList.size()) {
            String countryName = countryNameList.toArray(new String[countryNameList.size()])[counter];
            System.out.println(" Assign country-->" + countryName + "-->To Continent");
            Scanner sca = new Scanner(System.in);
            String continentName = sca.nextLine();
            System.out.println(" Assign Center Start-->" + countryName + "");
            int x = sca.nextInt();
            System.out.println(" Assign Center End-->" + countryName + "");
            int y = sca.nextInt();
        	String center = x+" "+y;
        	centers.add(center);
            if (continentNameList.contains(continentName)) {
                Country singleCountry = new Country(countryName, x, y, continentName);
                countryList.add(singleCountry);
                Continent continent = new Continent(continentName);
                //if continent is not present in continent list then only add it 
                if(!continentList.contains(continent)) {
                	continent.setNumberOfTerritories(1);
                	continentList.add(continent);
                }else { // if continent is already there , we need to increase the Number of territories of that continent
                	int numberOfTerritory=continentList.get(continentList.indexOf(continent)).getNumberOfTerritories();
                	continentList.get(continentList.indexOf(continent)).setNumberOfTerritories(numberOfTerritory+1);
                }
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
        while (counter < countryNameList.size()) {
            String countryName = countryNameList.toArray(new String[countryNameList.size()])[counter];
            String continentName = countryContinentMap.get(countryName);
            String center = centers.get(counter);
            int x = 0;
            int y = 0;
        	String[] center_split = center.split(" ");
        	x= Integer.parseInt(center_split[0]);           	
        	y= Integer.parseInt(center_split[1]); 
            Country country = new Country(countryName, x, y, continentName);
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
        System.out.println("Please Enter the Name of The Map that you want to create");
        Scanner scanMapFileName = new Scanner(System.in);
        String fileName= scanMapFileName.nextLine();
        GameMap gameMap = new GameMap();
        gameMap.setCountryAndNeighborsMap(countryNeibourMap);
        gameMap.setContinentList(continentList);
        MapModel mapModel = new MapModel();
        if(RiskGameUtil.checkNullString(fileName)) {
        mapModel.writeMap(gameMap,fileName);
        }
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
        
        JFrame f = new JFrame();
        f.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getX()+","+e.getY());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getX()+","+e.getY());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getX()+","+e.getY());
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getX()+","+e.getY());
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getX()+","+e.getY());
			}
		});
		int width = 0;
		int height = 0;
		//find size of jframe from the position of countries
		for(Country c:gameMap.getCountryAndNeighborsMap().keySet()){
			if(c.getStartPixel()>width){
				width = c.getStartPixel();
			}
			if(c.getEndPixel()>height){
				height = c.getEndPixel();
			}
		}
		
		JLabel jIcon = new JLabel(){
			@Override
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        HashMap<Country,List<Country>> map =  gameMap.getCountryAndNeighborsMap();
		        Set<Country> countryList = map.keySet();
		        for(Entry<Country, List<Country>> entry :map.entrySet()){
		        	for(Country c:entry.getValue()){
		        		for(Country t:countryList){
		        			if(c.getCountryName().equals(t.getCountryName())){
		        				g.setColor(Color.RED);
		        				g.drawLine(t.getStartPixel(), t.getEndPixel()+50, entry.getKey().getStartPixel(), entry.getKey().getEndPixel()+50);
		        			}
		        		}
		        	}
		        }
		    }
		};
		jIcon.setSize(width, height);

		f.add(jIcon);
		f.setSize(width+100, height+100);
		jIcon.setVisible(true);
		f.setVisible(true);
		JLabel[] l = new JLabel[gameMap.getCountryAndNeighborsMap().keySet().size()];
		int i = 0;
		for(Country c:gameMap.getCountryAndNeighborsMap().keySet()){
			l[i] = new JLabel(c.getCountryName());
			l[i].setBounds(c.getStartPixel(), c.getEndPixel(), 100, 100);
			l[i].setVisible(true);
			jIcon.add(l[i]);
			//l[i].setToolTipText("Solider:");
			i++;
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