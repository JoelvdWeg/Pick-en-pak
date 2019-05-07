/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author hylke
 */
public class Pick {
private int vakjex;
private int vakjey;

    public Pick(int vakjex, int vakjey) {
        this.vakjex = vakjex;
        this.vakjey = vakjey;
        vakjex = 0;
        vakjey = 0;
    }

public void vakjex() {
    if (vakjex > 5) {
        vakjex--;
    }
    if (vakjex < 1 )
        vakjex++;
        }
public void vakjey(){
    if (vakjey > 5) {
        vakjey--;
    }
    if (vakjey < 1 )
        vakjey++;
        }

    public int getVakjex() {
        return vakjex;
    }

    public int getVakjey() {
        return vakjey;
    }




}
