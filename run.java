import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class run {    
    private static ArrayList<Item> pakbon;
    
    public static void main(String[] args) {
        maakItems();
        
        for(Item item: PickPak.items){
            System.out.println(item);
        }
        
        pakbon = new ArrayList<>();
        
        try{
            FileReader file = new FileReader("newfile.txt");
            Scanner parser = new Scanner(file);
            while(parser.hasNextLine()){
                voegToeAanPakbon(parser.nextLine());
            }
        } catch(FileNotFoundException fe){
            System.out.println(fe);
        }
        
        PickPak pp = new PickPak(pakbon);
        
        
    }
    
    private static void voegToeAanPakbon(String line){
        int nextItemID = Integer.parseInt(line);
       
        for(Item item: PickPak.items){
            if(item.getLocatie().getID() == nextItemID){
                pakbon.add(item);
                System.out.println("Item met locatie-id" + item.getLocatie().getID() + " toegevoegd aan pakbon");
                break;
            }
        }
    }
    
    private static void maakItems(){
        PickPak.items = new ArrayList<>();
        Random r = new Random();
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                PickPak.items.add(new Item(new Locatie(k, new Coordinate(i, j)),r.nextInt(12)+1));
                k++;
            }
        }
        PickPak.items.add(new Item(new Locatie(k, new Coordinate(4, 0))));
    }
}
