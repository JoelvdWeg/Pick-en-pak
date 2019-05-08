/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArduinoTest;

/**
 *
 * @author nickv
 */
public class ProgressBar extends GUI implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread started");
        while (true) {
            String value = "";
            try {
                value = arduino1.serialRead(1);
            } catch (NullPointerException e) {
                System.out.println("No data. Thread closing");
                Thread.currentThread().interrupt();
                return;
            }
            
            String[] result = value.split("\n", 2);
            System.out.println(result[0].trim());
            updateText(result[0].trim());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("FOUT!");
            }
        }
    }

}
