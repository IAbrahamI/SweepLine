package controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;
    private int minX = 0;
    private int maxX = 450;
    private int minY = 0;
    private int maxY = 550;
    private int valueForLine = 0;

    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        Calculation c = new Calculation();
        this.drawVoronoi(c);

    }
    //---------------------------------------------------------------------
    public void drawVoronoi(Calculation c) {
        gc = canvas.getGraphicsContext2D();

        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {

                valueForLine++;
                refreshCanvas();
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
    public void createAndDrawDots(){

    }
    //---------------------------------------------------------------------

}