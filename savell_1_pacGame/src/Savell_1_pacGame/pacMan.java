/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ethan
 */
public class pacMan extends Arc {
    int lives = 3;
    boolean canMoveDown;
    boolean canMoveUp;
    boolean canMoveLeft;
    boolean canMoveRight;
    boolean left;
    boolean right;
    boolean up;
    boolean down;
        public pacMan(int x, int y){
        this.setFill(Color.PLUM);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadiusX(250);
        this.setRadiusY(250);
        this.setStartAngle(45);
        this.setLength(270);
        this.setType(ArcType.ROUND);
        
        this.setFill(Color.PLUM);
        }
}
