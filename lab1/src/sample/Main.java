package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class Main extends Application {

    final int HEIGHT = 450;
    final int WIDTH = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Lab 1");

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        //background
        scene.setFill(Color.DARKBLUE);

        // house body
        double houseBodyX = WIDTH/8;
        double houseBodyY = HEIGHT*0.55;
        double houseWidth = WIDTH*0.8;
        double houseHeight = HEIGHT*0.4;
        Rectangle house = new Rectangle(houseBodyX, houseBodyY, houseWidth, houseHeight);

        house.setFill(Color.DARKRED);
        root.getChildren().add(house);

        // roof
        double roofDot1x = WIDTH/8;
        double roofDot1y = HEIGHT*0.56;

        double roofDot2x = WIDTH/8;
        double roofDot2y = HEIGHT*0.53;

        double roofDot3x = WIDTH*0.48;
        double roofDot3y = HEIGHT*0.26;

        double roofDot4x = WIDTH*0.93;
        double roofDot4y = HEIGHT*0.53;

        double roofDot5x = WIDTH*0.93;
        double roofDot5y = HEIGHT*0.55;

        Polygon roof = new Polygon(roofDot1x, roofDot1y,
                                   roofDot2x, roofDot2y,
                                   roofDot3x, roofDot3y,
                                   roofDot4x, roofDot4y,
                                   roofDot5x, roofDot5y);
        roof.setFill(Color.GRAY);
        root.getChildren().add(roof);

        // windows
        Rectangle window1 = new Rectangle(WIDTH*0.25, HEIGHT*0.65, houseHeight*0.4, houseHeight*0.4);
        window1.setFill(Color.YELLOW);
        root.getChildren().add(window1);

        Rectangle window2 = new Rectangle(WIDTH*0.5, HEIGHT*0.65, houseHeight*0.37, houseHeight*0.4);
        window2.setFill(Color.YELLOW);
        root.getChildren().add(window2);

        // stars
        double starSize = WIDTH*0.025; // WIDTH 800/40
        Rectangle star1 = new Rectangle(WIDTH*0.03, HEIGHT*0.2, starSize, starSize);
        star1.setFill(Color.YELLOW);
        Rectangle star2 = new Rectangle(WIDTH*0.20, WIDTH*0.03, starSize, starSize);
        star2.setFill(Color.YELLOW);
        Rectangle star3 = new Rectangle(WIDTH*0.6, HEIGHT*0.2, starSize, starSize);
        star3.setFill(Color.YELLOW);
        Rectangle star4 = new Rectangle(WIDTH*0.75, WIDTH*0.03, starSize, starSize);
        star4.setFill(Color.YELLOW);

        root.getChildren().add(star1);
        root.getChildren().add(star2);
        root.getChildren().add(star3);
        root.getChildren().add(star4);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
