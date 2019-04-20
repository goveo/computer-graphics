import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChristmasTree extends Application {
    public static void main (String args[]) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene (root, 1600, 800);

        Ellipse standUnder = new Ellipse(400, 665, 120, 35);
        standUnder.setFill(Paint.valueOf("#5b3322"));
        standUnder.setStroke(Color.BLACK);
        standUnder.setStrokeWidth(3);
        root.getChildren().add(standUnder);

        Ellipse stand = new Ellipse(400, 655, 120, 35);
        stand.setFill(Paint.valueOf("#5b3322"));
        stand.setStroke(Color.BLACK);
        stand.setStrokeWidth(3);
        root.getChildren().add(stand);

        Arc lightTop = new Arc(400, 655, 117, 33, 90, 180);
        lightTop.setType(ArcType.ROUND);
        lightTop.setFill(Paint.valueOf("#96776a"));
        root.getChildren().add(lightTop);

        Arc lightDown = new Arc(400, 655, 70, 33, 90, 180);
        lightDown.setType(ArcType.ROUND);
        lightDown.setFill(Paint.valueOf("#5b3322"));
        root.getChildren().add(lightDown);

        Rectangle rack = new Rectangle(370, 580, 60, 85);
        rack.setArcHeight(30);
        rack.setArcWidth(80);
        rack.setFill(Paint.valueOf("#5b3322"));
        rack.setStroke(Color.BLACK);
        rack.setStrokeWidth(3);
        root.getChildren().add(rack);

        Arc treeDown = new Arc(400, 350, 300, 250, 227, 86);
        treeDown.setType(ArcType.ROUND);
        treeDown.setStroke(Color.BLACK);
        treeDown.setFill(Color.GREEN);
        treeDown.setStrokeWidth(3);
        Arc treeMiddle = new Arc(400, 250, 250, 225, 230, 80);
        treeMiddle.setType(ArcType.ROUND);
        treeMiddle.setStroke(Color.BLACK);
        treeMiddle.setFill(Color.GREEN);
        treeMiddle.setStrokeWidth(3);
        Arc treeUp = new Arc(400, 150, 250, 200, 240, 60);
        treeUp.setType(ArcType.ROUND);
        treeUp.setStroke(Color.BLACK);
        treeUp.setFill(Color.GREEN);
        treeUp.setStrokeWidth(3);
        root.getChildren().addAll(treeDown, treeMiddle, treeUp);

        Path star = getStarPath(384, 181, 35, 0);
        Path starStroke = getStarPath(380, 180, 40, 4);
        root.getChildren().addAll(starStroke, star);

        // balls
        int ballsQuantity = 21;
        Circle [] balls = new Circle[ballsQuantity];
        Ball[] ballsArray = new Ball[ballsQuantity];

        ballsArray[0] = new Ball(430, 250, Color.BLUE);
        ballsArray[1] = new Ball(470, 460, Color.BLUE);
        ballsArray[2] = new Ball(260, 505, Color.BLUE);

        ballsArray[3] = new Ball(370, 274, Color.ORANGE);
        ballsArray[4] = new Ball(410, 460, Color.ORANGE);
        ballsArray[5] = new Ball(405, 590, Color.ORANGE);

        ballsArray[6] = new Ball(350, 230, Color.rgb(0,255,0));
        ballsArray[7] = new Ball(355, 470, Color.rgb(0,255,0));
        ballsArray[8] = new Ball(280, 573, Color.rgb(0,255,0));

        ballsArray[9] = new Ball(430, 400, Color.YELLOW);
        ballsArray[10] = new Ball(330, 510, Color.YELLOW);
        ballsArray[11] = new Ball(510, 500, Color.YELLOW);

        ballsArray[12] = new Ball(330, 330, Color.PURPLE);
        ballsArray[13] = new Ball(500, 370, Color.PURPLE);
        ballsArray[14] = new Ball(420, 530, Color.PURPLE);

        ballsArray[15] = new Ball(465, 310, Color.RED);
        ballsArray[16] = new Ball(305, 430, Color.RED);
        ballsArray[17] = new Ball(340, 570, Color.RED);
        ballsArray[18] = new Ball(545, 540, Color.RED);

        ballsArray[19] = new Ball(380, 345, Color.CYAN);
        ballsArray[20] = new Ball(466, 583, Color.CYAN);

        for (int i = 0; i < balls.length; i++){
            balls[i] = new Circle(0, 0, 15);
            balls[i].setStroke(Color.BLACK);
            balls[i].setStrokeWidth(3);
            balls[i].setCenterX(ballsArray[i].getX());
            balls[i].setCenterY(ballsArray[i].getY());
            balls[i].setFill(ballsArray[i].getColor());
            root.getChildren().add(balls[i]);
        }

        int cycleCount = 2;
        int time = 20000;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time / 2), root);
        scaleTransition.setToX(-1);
        scaleTransition.setToY(-1);
        scaleTransition.setCycleCount(cycleCount);
        scaleTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(50);
        translateTransition.setToX(600);
        translateTransition.setCycleCount(cycleCount);
        translateTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(cycleCount);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                scaleTransition,
                rotateTransition
        );

        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();

        primaryStage.setTitle("Christmas tree");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Path getStarPath(int x, int y, int hypotenuse, int strokeWidth) {
        // star drawing
        double bigLeg = hypotenuse * Math.sin(Math.toRadians(54));
        double smallLeg = hypotenuse * Math.cos(Math.toRadians(54));
        double startX = x;
        double startY = y;
        double middleBigLeg = (2*bigLeg) * Math.cos(Math.toRadians(36));
        double middleSmallLeg = (2*bigLeg) * Math.sin(Math.toRadians(36));
        Path path = new Path();
        MoveTo moveTo = new MoveTo(startX, startY);
        LineTo line1 = new LineTo(startX + bigLeg * 2, startY);
        LineTo line2 = new LineTo(startX + (2 * bigLeg - middleBigLeg), startY + middleSmallLeg);
        LineTo line3 = new LineTo(startX + bigLeg, startY - smallLeg);
        LineTo line4 = new LineTo(startX + middleBigLeg, startY + middleSmallLeg);
        LineTo line5 = new LineTo(startX, startY);
        path.getElements().add(moveTo);
        path.getElements().addAll(line1, line2, line3, line4, line5);
        path.setFill(Color.RED);
        path.setStrokeWidth(strokeWidth);
        return path;
    }

    private class Ball {
        private int x;
        private int y;
        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Ball(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}