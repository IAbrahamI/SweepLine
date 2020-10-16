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

    private double xLine1, yLine1, xLine2, yLine2, xParabole;
    private Random rd = new Random();
    private GraphicsContext context;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Dot> activatedDots = new ArrayList<Dot>();
    private ArrayList<Parabole> paraboles = new ArrayList<Parabole>();
    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        this.strokeSweepLine();
    }
    //---------------------------------------------------------------------
    public void strokeSweepLine() {
        context = canvas.getGraphicsContext2D();
        xLine1 = 0;
        yLine1 = 0;
        xLine2 = 450;
        yLine2 = 0;
        createRandomDots();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {

                yLine1++;
                yLine2++;
                context.setFill(Color.WHITE);
                context.fillRect(0, 0, 450, 550);

                strokeDots();
                for (Dot d : dots) {
                    scannForLineDotCollision(d.getX(), d.getY());
                }
                drawParaboleforEachDot();

                context.setStroke(Color.RED);
                context.strokeLine(xLine1, yLine1, xLine2, yLine2);
//                if (y1 >= 550 && y2 >= 550) {
//                    //deleteDots();
//                }
            }
        };
        timer.start();
    }
    //---------------------------------------------------------------------
    public void addDot(int x, int y) {
        dots.add(new Dot(x, y));
    }

    public void addActivatedDot(int x, int y, double xParabole) {
        activatedDots.add(new Dot(x, y, xParabole));
    }

    public void deleteDots(){
        for(Dot d : dots){
            dots.clear();
        }
        for(Dot d : activatedDots){
            activatedDots.clear();
        }
    }

    public void addParabole(double startX, double startY, double curveX, double curveY, double endX, double endY){
        paraboles.add(new Parabole(startX,startY,curveX,curveY,endX,endY));
    }
    //---------------------------------------------------------------------
    public void createRandomDots() {
        for (int i = 1; i <= 2; i++) {
            int randomXValue = rd.nextInt(430);
            int randomYValue = rd.nextInt(530);
            this.addDot(randomXValue + 3, randomYValue + 3);
        }
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
    public void scannForLineDotCollision(int x, int y) {
        if (this.yLine1 == y && this.yLine2 == y) {
            xParabole = 0;
            this.addActivatedDot(x, y, xParabole);
        } else {
        }
    }
    //---------------------------------------------------------------------
    public void scannForParaboleCollision(double startX, double startY, double endX, double endY, double curveXpoint, double curveYpoint){
        for(Parabole p : paraboles){
            //for()
        }
    }
    //---------------------------------------------------------------------
    public void drawParaboleforEachDot(){
        for (Dot d : activatedDots) {

            this.addParabole(d.getX()-d.getxParaboleValue(),0,d.getX()+6,d.getY()+(d.getY()+50),d.getX()+d.getxParaboleValue(),0);

            context.beginPath();
            context.moveTo(d.getX()-d.getxParaboleValue(),0);
            context.quadraticCurveTo(d.getX()+6,d.getY()+(d.getY()+50),d.getX()+d.getxParaboleValue(),0);
            context.setStroke(Color.GREEN);
            context.stroke();

            d.setxParaboleValue(d.getxParaboleValue()+1);
        }
    }
    //---------------------------------------------------------------------
}
