/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import static Savell_1_pacGame.Savell_1_pacGame.root;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ethan
 */
public class Mark extends Rectangle {
    public Mark(int x, int y){
        this.setX(x);
        this.setY(y);
        root.getChildren().add(this);
    }
}
