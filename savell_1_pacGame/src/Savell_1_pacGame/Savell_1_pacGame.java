/*
 * This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package Savell_1_pacGame;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.shape.Shape;
import javafx.event.Event;
import javafx.scene.Node;

/**
 * For more information see:
 * http://stackoverflow.com/questions/21331519/how-to-get-smooth-animation-with-keypress-event-in-javafx
 * http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
 * http://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 */
public class Savell_1_pacGame extends Application {

    static ArrayList<Wall> wallz = new ArrayList();
    static ArrayList<Rectangle> badblockz = new ArrayList();
    static ArrayList<String> input = new ArrayList<String>();
    static ArrayList<Dot> dotz = new ArrayList();
    static ArrayList<FakeDot> fdotz = new ArrayList();
    public static Group root;
    static Rectangle rect;
    static pacMan pacman;
    static Wall wall;
    static int mouthCounter;
    static boolean [][] powerpellets = new boolean [1100][600];  

    @Override
    public void start(Stage primaryStage) {

        root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("pac Man");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(1100, 600);

        //Notice gc is not being used yet 
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //notice we are creating shape objects 
        pacman = new pacMan(300, 300);

        rect = new Rectangle(150, 50, 25, 25);
        rect.setFill(Color.BLUE);
        badblockz.add(rect);
        makeWalls();
        makeDots();

//        for (int i = 0; i < 10; i++) {
//            System.out.println(i);
//            Rectangle rectangle = new Rectangle();
//            rectangle.setX(0);
//            rectangle.setY(i*20);
//            rectangle.setWidth(20);
//            rectangle.setHeight(20);
//            rectangle.setFill(Color.BLACK);
//            root.getChildren().add(rectangle);
//
//        }
        //we have created an animation timer --- the class MUST be overwritten - look below 
        AnimationTimer timer = new MyTimer();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                input.remove(code);
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    pacman.left = false;
                    pacman.right = false;
                    pacman.up = false;
                    pacman.down = false;
                }
                if (event.getCode() == KeyCode.RIGHT) { // don't use toString here!!!
                    pacman.right = true;
                    pacman.setFill(Color.CADETBLUE);
                    checkBounds(pacman);
                    System.out.println("right is true");
                } else if (event.getCode() == KeyCode.LEFT) {
                    pacman.left = true;
                    pacman.setFill(Color.RED);
                    checkBounds(pacman);
                    System.out.println("left is true");
                } else if (event.getCode() == KeyCode.UP) {
                    pacman.up = true;
                    pacman.setFill(Color.GREEN);
                    checkBounds(pacman);
                    System.out.println("up is true");
                } else if (event.getCode() == KeyCode.DOWN) {
                    pacman.down = true;
                    pacman.setFill(Color.ORANGE);
                    checkBounds(pacman);
                    System.out.println("down is true");
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
//                    pacman.left = false;
//                    pacman.right = false;
//                    pacman.up = false;
//                    pacman.down = false;

                }
            }
        });

        //try disabling canvas --- notice the difference 
        root.getChildren().add(canvas);
        //notice we are manually adding the shape objects to the "root" window
        root.getChildren().add(rect);
        root.getChildren().add(pacman);

        timer.start();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     *
     * The same as before main just calls the args described above
     */
    ///  vvvvvvvvvvvv   MAIN vvvvvvvvvvv
    public static void main(String[] args) {
        launch(args);
    }

    //// ^^^^^^^^^^^  MAIN ^^^^^^^^^^^^^
    // we create our time here --- to animate 
    private class MyTimer extends AnimationTimer {

        boolean movedown = true;

        /// handle is defined by the abstract parent class -- it must be redined 
        /// this is what happens again and again until stop()
        @Override
        public void handle(long now) {
            // You can look at the key presses here as well -- this is one of many. Try others
            if (input.contains("LEFT")) {
                pacman.setCenterX(pacman.getCenterX() - 5);
            }
          //  System.out.println(pacman.getCenterX() + ",");
            handlePacman();
//            doHandle();
            /// notice doHandle()  is what happens again and again it's defined below

        }

        private void handlePacman() {
            if (checkWall()) {
                System.out.println("WALLS");
            }
            mouthCounter += 1;
            if (mouthCounter > 6) {
                if (pacman.open) {
                    pacman.closed = false;
                    pacman.setLength(360);
                    pacman.closed = true;

                    mouthCounter = 0;

                }
                if (pacman.closed) {
                    pacman.open = false;
                    pacman.setLength(270);
                    mouthCounter = 0;
                    pacman.open = true;
                }
            }

            checkBounds(pacman);
            if (pacman.right) {
                pacman.setCenterX(pacman.getCenterX() + 3);
                pacman.setRotate(0);
                if (checkWall()) {
                    pacman.right = false;
                    pacman.setCenterX(pacman.getCenterX() - 5);

                }

//                for (Wall w : wallz) {
//                    if (w.getBoundsInParent().intersects(pacman.getBoundsInParent())) {
//                        pacman.setCenterX(pacman.getCenterX() - 3);
//                    }
//                }
            } else if (pacman.left) {
                pacman.setCenterX(pacman.getCenterX() - 3);
                pacman.setRotate(180);
                if (checkWall()) {
                    pacman.left = false;
                    pacman.setCenterX(pacman.getCenterX() + 5);
                }
//                for (Wall w : wallz) {
//                    if (w.getBoundsInParent().intersects(pacman.getBoundsInParent())) {
//                        pacman.setCenterX(pacman.getCenterX() + 3);
//                    }
//                }
            } else if (pacman.down) {
                pacman.setCenterY(pacman.getCenterY() + 3);
                pacman.setRotate(90);

                if (checkWall()) {
                    pacman.down = false;
                    pacman.setCenterY(pacman.getCenterY() - 5);
                }
//                for (Wall w : wallz) {
//                    if (w.getBoundsInParent().intersects(pacman.getBoundsInParent())) {
//                        pacman.setCenterY(pacman.getCenterY() - 3);
//                    }
//                }
            } else if (pacman.up) {
                pacman.setCenterY(pacman.getCenterY() - 3);
                pacman.setRotate(270);
                if (checkWall()) {
                    pacman.up = false;
                    pacman.setCenterY(pacman.getCenterY() + 5);
                }
//                for (Wall w : wallz) {
//                    if (w.getBoundsInParent().intersects(pacman.getBoundsInParent())) {
//                        pacman.setCenterY(pacman.getCenterY() + 3);
//                    }
//                }
            }

            // stop();
            // System.out.println("Animation stopped");
        }

        private void doHandle() {
//            checkBounds(pacman);
            if (movedown && rect.getY() < 555) {
                rect.setY(rect.getY() + 5);
            }
            if (!movedown && rect.getY() > 1) {
                rect.setY(rect.getY() - 5);
            }
            if (rect.getY() > 550) {
                movedown = false;
            }
            if (rect.getY() < 1) {
                movedown = true;
            }

            // stop();
            // System.out.println("Animation stopped");
        }
    }

    public void makeDots() {
//        for (int i = 1; i < 8; i++) {
//            for (int j = 0; j < 2; j++) {
//                Dot dot = new Dot(10 + (i * 47), 65 + (j * 490));
//            }
//        }
//        for (int i = 8; i < 14; i++) {
//            for (int j = 0; j < 2; j++) {
//                Dot dot = new Dot(i * 53, 60 + (j * 490));
//            }
//        }
//        for (int i = 15; i < 21; i++) {
//            for (int j = 0; j < 2; j++) {
//                Dot dot = new Dot(i * 52, 60 + (j * 490));
//            }
//        }
//        for (int i = 0; i < 8; i++) {
//            Dot dot = new Dot(60, 100 + (i * 60));
//        }
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 3; j++) {
//                Dot dot = new Dot(330 + (j * 360), 100 + (i * 60));
//            }
//        }
//        for ( int i = 0; i < 4; i ++) {
//            Dot dot = new Dot(145+(i*45), 180);
//            
//        }
//        for (int i = 4; i < 19; i ++){
//            Dot dot = new Dot(145+(44*i), 180);
//        }


 for (int i = 0; i < 1100; i++) {
            for (int j = 0; j < 600; j++) {
                powerpellets[i][j] = false; }}

 for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 11; j++) {
                powerpellets[i*50][j*50] = true; }}

int dotnum = 0; 
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 11; j++) {
                FakeDot fdot = new FakeDot(i * 50, j*50);
                for (FakeDot fd : fdotz) {
                    for (Wall w : wallz) {
                        if (fd.getBoundsInParent().intersects(w.getBoundsInParent())) {
                            System.out.printf("\n *********** \n Intersection at %d %d \n ***********\n", i*50 , j*50);
                             powerpellets[i*50][j*50] = false; 
                            
                        } else {
                            dotnum++; 
                            System.out.printf("You have %d dots ---- ", dotnum);
                            System.out.printf("dot at %d %d \n", i, j); 
                          
                        }
                    }
                }

            }
        }
        
        for (FakeDot fd : fdotz) {
                    for (Wall w : wallz) {
                         if (fd.getBoundsInParent().intersects(w.getBoundsInParent())) {
                        fd.imok = false; 
                    }
                    }}
        
        dotnum = 0; 
        
        for (FakeDot fd : fdotz) {
            if(fd.imok){
              
                 fd.setFill(Color.INDIANRED);
                 fd.gobbleok = true; 
            }
            else { 
              fd.setFill(Color.TRANSPARENT);
              fd.gobbleok = false; 
        }
//      for (int i = 0; i < 12; i++) {
//            for (int j = 0; j < 6; j++) {
//                if (powerpellets[i*50][j*50]){
//                    Dot dot = new Dot(i*50, j*50); 
//                    dotnum++; 
//                       System.out.printf("\n You really have %d dots", dotnum);
//                    
//                }
//            }
    }
    }

    public void makeWalls() {
        Wall topWall = new Wall(0, 0, 30, 1100);
        Wall leftWall = new Wall(0, 0, 600, 30);
        Wall rightWall = new Wall(1070, 0, 600, 30);
        Wall bottomWall = new Wall(0, 570, 30, 1100);
        Wall botSquare = new Wall(470, 345, 30, 175);
        Wall sqaureTop = new Wall(470, 225, 30, 75);
        Wall sqareTop = new Wall(595, 225, 30, 75);

        for (int i = 0; i < 3; i++) {
            Wall topwall = new Wall(360 + ((i - 1) * 360), 0, 150, 30);
            //    topwall.setFill(Color.AQUAMARINE); 
            Wall botWall = new Wall(360 + ((i - 1) * 360), 450, 150, 30);
            Wall topLlong = new Wall(100 + ((i - 1) * 870), 100, 150, 30);
            Wall botLlong = new Wall(100 + ((i - 1) * 870), 350, 150, 30);
            Wall topLShort = new Wall(100 + ((i - 1) * 725), 100, 30, 170);
            Wall botLshort = new Wall(100 + ((i - 1) * 725), 470, 30, 170);
            Wall midTop = new Wall(220 + ((i - 1) * 595), 220, 30, 75);
            Wall midBot = new Wall(220 + ((i - 1) * 595), 350, 30, 75);
            Wall middle = new Wall(470, 100 + ((i - 1) * 370), 30, 175);
            Wall sqaureSides = new Wall(i * (470 - ((i - 1) * 150)), 225, 150, 30);

        }

//        root.getChildren().add()
        for (int i = 12; i < 12; i++) {
//        wall = new Wall();
        }
    }

    private void checkBounds(pacMan pacman) {
        // checkBounds is called in two different locations --- it's really only necessary in the animation dohandle
        // experiment - check the differences

        boolean collisionDetected = false;

        // notice the difference in how an ArrayList iterates through items 
        for (Rectangle badblock : badblockz) {
            if (pacman.getBoundsInParent().intersects(badblock.getBoundsInParent())) {
                collisionDetected = true;
                badblock.setFill(Color.RED);
            } else {
                badblock.setFill(Color.BLUE);
            }
        }
        if (collisionDetected) {
            pacman.setFill(Color.RED);
        } else {
            pacman.setFill(Color.ORANGE);
        }
    }

    public boolean checkWall() {
        for (Wall w : wallz) {
            if (pacman.getBoundsInParent().intersects(w.getBoundsInParent())) {
                return true;
            }
        }
        return false;

    }

}
