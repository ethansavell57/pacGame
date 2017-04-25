/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import static Savell_1_pacGame.Savell_1_pacGame.junctionz;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ethan
 */
class Junction extends Rectangle{
    public Junction(int x, int y){
        this.setX(x);
        this.setY(y);
        this.setHeight(40);
        this.setWidth(100);
        junctionz.add(this);
    }
}
