/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ethan
 */
public class pacMan extends Rectangle {
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
        this.setX(x);
        this.setY(y);
        this.setHeight(23);
        this.setWidth(23);
        
        this.setFill(Color.PLUM);
        }
}
