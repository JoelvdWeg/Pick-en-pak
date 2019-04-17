package tsp;

import java.util.ArrayList;

public class NearestNeighbourTSP {
    private final int AANTAL_VAKKEN = 30;
    private ArrayList<Integer> bestRoute;
    private double bestRouteDist;
    
    private Product[] products = new Product[AANTAL_VAKKEN+1];
    
    public NearestNeighbourTSP(){
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

    public ArrayList<Integer> getBestRoute() {
        return bestRoute;
    }

    public double getBestRouteDist() {
        return bestRouteDist;
    }

    private double distance(ArrayList<Integer> r) {
        double dist = 0;

        for (int i = 0; i < r.size() - 1; i++) {
            dist += products[r.get(i)].getCoord().dist(products[r.get(i + 1)].getCoord());
        }
        return dist;
    } 

    public void findBestRoute(ArrayList<Integer> r, ArrayList<Integer> citiesNotInRoute) {
        if (r.isEmpty()) {
            r.add(AANTAL_VAKKEN);
        }
        if (citiesNotInRoute.size() != 1) {

            double closestDist = products[citiesNotInRoute.get(0)].getCoord().dist(products[r.get(r.size() - 1)].getCoord());
            int closest = 0;

            for (int i = 1; i < citiesNotInRoute.size(); i++) {
                if (products[citiesNotInRoute.get(i)].getCoord().dist(products[r.get(r.size() - 1)].getCoord()) < closestDist) {
                    closestDist = products[citiesNotInRoute.get(i)].getCoord().dist(products[r.get(r.size() - 1)].getCoord());
                    closest = i;
                }
            }
            //System.out.println("Dichtsbijzijnde punt: "+ citiesNotInRoute.get(closest) + "   Afstand: " + closestDist);
            

            r.add(citiesNotInRoute.get(closest));
            citiesNotInRoute.remove(closest);
            findBestRoute(r, citiesNotInRoute);

        } else {
            r.add(citiesNotInRoute.get(0));
            r.add(AANTAL_VAKKEN);
            bestRoute = r;
            bestRouteDist = distance(r);
        }
    }
}
