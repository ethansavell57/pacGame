/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import static Savell_1_pacGame.Savell_1_pacGame.root;
import static Savell_1_pacGame.Savell_1_pacGame.dotz;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author ethan
 */
public class Dot extends Circle {
    public Dot(int x, int y){
        this.setRadius(10);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setFill(Color.INDIANRED);
        dotz.add(this);
        root.getChildren().add(this);
    }
}
