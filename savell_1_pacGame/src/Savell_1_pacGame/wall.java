/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import javafx.scene.Group;
import javafx.scene.shape.*;

/**
 *
 * @author ethan
 */
public class wall extends Rectangle{    
    int x;
    int y;
    int w;
    int h;
    public wall(int x, int y, int w, int h, Group root){
        this.setX(x);
        this.setY(y);
        this.setHeight(w);
        this.setWidth(h);
        root.getChildren().add(this);
    }
    
}
