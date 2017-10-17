package riskModels.gamedriver;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.player.Player;

import java.awt.Color;
import java.util.*;

public class StartupPhase {

    public static HashMap<Country, List<Country>> graphMap = new HashMap<>();
    public static List<Country> neighNodeList = new ArrayList<>();

    public static void main(String arg[]) {
        StartupPhase s = new StartupPhase();
        Country n1 = new Country("Pakistan", 13, 14, "Asia");
        neighNodeList.add(n1);
        Country n2 = new Country("China", 13, 14, "Asia");
        neighNodeList.add(n2);

        Country country1 = new Country("USA", 10, 11, "NorthAmerica");
        Country country2 = new Country("Canada", 10, 11, "NorthAmerica");
        Country country3 = new Country("Australia", 10, 11, "NorthAmerica");
        Country country4 = new Country("Britian", 10, 11, "NorthAmerica");
        Country country5 = new Country("Iceland", 10, 11, "NorthAmerica");
        Country country6 = new Country("Mexico", 10, 11, "NorthAmerica");
        Country country7 = new Country("Spain", 10, 11, "NorthAmerica");
        Country country8 = new Country("Rome", 10, 11, "NorthAmerica");

        graphMap.put(country1, neighNodeList);
        graphMap.put(country2, neighNodeList);
        graphMap.put(country3, neighNodeList);
        graphMap.put(country4, neighNodeList);
        graphMap.put(country5, neighNodeList);
        graphMap.put(country6, neighNodeList);
        graphMap.put(country7, neighNodeList);
        graphMap.put(country8, neighNodeList);

        Scanner scanner = new Scanner(System.in);
        int numberOfPlayers = scanner.nextInt();
        Color color[]= {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN};
        // players
        List<Player> playerList = new ArrayList<>();
        int i = 0;
        while (i < numberOfPlayers) {
            playerList.add(new Player("player" + i,color[i]));
            i++;
        }

        // assign countries to players
        int j = 0;
        for (Map.Entry<Country, List<Country>> e : graphMap.entrySet()) {
            playerList.get(j % numberOfPlayers).assignedCountries.add(e.getKey());
            j++;
        }

        //To assign initial number iof armies/infantries according to the players
        switch (numberOfPlayers) {
            case 2:
                for (Player p : playerList) {
                    p.setTotalArmies(40);
                }
                break;
            case 3:
                for (Player p : playerList) {
                    p.setTotalArmies(35);
                }
                break;
            case 4:
                for (Player p : playerList) {
                    p.setTotalArmies(30);
                }
                break;
            case 5:
                for (Player p : playerList) {
                    p.setTotalArmies(25);
                }
                break;

        }

        // print players assigned countries
        for (Player p : playerList) {
            StringBuilder assigned = new StringBuilder();
            for (Country c : p.assignedCountries) {
                c.setBelongsToPlayer(p);
                c.setCurrentArmiesDeployed(1);
                assigned.append(c.getCountryName()).append(", ");
            }

            System.out.println("Player " + p.getName() + " : [" + assigned + "]");
            System.out.println("Player " + p.getName() + " : [" + p.getTotalArmies() + "]");
        }
        
        //print Countries assigned players
        for (Map.Entry<Country, List<Country>> e : graphMap.entrySet()) {
            System.out.println(e.getKey().getCountryName() + "  ------ " + e.getKey().getBelongsToPlayer().getName() + "----------" + e.getKey().getCurrentArmiesDeployed());
        }

        //Allocating armies to countries for each player in RR fashion
        j = 0;
        for (Player p : playerList) {
            System.out.println("" + playerList.get(j % numberOfPlayers).getName() + " pls select from :");
            StringBuilder assigned = new StringBuilder();
            for (Country c : p.getAssignedCountries()) {
                assigned.append(c.getCountryName()).append(", ");
            }
            System.out.println(assigned);
            Scanner scanner1 = new Scanner(System.in);
            String countryNameToAssign = scanner1.next();
            Country c = (Country) graphMap.get(countryNameToAssign);
            System.out.println(c.toString());
            s.reinforcementPhaseAssign(c, p);
            j++;
        }

    }

    public void reinforcementPhaseAssign(Country countrySelected, Player player) {
        System.out.println(countrySelected.toString());
        int currentArmiesOnCountry = countrySelected.getCurrentArmiesDeployed();
        countrySelected.setCurrentArmiesDeployed(currentArmiesOnCountry + 1);
        player.setReinforcementArmies(player.getTotalArmies() - player.getAssignedCountries().size() - 1);

    }

	public void assignCountriesToPlayer(int numberOfPlayers, GameMap gameMap) {
		
		Color color[]= {Color.RED,Color.MAGENTA,Color.BLUE,Color.GREEN};
        // players
        List<Player> playerList = new ArrayList<>();
        int i = 0;
        while (i < numberOfPlayers) {
            playerList.add(new Player("player" + i,color[i]));
            i++;
        }
        // assign countries to players
        int j = 0;
        for (Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet()) {
            playerList.get(j % numberOfPlayers).assignedCountries.add(e.getKey());
            e.getKey().setBelongsToPlayer(playerList.get(j % numberOfPlayers));
            j++;
        }
        assignInitialArmiesToPlayers(numberOfPlayers,playerList);
        // print players assigned countries
        for (Player p : playerList) {
            StringBuilder assigned = new StringBuilder();
            for (Country c : p.assignedCountries) {
                c.setBelongsToPlayer(p);
                c.setCurrentArmiesDeployed(1);
                assigned.append(c.getCountryName()).append(", ");
            }

            System.out.println("Player " + p.getName() + " : [" + assigned + "]");
            System.out.println("Player " + p.getName() + " : [" + p.getTotalArmies() + "]");
        }
		
	}

	public void assignInitialArmiesToPlayers(int numberOfPlayers, List<Player> playerList) {
		switch (numberOfPlayers) {
        case 2:
            for (Player p : playerList) {
                p.setTotalArmies(40);
            }
            break;
        case 3:
            for (Player p : playerList) {
                p.setTotalArmies(35);
            }
            break;
        case 4:
            for (Player p : playerList) {
                p.setTotalArmies(30);
            }
            break;
        case 5:
            for (Player p : playerList) {
                p.setTotalArmies(25);
            }
            break;

    }
		
	}
	
	
}

