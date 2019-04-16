package tsp;

import java.util.ArrayList;
import java.util.Collections;

public class TwoOptTSP {
    public static final int AANTAL_VAKKEN = 30;
    private static ArrayList<Integer> bestRoute;
    private static double bestRouteDist;

    private static Product[] products = new Product[AANTAL_VAKKEN+1];

    public TwoOptTSP(ArrayList<Integer> r) {
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                products[k] = new Product(new Coordinate(i, j));
                k++;
            }
        }
        products[AANTAL_VAKKEN] = new Product(new Coordinate(8, 5), "lospunt");
        
        bestRoute = r;
        bestRouteDist = distance(bestRoute);
    }

    public static ArrayList<Integer> getBestRoute() {
        return bestRoute;
    }

    public static double getBestRouteDist() {
        return bestRouteDist;
    }

    private static boolean isBestRoute(ArrayList<Integer> r) {

        return distance(r) < bestRouteDist || bestRouteDist == -1;

    }

    private static double distance(ArrayList<Integer> r) {
        double dist = 0;

        for (int i = 0; i < r.size() - 1; i++) {
            dist += products[r.get(i)].getCoord().dist(products[r.get(i + 1)].getCoord());
        }
        return dist;
    }

    public static void findBestRoute(ArrayList<Integer> r) {
        ArrayList<Integer> swappedRoute;
        for (int i = 1; i < r.size() - 2; i++) {
            for (int k = i + 1; k < r.size() - 1; k++) {
                ArrayList<Integer> clone = (ArrayList<Integer>) r.clone();
                swappedRoute = swap(clone,i,k);
                if(isBestRoute(swappedRoute)){
                    bestRoute = swappedRoute;
                    bestRouteDist = distance(swappedRoute);
                    //System.out.println("Nieuwe 2-opt verbetering: "+bestRoute);
                    //System.out.println("Afstand: "+bestRouteDist);
                }
            }
        }
    }
    
    public static ArrayList<Integer> swap(ArrayList<Integer> route, int i, int k){
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

}


