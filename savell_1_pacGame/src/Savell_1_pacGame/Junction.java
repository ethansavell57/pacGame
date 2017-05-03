/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Savell_1_pacGame;

import static Savell_1_pacGame.Savell_1_pacGame.ghostz;
import static Savell_1_pacGame.Savell_1_pacGame.junctCounter;
import static Savell_1_pacGame.Savell_1_pacGame.junctionz;
import static Savell_1_pacGame.Savell_1_pacGame.root;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ethan
 */
class Junction extends Rectangle {

    boolean active;
    int ox;
    int oy;

    public Junction(int x, int y, boolean active) {
        this.setOx(x);
        this.setOy(y);
        this.setX(x);
        this.setY(y);
        this.setHeight(40);
        this.setWidth(100);
        junctionz.add(this);
//        root.getChildren().add(this);
    }

    public boolean isIsActive() {
        return active;
    }

    public void setIsActive(boolean isActive) {
        this.active = isActive;
    }

    public int getOx() {
        return ox;
    }

    public void setOx(int ox) {
        this.ox = ox;
    }

    public int getOy() {
        return oy;
    }

    public void setOy(int oy) {
        this.oy = oy;
    }

    private void checkJunctionActive() {
        if (this.isIsActive() == false) {
            this.setX(10000);
            this.setY(100000);
            junctCounter += 1;
            if (junctCounter % 200 == 0) {
                this.setIsActive(true);
                this.setX(this.getOx());
                this.setY(this.getOy());
            }
        }
    }
    public void handleJunctions(){
        checkJunctionActive();
        
        for (Ghost g : ghostz){
            
            if(g.getBoundsInParent().intersects(this.getBoundsInParent())){
                this.setIsActive(false);
            }
        
    
    }
}
}
    
