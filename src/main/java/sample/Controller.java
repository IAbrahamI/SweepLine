package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    private double x1, y1, x2, y2;
    private Random rd = new Random();
    private GraphicsContext context;
    private ArrayList<Dot> dots = new ArrayList<Dot>();

    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        this.strokeSweepLine();
    }

    //---------------------------------------------------------------------
      public void strokeSweepLine(){
        context = canvas.getGraphicsContext2D();
        x1 = 0;
        y1 = 0;
        x2 = 450;
        y2 = 0;
        createRandomDots();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {
            y1++;
            y2++;

            context.setFill(Color.WHITE);
            context.fillRect(0,0,450,600);

            strokeDots();

                for(Dot d : dots){
                    scannForCollision(d.getX(),d.getY());
                }

            context.setStroke(Color.RED);
            context.strokeLine(x1,y1,x2,y2);

            }
        };
        timer.start();
        if(x1 > 600 && x2 > 600){
            timer.stop();
        }
    }

    //---------------------------------------------------------------------
    public void addDot(int x, int y) {
        dots.add(new Dot(x, y));
    }

    //---------------------------------------------------------------------
    public void createRandomDots() {
        for (int i = 1; i <= 10; i++) {
            int randomXValue = rd.nextInt(440);
            int randomYValue = rd.nextInt(570);
            this.addDot(randomXValue + 3, randomYValue + 3);
        }
//        for(Dot d : dots){
//            System.out.println("X: "+d.getX()+" Y: "+d.getY());
//        }
    }

    //---------------------------------------------------------------------
    public void strokeDots() {
        context = canvas.getGraphicsContext2D();
        for (Dot d : dots) {
            context.setStroke(Color.GREEN);
            context.setFill(Color.GREEN);
            context.strokeOval(d.getX(), d.getY(), 6, 6);
            context.fillOval(d.getX(), d.getY(), 6, 6);
        }
    }

    //---------------------------------------------------------------------
    public void scannForCollision(int x, int y) {
        if (this.y1 == y && this.y2 == y) {
            //System.out.println("Line collided with dot");
            context.strokeOval(x - 15, y - 15, 36, 36);
        } else {
        }

    }
    //---------------------------------------------------------------------
}
