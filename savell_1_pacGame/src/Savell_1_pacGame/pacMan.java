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
    int score;
    boolean canMoveDown;
    boolean canMoveUp;
    boolean canMoveLeft;
    boolean canMoveRight;
    boolean left;
    boolean right;
    boolean up;
    boolean down;
    boolean open = true;
    boolean closed;

    public pacMan(int x, int y) {
        this.setFill(Color.PLUM);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadiusX(22);
        this.setRadiusY(22);
        this.setStartAngle(45);

        this.setLength(270);
        this.setType(ArcType.ROUND);
        this.setFill(Color.PLUM);
//        if (down) {
//            this.setRotate(90);
//        }
//        if (left) {
//            this.setRotate(180);
//        }
//        if (up) {
//            this.setRotate(270);
//        }
//        if (right) {
//            this.setRotate(0);
//        }
    }
}
