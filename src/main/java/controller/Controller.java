package controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.Dot;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    @FXML
    private Slider slider;

    private GraphicsContext gc;
    private int amountOfDots = 10;
    private int minX = 0;
    private int maxX = 450;
    private int minY = 0;
    private int maxY = 630;
    private int valueForLine = 0;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        Calculation c = new Calculation();
        this.drawVoronoi(c);

    }
    //---------------------------------------------------------------------
    public void drawVoronoi(final Calculation c) {
        gc = canvas.getGraphicsContext2D();
        createDots(c);

        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {

                valueForLine++;
                refreshCanvas();
                drawDots();
                drawSweepLine();

            }
        };
        timer.start();
    }
    //---------------------------------------------------------------------
    public void refreshCanvas(){
        gc.setFill(Color.WHITE);
        gc.fillRect(minX,minY,maxX,maxY);
    }
    //---------------------------------------------------------------------
    public void drawSweepLine(){
        gc.setStroke(Color.RED);
        gc.strokeLine(minX,valueForLine,maxX,valueForLine);
    }
    //---------------------------------------------------------------------
    public void drawDots(){
        for(Dot d : dots){
            gc.setStroke(Color.BLUE);
            gc.strokeOval(d.getX()+3,d.getY()+3,6,6);
            gc.fillOval(d.getX()+3,d.getY()+3,6,6);
        }
    }
    //---------------------------------------------------------------------
    // Implementations
    //---------------------------------------------------------------------
    public void addDot(int x, int y){
        dots.add(new Dot(x,y));
    }
    //---------------------------------------------------------------------
    public void createDots(Calculation c){
        for(int i = 0;i < Math.round(slider.getValue());i++){
            int xValue = c.createRandomXDot(maxX);
            int yValue = c.createRandomYDot(maxY);
            this.addDot(xValue,yValue);
            System.out.println(Math.round(slider.getValue()));
        }
    }
    //---------------------------------------------------------------------

}