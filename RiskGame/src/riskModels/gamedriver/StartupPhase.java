package riskModels.gamedriver;

import riskModels.country.Country;
import riskModels.player.Player;

import java.util.*;

public class StartupPhase {

    public void assignCountriesToPlayers() {
        HashMap<Country, List<Country>> graphMap = new HashMap<>();
        List<Country> neighNodeList = new ArrayList<>();
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
        int totalPlayers = scanner.nextInt();

        // players
        List<Player> playerList = new ArrayList<Player>();
        int i = 0;
        while (i < totalPlayers) {
            playerList.add(new Player("player" + i));
            i++;
        }

        // assign countries to players
        int j = 0;
        for (Map.Entry<Country, List<Country>> e : graphMap.entrySet()) {
            playerList.get(j % totalPlayers).assignedCountries.add(e.getKey());
            j++;
        }

        // print players assigned countries
        for (Player p : playerList) {
            StringBuilder assigned = new StringBuilder();
            for (Country c : p.assignedCountries) {
                assigned.append(c.getCountryName()).append(", ");
            }
            System.out.println("Player " + p.getName() + " : [" + assigned + "]");
        }
    }
}

