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
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private Random rd = new Random();
    private ArrayList<Dot> dots= new ArrayList<Dot>();
    @FXML
    void startAction(ActionEvent event) {

        this.strokeSweepLine();
        this.strokeDots();
    }

    public void strokeSweepLine(){
        GraphicsContext context = canvas.getGraphicsContext2D();
        x1 = 0;
        y1 = 0;
        x2 = 450;
        y2 = 0;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {
                    y1++;
                    y2++;
                    context.clearRect(x1,y1-1,x2,y2-1);
                    context.strokeLine(x1,y1,x2,y2);
                    context.setStroke(Color.RED);


            }
        };
        timer.start();
        if(x1 > 600 && x2 > 600){
            timer.stop();
        }
    }

    public void addDot(int x, int y){
        dots.add(new Dot(x,y));
    }

    public void createRandomDots(){
        for(int i=1;i<=8;i++){
            int randomXValue = rd.nextInt(440);
            int randomYValue = rd.nextInt(590);
            this.addDot(randomXValue,randomYValue);
        }
        for(Dot d : dots){
            System.out.println("X: "+d.getX()+" Y: "+d.getY());
        }
    }

    public void strokeDots(){
        this.createRandomDots();
        GraphicsContext context = canvas.getGraphicsContext2D();
        for(Dot d: dots){
            context.setStroke(Color.GREEN);
            context.setFill(Color.GREEN);
            context.strokeOval(d.getX(),d.getY(),5,5);
            context.fillOval(d.getX(),d.getY(),5,5);

        }
    }
}
