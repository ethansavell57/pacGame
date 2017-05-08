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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * For more information see:
 * http://stackoverflow.com/questions/21331519/how-to-get-smooth-animation-with-keypress-event-in-javafx
 * http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
 * http://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 */
public class Savell_1_pacGame extends Application {

    static ArrayList<Wall> wallz = new ArrayList();
    static ArrayList<Junction> junctionz = new ArrayList();
    static ArrayList<gameUser> users = new ArrayList();
    static boolean makeScoreBoardStuff;
    static ArrayList<Rectangle> badblockz = new ArrayList();
    static ArrayList<String> input = new ArrayList<String>();
    static ArrayList<Dot> dotz = new ArrayList();
    static ArrayList<FakeDot> fdotz = new ArrayList();
    static ArrayList<Ghost> ghostz = new ArrayList();
    public static Group root;
    static Rectangle rect;
    static Wall mario;
    static Ghost ghost;
    static Mark mark;
    static boolean wallInCenter = true;
    static pacMan pacman;
    static Wall wall;
    static Wall naruto;
    static Junction junct;
    static int junctCounter;
    static int mouthCounter;
    static boolean[][] powerpellets = new boolean[1100][600];
    static Random randy;
    static int calcedScore;
    static int finalScore;
    static int numdot;
    static int divisor;
    static Button submit;
    static TextField textField;
    static boolean scoreNotSet = true;
    static boolean youWon;
    static boolean lbIsShowing;
    static boolean leaderBoardShowing;
    static boolean leaderBoardHasNotBeenMade;
    static Text score;

    @Override
    public void start(Stage primaryStage) {

        root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("pac Man");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(1100, 600);

        Rectangle background = new Rectangle();
        background.setX(0);
        background.setY(0);
        background.setHeight(600);
        background.setWidth(1100);
        background.setFill(Color.CORNFLOWERBLUE);
        root.getChildren().add(background);

        randy = new Random();

        //Notice gc is not being used yet 
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //notice we are creating shape objects 
        pacman = new pacMan(300, 300);
        ghost = new Ghost(2);
        ghost = new Ghost(1);
        ghost = new Ghost(3);
        ghost = new Ghost(4);
        mario = new Wall(590, 275, 90);
        rect = new Rectangle(150, 50, 25, 25);
        rect.setFill(Color.BLUE);
        badblockz.add(rect);
        mark = new Mark(580, 156);
        junct = new Junction(420, 250, true);
        junct = new Junction(250, 300, true);
        score = new Text(50, 40, "SCORE-" + Integer.toString(calcedScore));
        score.setFill(Color.WHITE);
        score.setFont(new Font(50));

//        junct = new Junction(600, 300);
//        junct = new Junction(600, 270);
        makeWalls();
        makeDots();

//        for (int i = 0; i < 10; i++) {
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
                if (event.getCode() == KeyCode.W) {
                    for (Ghost g : ghostz) {
                        g.up = true;
                    }
                }
//                if (event.getCode() == KeyCode.E) {
//                    youWon = true;
//                    if (youWon) {
//                        lbIsShowing = true;
//                    }
//                }
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    pacman.left = false;
                    pacman.right = false;
                    pacman.up = false;
                    pacman.down = false;
                }
                if (event.getCode() == KeyCode.L) {
                    pacman.rawScore = 157;
                }

                if (event.getCode() == KeyCode.T) {
                    pacman.rawScore = 150;
                }
                if (event.getCode() == KeyCode.RIGHT) { // don't use toString here!!!
                    pacman.right = true;
                    pacman.setFill(Color.CADETBLUE);
                    checkBounds(pacman);
                } else if (event.getCode() == KeyCode.LEFT) {
                    pacman.left = true;
                    pacman.setFill(Color.RED);
                    checkBounds(pacman);
                } else if (event.getCode() == KeyCode.UP) {
                    pacman.up = true;
                    pacman.setFill(Color.GREEN);
                    checkBounds(pacman);
                } else if (event.getCode() == KeyCode.DOWN) {
                    pacman.down = true;
                    pacman.setFill(Color.ORANGE);
                    checkBounds(pacman);
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
        root.getChildren().add(pacman);
        root.getChildren().add(score);
        
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
            numdot = 0;
            for (FakeDot fd : fdotz) {

                numdot += 1;
            }

            if (input.contains("LEFT")) {
                pacman.setCenterX(pacman.getCenterX() - 5);
            }
            handlePacman();
            checkDots();
            handleText();

            for (Junction j : junctionz) {
                j.handleJunctions();
            }
            for (Ghost g : ghostz) {
                g.handleGhost();
            }
            if (wallInCenter) {
                wallz.remove(naruto);
            }

            if (youWon) {

            }

//                if (lbIsShowing) {
//                    System.out.println("leaderBoard is showing");
//                    if (leaderBoardHasNotBeenMade) {
//                        System.out.println("userI is true");
//                        Label userLabel = new Label("Your Name:");
//                        submit = new Button("Submit");
//                        textField = new TextField();
//                        HBox hb = new HBox();
//                        hb.setSpacing(10);
//                        hb.setAlignment(Pos.CENTER);
//                        submit.isDefaultButton();
//                        root.getChildren().add(hb);
//
//                        hb.getChildren().addAll(userLabel, textField, submit);
//                    }
//
//                    submit.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent e) {
//                            if (scoreNotSet) {
//                                String name = new String();
//                                name = (textField.getText());
//
////                                      System.out.println(name);
//                                System.out.println("clicking");
//                                gameUser user = new gameUser(finalScore, name);
//                                user.setName(name);
//
//                                user.setScore(finalScore);
//                                users.add(user);
//                                String userLine = new String();
//                                userLine = (user.getScore() + " " + user.getName());
//                                System.out.println(userLine);
//                                String lbtxt = "leaderBoard.txt";
//                                writeFile data = new writeFile(lbtxt, true);
//                                try {
//                                    data.writeToFile(userLine);
//                                } catch (IOException ex) {
//                                    Logger.getLogger(Savell_1_pacGame.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                users.clear();
//                                try {
//                                    Scanner myScanner = new Scanner(new File(lbtxt));
//                                    while (myScanner.hasNextLine()) {
//                                        int i = 0;
//                                        String line = myScanner.nextLine();
//                                        if (!line.isEmpty()) {
//                                            while (line.charAt(i) != ' ') {
//                                                i++;
//                                            }
//                                            int sc = Integer.parseInt(line.substring(0, i));
//                                            String nm = line.substring(i + 1, line.length());
//                                            gameUser player1 = new gameUser(sc, nm);
//                                            player1.setScore(sc);
//                                            player1.setName(nm);
//                                            users.add(player1);
//
//                                        }
//
//                                    }
//                                } catch (FileNotFoundException g) {
//                                }
//
//                                leaderBoardShowing = true;
//
//                            }
//                        }
//
//                    });
//                }
//            doHandle();
            /// notice doHandle()  is what happens again and again it's defined below
        }

//        private void handleGhost() {
//            if (checkWallGhost()) {
//                System.out.println("GHost Is touching wall");
//            }
//            if (ghost.right) {
//                ghost.setX(ghost.getX() + 3);
//                if (checkWall()) {
//                    ghost.right = false;
//                    ghost.setX(ghost.getX() - 5);
//
//                }
//
//            } else if (ghost.left) {
//                ghost.setX(ghost.getX() - 3);
//                ghost.setRotate(180);
//                if (checkWallGhost()) {
//                    ghost.left = false;
//                    ghost.setX(ghost.getX() + 5);
//                }
//
//            } else if (ghost.down) {
//                ghost.setY(ghost.getY() + 3);
//                ghost.setRotate(90);
//
//                if (checkWallGhost()) {
//                    ghost.down = false;
//                    ghost.setY(ghost.getY() - 5);
//                }
//
//            } else if (ghost.up) {
//                ghost.setY(ghost.getY() - 3);
//                ghost.setRotate(270);
//                if (checkWallGhost()) {
//                    ghost.up = false;
//                    ghost.setY(ghost.getY() + 5);
//                }
//            }
//        }
        public void handleText() {
            score.setText("SCORE-" + Integer.toString(calcedScore));

        }

        public void makeBackground() {
            Rectangle background = new Rectangle();
            background.setX(0);
            background.setY(0);
            background.setHeight(600);
            background.setWidth(1100);
            background.setFill(Color.CORNFLOWERBLUE);
            root.getChildren().add(background);
        }

        public void handlePacman() {
            
            System.out.println(pacman.rawScore);
            if (pacman.getRawScore() == 157) {
                youWon = true;
                finalScore = calcedScore;
                System.out.println("YOU WON");
                System.out.println("the final score is" + finalScore);
                root.getChildren().clear();
                makeScoreBoardStuff = true;
                pacman.rawScore += 1;
                
            }
            
            
            if (pacman.rawScore == 158) {
                if (makeScoreBoardStuff){
                    
                
                Text finalScoreText = new Text();
                finalScoreText.setText("Final Score - " + Integer.toString(finalScore));
                finalScoreText.setFont(new Font(60));
                finalScoreText.setX(475);
                finalScoreText.setY(100);
                finalScoreText.setFill(Color.TOMATO);
                root.getChildren().add(finalScoreText);
                Text leaderBoard = new Text();
                leaderBoard.setFont(new Font(60));
                leaderBoard.setX(150);
                leaderBoard.setY(250);
                leaderBoard.setFill(Color.STEELBLUE);
                root.getChildren().add(leaderBoard);
                Label userLabel = new Label("Your Name:");
                submit = new Button("Submit");
                textField = new TextField();
                HBox hb = new HBox();
                hb.setSpacing(10);
                hb.setAlignment(Pos.CENTER);
                submit.isDefaultButton();
                root.getChildren().add(hb);
                hb.getChildren().addAll(userLabel, textField, submit);
                makeScoreBoardStuff = false;
                }
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        System.out.println("clicking");
                        if (scoreNotSet) {
                            String name = new String();
                            name = (textField.getText());
                            System.out.println(name);

//                                    System.out.println(name);
                            gameUser user = new gameUser(finalScore, name);
                            user.setName(name);
                            user.setScore(finalScore);
                            users.add(user);
                            String userLine = new String();
                            userLine = (user.getScore() + " " + user.getName());
                            System.out.println(userLine);
                            String lbtxt = "leaderBoard.txt";
                            writeFile data = new writeFile(lbtxt, true);
                            try {
                                data.writeToFile(userLine);
                                System.out.println("trying");
                            } catch (IOException ex) {
                                Logger.getLogger(Savell_1_pacGame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            users.clear();
                            try {
                                Scanner myScanner = new Scanner(new File(lbtxt));
                                while (myScanner.hasNextLine()) {
                                    int i = 0;
                                    String line = myScanner.nextLine();
                                    if (!line.isEmpty()) {
                                        while (line.charAt(i) != ' ') {
                                            i++;
                                        }
                                        int sc = Integer.parseInt(line.substring(0, i));
                                        String nm = line.substring(i + 1, line.length());
//                                                System.out.println(sc);
                                        gameUser player1 = new gameUser(sc, nm);
                                        player1.setScore(sc);
                                        player1.setName(nm);
                                        users.add(player1);
//                                                System.out.println("score-" + player1.score);
//                                                System.out.println("name-" + player1.getName());

                                    }

                                }
                            } catch (FileNotFoundException g) {
                            }

                            leaderBoardShowing = true;

                        }
                                if (leaderBoardShowing) {
                scoreNotSet = false;

                System.out.println("before sorting");
                System.out.println(users);
                Collections.sort(users);

                System.out.println("after sorting");
                System.out.println(users);
                ArrayList<gameUser> topTen = new ArrayList();
                Text rankText = new Text(160, 70, "RANK");
                Text nameText = new Text(300, 70, "NAME");
                Text scoreText = new Text(10, 70, "SCORE");
                rankText.setFont(new Font(40));
                rankText.setFill(Color.BLACK);
                nameText.setFont(new Font(40));
                nameText.setFill(Color.BLACK);
                scoreText.setFont(new Font(40));
                scoreText.setFill(Color.BLACK);
                root.getChildren().addAll(rankText, nameText, scoreText);
                for (int i = 1; i < 11; i++) {
                    Text text = new Text();
                    text.setText(Integer.toString(i) + "          " + users.get(i));
                    text.setFont(new Font(40));
                    text.setX(20);
                    text.setY(100 + i * 40);
                    text.setFill(Color.STEELBLUE);
                    root.getChildren().add(text);

                }
            }
                    }

                });

            }

            if (!pacman.isAlive) {

            }
            checkMiddleWall(naruto);
            if (checkWall()) {
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
        }
    }

    public void calcScore() {
        if (junctCounter < 600) {
            divisor = 1;
        } else {
            divisor = (int) Math.ceil(junctCounter / 70);

        }
        calcedScore = (int) Math.ceil(pacman.getRawScore() * junctCounter / divisor);
    }

    public void checkDots() {
        for (FakeDot fd : fdotz) {
            if (pacman.getBoundsInParent().intersects(fd.getBoundsInParent())) {
                pacman.rawScore += 1;
                root.getChildren().remove(fd);

                fd.setCenterX(2000);
                fd.setCenterY(2000);
                calcScore();

            }
        }
    }

    public void makeDots() {

        for (int i = 0; i < 1100; i++) {
            for (int j = 0; j < 600; j++) {
                powerpellets[i][j] = false;
            }
        }

        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                powerpellets[i * 50][j * 50] = true;
            }
        }

        int dotnum = 0;
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                FakeDot fdot = new FakeDot(i * 50, j * 50);
                for (FakeDot fd : fdotz) {
                    for (Wall w : wallz) {
                        if (fd.getBoundsInParent().intersects(w.getBoundsInParent())) {
                            powerpellets[i * 50][j * 50] = false;

                        } else {
                            dotnum++;

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
            }
        }

        dotnum = 0;

        for (FakeDot fd : fdotz) {
            if (fd.imok) {

                fd.setFill(Color.INDIANRED);
                fd.gobbleok = true;
            } else {
                fd.setFill(Color.BLACK);
                fd.gobbleok = false;
                fd.setCenterX(-1000);
                fd.setCenterY(-1000);
            }
//      for (int i = 0; i < 12; i++) {
//            for (int j = 0; j < 6; j++) {
//                if (powerpellets[i*50][j*50]){
//                    Dot dot = new Dot(i*50, j*50); 
//                    dotnum++; 
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
        naruto = new Wall(500, 300);
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                junct = new Junction(200 + ((i - 1) * 400), 60 + (400 * (1 - j)), true);
            }

        }
        junct = new Junction(58, 300, true);
        junct = new Junction(700, 300, true);
        junct = new Junction(975, 275, true);
        junct = new Junction(100, 300, true);
        junct = new Junction(350, 300, true);

        junct = new Junction(900, 200, true);
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

    public void checkMiddleWall(Wall naruto) {
        if (wallInCenter) {
            wallz.remove(naruto);
            wallInCenter = false;
        }
    }

    private void checkBounds(pacMan pacman) {
        // checkBounds is called in two different locations --- it's really only necessary in the animation dohandle
        // experiment - check the differences

        boolean collisionDetected = false;

        // notice the difference in how an ArrayList iterates through items 
        for (Ghost g : ghostz) {
            if (pacman.getBoundsInParent().intersects(g.getBoundsInParent())) {
                collisionDetected = true;
                pacman.setFill(Color.RED);
                pacman.isAlive = true;
            } else {
                pacman.setFill(Color.PLUM);
            }
        }
        if (collisionDetected) {
            pacman.setFill(Color.RED);
        } else {
            pacman.setFill(Color.ORANGE);
        }
    }

//    public boolean checkWallGhost() {
//        for (Ghost g : ghostz) {
//            for (Wall w : wallz) {
//
//                if (g.getBoundsInParent().intersects(w.getBoundsInParent())) {
//                    return true;
//                }
//            }
//
//        }
//        return false;
//    }
    public boolean checkWall() {
        for (Wall w : wallz) {
            if (pacman.getBoundsInParent().intersects(w.getBoundsInParent())) {
                return true;
            }
        }
        return false;

    }

}
