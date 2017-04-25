/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import static Savell_1_pacGame.Savell_1_pacGame.ghostz;
import static Savell_1_pacGame.Savell_1_pacGame.randy;
import static Savell_1_pacGame.Savell_1_pacGame.root;
import static Savell_1_pacGame.Savell_1_pacGame.wallz;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ethan
 */
public class Ghost extends Rectangle {

    int color;

    boolean canMoveDown;
    boolean canMoveUp;
    boolean canMoveLeft;
    boolean canMoveRight;
    boolean left;
    boolean right;
    boolean up;
    boolean down;

    public Ghost(int C) {
        this.setHeight(30);
        this.setWidth(30);

        this.color = C;
        if (color == 1) {
            this.setFill(Color.RED);
            this.setX(505);
            this.setY(300);
        }
        if (color == 2) {
            this.setFill(Color.CYAN);
            this.setX(550);
            this.setY(300);
        }
        if (color == 3) {
            this.setFill(Color.MAGENTA);
            this.setX(505);
            this.setY(260);
        }
        if (color == 4) {
            this.setFill(Color.ORANGE);
            this.setX(550);
            this.setY(260);
        }
        root.getChildren().add(this);
        ghostz.add(this);

    }

    public void handleGhost() {
        if (checkWallGhost()) {
            System.out.println("GHost Is touching wall");
        }
        if (this.right) {
            this.setX(this.getX() + 3);
            if (checkWallGhost()) {
                this.right = false;
                this.setX(this.getX() - 5);
                int n = randy.nextInt(2)+1;
                if (n == 1) {
                    this.up = true;
                } else {
                    this.down = true;
                }

            }

        } else if (this.left) {
            this.setX(this.getX() - 3);
            if (checkWallGhost()) {
                this.left = false;
                this.setX(this.getX() + 5);
                int n = randy.nextInt(2)+1;
                if (n == 1) {
                    this.up = true;
                } else {
                    this.down = true;
                }

            }

        } else if (this.down) {
            this.setY(this.getY() + 3);
            if (checkWallGhost()) {
                this.down = false;
                this.setY(this.getY() - 5);
                int n = randy.nextInt(2)+1;
                if(n == 1){
                    this.right = true;
                }else {
                    this.left = true;
                }
            }

        } else if (this.up) {
            this.setY(this.getY() - 3);
            if (checkWallGhost()) {
                this.up = false;
                this.setY(this.getY() + 5);
                int n = randy.nextInt(2)+1;
                if(n == 1){
                    this.right = true;
                }else {
                    this.left = true;
                }
            }
        }
    }

    public boolean checkWallGhost() {
        for(Ghost g :ghostz){
            for(Junction j : junctionz){
                
            }
        }
        for (Ghost g : ghostz) {
            for (Wall w : wallz) {

                if (g.getBoundsInParent().intersects(w.getBoundsInParent())) {
                    return true;
                }
            }

        }
        return false;
        
    }
}
