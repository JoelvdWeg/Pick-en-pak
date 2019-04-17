package tsp;

import java.util.ArrayList;
import java.util.Random;

public class TSP {
    private static final int AANTAL_VAKKEN = 30;
    private static final int AANTAL_LOCATIES = 5;

    private static ArrayList<Integer> generateLocations(int n) {
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
    
    private static double[] runTest(int iterations){
        double[] sum = new double[3];
        
        for(int i = 0; i < iterations; i++){
            ArrayList<Integer> randomLocations = generateLocations(AANTAL_LOCATIES);
            
            BruteForceTSP bf = new BruteForceTSP();
            bf.findBestRoute(new ArrayList<>(), randomLocations);
                       
            NearestNeighbourTSP nn = new NearestNeighbourTSP();
            nn.findBestRoute(new ArrayList<>(), randomLocations);       
                      
            TwoOptTSP twoOpt = new TwoOptTSP(nn.getBestRoute());
            twoOpt.findBestRoute(nn.getBestRoute());
            
            double improvementBFtoNN = (nn.getBestRouteDist()-bf.getBestRouteDist())/nn.getBestRouteDist();
            double improvementBFto2opt = (twoOpt.getBestRouteDist()-bf.getBestRouteDist())/twoOpt.getBestRouteDist();
            double improvement2opttoNN = (nn.getBestRouteDist()-twoOpt.getBestRouteDist())/nn.getBestRouteDist();
            
            sum[0] += improvementBFtoNN;
            sum[1] += improvementBFto2opt;
            sum[2] += improvement2opttoNN;
            
            //System.out.println("__________________");
        }
        
        sum[0] /= iterations;
        sum[1] /= iterations;
        sum[2] /= iterations;
        return sum;
    }

    public static void main(String[] args) {
        int n = 10000;
        for(int i = 2; i < 11; i++){
            System.out.println("Algoritmes uitvoeren...");
            double[] result = runTest(n);
            
            System.out.println("Gemiddelde verbetering van brute force t.o.v. nn ("+n+" iteraties, "+i+" locaties): " + result[0]);
            System.out.println("Gemiddelde verbetering van brute force t.o.v. 2-opt ("+n+" iteraties, "+i+" locaties): " + result[1]);
            System.out.println("Gemiddelde verbetering van 2-opt t.o.v. nn ("+n+" iteraties, "+i+" locaties): " + result[2]);
            System.out.println("___________________________________________________________________________________________________________");
        }
  
    }
}
