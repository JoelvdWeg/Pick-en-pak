import java.util.ArrayList;
import java.util.Collections;

public class TSP {
    private static final int AANTAL_VAKKEN = 25;
    private static ArrayList<Item> items;
    private ArrayList<Integer> bestRoute;
    private double bestRouteDist;
    private ArrayList<Integer> pickItems;
    
    public TSP(ArrayList<Item> bestelling){
        items = run.items;
        pickItems = new ArrayList<>();
        
        for(Item item: bestelling){
            pickItems.add(item.getLocatie().getID());
        }
   
        nearestNeighbour(new ArrayList<>(), pickItems);   
        optimize(bestRoute);
    }

    
    
    private double distance(ArrayList<Integer> r) {
        double dist = 0;

        for (int i = 0; i < r.size() - 1; i++) {
            dist += items.get(r.get(i)).getCoord().dist(items.get(r.get(i + 1)).getCoord());
        }
        return dist;
    } 
    
   
    public void nearestNeighbour(ArrayList<Integer> r, ArrayList<Integer> citiesNotInRoute) {
        //System.out.println(citiesNotInRoute);
        if (r.isEmpty()) {
            r.add(AANTAL_VAKKEN);
        }
        if (citiesNotInRoute.size() != 1) {
            double closestDist = items.get(citiesNotInRoute.get(0)).getCoord().dist(items.get(r.get(r.size() - 1)).getCoord());
            int closest = 0;

            for (int i = 1; i < citiesNotInRoute.size(); i++) {
                if (items.get(citiesNotInRoute.get(i)).getCoord().dist(items.get(r.get(r.size() - 1)).getCoord()) < closestDist) {
                    closestDist = items.get(citiesNotInRoute.get(i)).getCoord().dist(items.get(r.get(r.size() - 1)).getCoord());
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
    
    private boolean isBestRoute(ArrayList<Integer> r) {
        return distance(r) < bestRouteDist || bestRouteDist == -1;
    }
    
     public void optimize(ArrayList<Integer> r) {
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
    
    public ArrayList<Integer> getBestRoute(){
        return bestRoute;
    }
    
    public double getBestRouteDist(){
        return bestRouteDist;
    }  
}
