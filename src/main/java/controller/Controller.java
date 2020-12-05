package controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Dot;

import java.util.ArrayList;
import java.util.Collections;

public class Controller{

    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField points;

    private GraphicsContext gc;
    private int amountOfDots = 10;
    private int minX = 0;
    private int maxX = 450;
    private int minY = 0;
    private int maxY = 500;
    private int valueForLine = 0;
    private ArrayList<Dot> dots = new ArrayList<Dot>();

    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        Calculation c = new Calculation();
        this.drawVoronoi(c);

    }

    //---------------------------------------------------------------------
    public void drawVoronoi(Calculation c) {
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
    public void refreshCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(minX, minY, maxX, maxY);
    }

    //---------------------------------------------------------------------
    public void drawSweepLine() {
        gc.setStroke(Color.RED);
        gc.strokeLine(minX, valueForLine, maxX, valueForLine);
    }

    //---------------------------------------------------------------------
    public void drawDots() {
        for (Dot d : dots) {
            gc.setStroke(Color.BLUE);
            gc.strokeOval(d.getX() + 3, d.getY() + 3, 6, 6);
            gc.fillOval(d.getX() + 3, d.getY() + 3, 6, 6);
        }
    }

    //---------------------------------------------------------------------
    // Implementations
    //---------------------------------------------------------------------
    public void addDot(int x, int y) {
        dots.add(new Dot(x, y));
    }
    public void sortDots(){
        Collections.sort(dots);
        for (Dot d: dots){
            System.out.println("X-Value: "+d.getX());
        }
    }
    //---------------------------------------------------------------------
    public void createDots(Calculation c) {
        if (!points.getText().matches("[0-9]*")) {
        } else {
            int amountOfDots = Integer.parseInt(points.getText());
            for (int i = 0; i < amountOfDots; i++) {
                int xValue = c.createRandomXDot(maxX);
                int yValue = c.createRandomYDot(maxY);
                this.addDot(xValue, yValue);
            }
        }
        sortDots();
    }
    //---------------------------------------------------------------------
    // Store functions
    //---------------------------------------------------------------------

    //---------------------------------------------------------------------
}