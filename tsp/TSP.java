package tsp;

import java.util.ArrayList;
import java.util.Collections;

public class TSP {
    static final int AANTAL_VAKKEN = 25;
    private final Product[] products = new Product[AANTAL_VAKKEN+1];
    
    private ArrayList<Integer> bestRoute;
    private double bestRouteDist;
    
    public TSP(){
        bestRouteDist = -1;
        
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                products[k] = new Product(new Coordinate(i, j));
                k++;
            }
        }
        products[AANTAL_VAKKEN] = new Product(new Coordinate(8, 4), "lospunt");
    }

    private void nearestNeighbour(ArrayList<Integer> r, ArrayList<Integer> citiesNotInRoute) {
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
           
            r.add(citiesNotInRoute.get(closest));
            citiesNotInRoute.remove(closest);
            nearestNeighbour(r, citiesNotInRoute);

        } else {
            r.add(citiesNotInRoute.get(0));
            r.add(AANTAL_VAKKEN);
            bestRoute = r;
            bestRouteDist = distance(r);
        }
    }
    
    private void optimize(ArrayList<Integer> r) {
        ArrayList<Integer> swappedRoute;
        for (int i = 1; i < r.size() - 2; i++) {
            for (int k = i + 1; k < r.size() - 1; k++) {
                ArrayList<Integer> clone = (ArrayList<Integer>) r.clone();
                swappedRoute = swap(clone,i,k);
                if(isBestRoute(swappedRoute)){
                    bestRoute = swappedRoute;
                    bestRouteDist = distance(swappedRoute);
                }
            }
        }
    }
    
    private ArrayList<Integer> swap(ArrayList<Integer> route, int i, int k){
        ArrayList<Integer> subList = new ArrayList<Integer>();
        for(int j = i; j <= k; j++){
            subList.add(route.get(j));
        }
        
        for(int j = 0; j < subList.size()/2; j++){
            Collections.swap(subList,j,subList.size()-j-1);
        }
        
        route.subList(i,k+1).clear();
        
        for(int j = 0; j < subList.size(); j++){
            route.add(i+j,subList.get(j));
        }
        return route;
    }
    
    private double distance(ArrayList<Integer> r) {
        double dist = 0;

        for (int i = 0; i < r.size() - 1; i++) {
            dist += products[r.get(i)].getCoord().dist(products[r.get(i + 1)].getCoord());
        }
        return dist;
    } 
    
    private boolean isBestRoute(ArrayList<Integer> r) {
        return distance(r) < bestRouteDist || bestRouteDist == -1;
    }
    
    public void findRoute(ArrayList<Integer> r) {
        nearestNeighbour(new ArrayList<Integer>(), r);
        optimize(bestRoute);
    }
    public ArrayList<Integer> getBestRoute(){
        return bestRoute;
    }

    public double getBestRouteDist() {
        return bestRouteDist;
    }
}
