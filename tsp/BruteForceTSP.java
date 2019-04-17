package tsp;

import java.util.*;

class BruteForceTSP{
    private static final int AANTAL_VAKKEN = 30;
    private static ArrayList<Integer> bestRoute;
    private static double bestRouteDist;
    
    private Product[] products = new Product[AANTAL_VAKKEN+1];
    
    public BruteForceTSP(){
        bestRouteDist = -1;
        
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                products[k] = new Product(new Coordinate(i, j));
                k++;
            }
        }
        products[AANTAL_VAKKEN] = new Product(new Coordinate(8, 5), "lospunt");
    }

    public static ArrayList<Integer> getBestRoute() {
        return bestRoute;
    }

    public static double getBestRouteDist() {
        return bestRouteDist;
    }
    
    
    
    private boolean isBestRoute(ArrayList<Integer> r) {

        return distance(r) < bestRouteDist || bestRouteDist == -1;

    }

    private double distance(ArrayList<Integer> r) {
        double dist = 0;

        for (int i = 0; i < r.size() - 1; i++) {
            dist += products[r.get(i)].getCoord().dist(products[r.get(i + 1)].getCoord());
        }
        return dist;
    } 

    public void findBestRoute(ArrayList<Integer> r, ArrayList<Integer> citiesNotInRoute) {
        if(citiesNotInRoute.size() <= 15){
        if (!citiesNotInRoute.isEmpty()) { //wanneer er nog steden niet bezocht zijn
            for (int i = 0; i < citiesNotInRoute.size(); i++) { //voor elke nog niet bezochte stad
                int removed = (int) citiesNotInRoute.remove(0); //haal een stad weg uit de lijst met niet bezochte steden

                ArrayList<Integer> newRoute = (ArrayList<Integer>) r.clone(); // maak een nieuwe route van de huidige route plus de nieuwe stad
                newRoute.add(removed);

                findBestRoute(newRoute, citiesNotInRoute); //ga recursief verder met zoeken naar routes

                citiesNotInRoute.add(removed); //voeg de weggehaalde stad weer toe aan de lijst niet bezochte steden wanneer alle routes na deze stad zijn gecontroleerd
            }
        } else { //alle steden zijn bezocht
            ArrayList<Integer> temp = (ArrayList<Integer>) r.clone();
            temp.add(0, AANTAL_VAKKEN);
            temp.add(AANTAL_VAKKEN);
            //route begint en eindigt bij het lospunt

            if (isBestRoute(temp)) { //check of deze route de snelste is
                bestRoute = temp;
                bestRouteDist = distance(temp);
                //System.out.println("Nieuwe beste route: " + temp.toString());
                //System.out.println("Nieuwe beste afstand: " + bestRouteDist);
            }
        }
        } else {
            System.out.println("Te veel locaties (>10) voor Brute Force!");
        }
    }
}
