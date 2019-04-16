package tsp;

import java.util.ArrayList;
import java.util.Random;

public class TSP {
    public static final int AANTAL_VAKKEN = 30;
    public static final int AANTAL_LOCATIES = 5;

    public static ArrayList<Integer> generateLocations(int n) {
        ArrayList<Integer> locations = new ArrayList<>();  //generate random locations to travel to
        Random random = new Random();
        int r;
       // System.out.println("Locaties te bezoeken: ");
        for (int i = 0; i < n; i++) {
            do {
                r = random.nextInt(AANTAL_VAKKEN);
            } while (locations.contains(r));
            locations.add(r);
           // System.out.print(r + " ");
        }
       // System.out.println("");
        return locations;
    }
    
    public static double runTest(int iterations){
        double sum = 0;
        
        for(int i = 0; i < iterations; i++){
            ArrayList<Integer> randomLocations = generateLocations(AANTAL_LOCATIES);
            
            BruteForceTSP bf = new BruteForceTSP();
            bf.findBestRoute(new ArrayList<Integer>(), randomLocations);
            
            NearestNeighbourTSP nn = new NearestNeighbourTSP();
            nn.findBestRoute(new ArrayList<Integer>(), randomLocations);
            
            TwoOptTSP twoOpt = new TwoOptTSP(nn.getBestRoute());
            twoOpt.findBestRoute(nn.getBestRoute());
            
            double improvement = (twoOpt.getBestRouteDist()-bf.getBestRouteDist())/bf.getBestRouteDist();
            //System.out.println("BF: "+bf.getBestRouteDist());
            //System.out.println("2-OPT: "+twoOpt.getBestRouteDist());
            //System.out.println("verbetering: "+improvement);
            sum += improvement;
            //System.out.println("__________________");
        }
        
        return sum/iterations;
    }

    public static void main(String[] args) {
        int n = 1000000;
        for(int i = 2; i < 11; i++){
            System.out.println("Gemiddelde verbetering van brute force t.o.v. 2-opt ("+n+" iteraties, "+i+" locaties): " + runTest(n));
        }
        
        
        
        
        
        
        
//        System.out.println("______________________________________");
//        System.out.println("BRUTE FORCE ALGORITME");
//        
//        BruteForceTSP bf = new BruteForceTSP();
//        bf.findBestRoute(new ArrayList<Integer>(), randomLocations);
//        
//        System.out.println("______________________________________");
//        System.out.println("NEAREST NEIGHBOUR ALGORITME");
//        
//        NearestNeighbourTSP nn = new NearestNeighbourTSP();
//        nn.findBestRoute(new ArrayList<Integer>(), randomLocations);
//        
//        ArrayList<Integer> nnBest = nn.getBestRoute();
//        
//        System.out.println("______________________________________");
//        System.out.println("2-OPT ALGORITME");
//        
//        TwoOptTSP twoOpt = new TwoOptTSP(nnBest);
//        twoOpt.findBestRoute(nnBest);
//        
//        System.out.println("______________________________________");
//        System.out.println("BRUTE FORCE RESULTAAT");
//        
//        System.out.println("Beste route (BF): ");
//        System.out.println(bf.getBestRoute());
//        System.out.println("Afstand: " + bf.getBestRouteDist());
//        
//        System.out.println("______________________________________");
//        System.out.println("NEAREST NEIGHBOUR RESULTAAT");
//        
//        
//    
//        System.out.println("Beste route (NN): ");
//        System.out.println(nnBest);
//        System.out.println("Afstand: " + nn.getBestRouteDist());
//        
//        
//        
//        System.out.println("______________________________________");
//        System.out.println("2-OPT RESULTAAT");
//        System.out.println("Verbeterde route: ");
//        System.out.println(twoOpt.getBestRoute());
//        System.out.println("Afstand: "+ twoOpt.getBestRouteDist());
//        

          
    }
}
