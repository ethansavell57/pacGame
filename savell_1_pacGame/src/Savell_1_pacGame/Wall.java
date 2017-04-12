/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import static Savell_1_pacGame.Savell_1_pacGame.root;
import static Savell_1_pacGame.Savell_1_pacGame.wallz;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author ethan
 */
public class Wall extends Rectangle{    
    int x;
    int y;
    int w;
    int h;
    public Wall(int x, int y, int w, int h){
        this.setX(x);
        this.setY(y);
        this.setHeight(w);
        this.setWidth(h);
        this.setFill(Color.BLACK);
        this.setStroke(Color.BLUE);
        this.setStrokeWidth(5);
        wallz.add(this);
        root.getChildren().add(this);
    }
    
}
