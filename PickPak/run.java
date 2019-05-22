package PickPak;

import java.awt.EventQueue;
import java.awt.ScrollPane;
import javax.swing.JFrame;

public class run {

    public static void main(String[] args) {
        PickPak pp = new PickPak();
        

       PickFrame pf = new PickFrame(pp);

        
        
//        try {
//            FileReader file = new FileReader("fruit.txt");
//            Scanner parser = new Scanner(file);
//            int k = 0;
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 5; j++) {
//                    String nextLine = parser.nextLine();
//                    items.add(new Item(new Locatie(k, new Coordinate(i, j)), Integer.parseInt(nextLine.split(" ")[2]), Integer.parseInt(nextLine.split(" ")[0]), nextLine.split(" ")[1]));
//                    k++;
//                }
//            }
//            items.add(new Item(new Locatie(k, new Coordinate(4, 0)), 0, 0, "LOSPUNT"));
//            System.out.println("Items opgehaald\n...");
//        } catch (FileNotFoundException fe) {
//            System.out.println("Kon items niet ophalen\n...");
//        }


    }
}
