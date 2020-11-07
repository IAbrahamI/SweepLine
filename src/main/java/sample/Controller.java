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
import java.util.Timer;
import java.util.TimerTask;

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
    private ArrayList<Dot> dotsForLine = new ArrayList<Dot>();
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
<<<<<<< Updated upstream

                drawParaboleforEachDot();
=======
                drawParabolaforEachDot();

                strokeLines();
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
    public void addActivatedDot(int x, int y, double xParabole) {
        activatedDots.add(new Dot(x, y, xParabole));
    }

=======
    public void addActivatedDot(double x, double y) {
        activatedDots.add(new Dot(x, y));
    }
    public void addDotForLine(double x, double y){
        dotsForLine.add(new Dot(x,y));
    }
    public void addParabola(double a, double u, double v, double minusXPointValue, double plusXPointValue, double incrementedValue, double bPointX, double bPointY) {
        paraboles.add(new Parabole(a, u, v, minusXPointValue, plusXPointValue, incrementedValue, bPointX, bPointY));
    }

    //---------------------------------------------------------------------
    public void addEmptyParabolas() {
        for (int i = 0; i < 8; i++) {
            this.addParabola(0, 0, 0, 0, 0, 0, 0, 0);
        }
    }

    //---------------------------------------------------------------------
>>>>>>> Stashed changes
    public void deleteDots() {
        for (Dot d : dots) {
            dots.clear();
        }
        for (Dot d : activatedDots) {
            activatedDots.clear();
        }
    }

    //---------------------------------------------------------------------
    public void createRandomDots() {
        for (int i = 1; i <= 8; i++) {
            int randomXValue = rd.nextInt(430);
            int randomYValue = rd.nextInt(530);
            this.addDot(randomXValue, randomYValue);
        }
    }

    //---------------------------------------------------------------------
    public void strokeDots() {
        for (Dot d : dots) {
            context.setStroke(Color.GREEN);
            context.setFill(Color.GREEN);
            context.strokeOval(d.getX(), d.getY(), 6, 6);
            context.fillOval(d.getX(), d.getY(), 6, 6);
        }
    }

    //---------------------------------------------------------------------
<<<<<<< Updated upstream
    public void scannForLineDotCollision(int x, int y) {
=======
    public void strokeLines(){
        for(Dot  d: dotsForLine){
            context.setStroke(Color.BLACK);
            context.setFill(Color.BLACK);
            context.strokeOval(d.getX(),d.getY(),2,2);
            context.fillOval(d.getX(),d.getY(),2,2);
        }
    }
    //---------------------------------------------------------------------
    public void scannForLineDotCollision(double x, double y) {
>>>>>>> Stashed changes
        if (this.yLine1 == y && this.yLine2 == y) {
            this.addActivatedDot(x, y);
        } else {
        }
    }

    //---------------------------------------------------------------------
<<<<<<< Updated upstream
    public void scannForParaboleCollision() {
    }
    //---------------------------------------------------------------------
    public void drawParaboleforEachDot() {
        for (Dot d : activatedDots) {
            for (int i = -800; i < 800; i++) {
                double a = 1 / (2*(d.getY()-this.yLine1));
                double u = d.getX();
                double v = 0.5*(d.getY()+this.yLine1);

                double pointY = a*((i-u)*(i-u))+v;
                double pointX = i;
                context.strokeOval(pointX,pointY,2,2);
                context.fillOval(pointX,pointY,2,2);
            }
=======
    public void scannForParabolaCollision() {
        for (Parabole p1 : paraboles) {
            for (Parabole p2 : paraboles) {
                double e = p1.getbPointX() * (p2.getbPointY() - this.yLine1) - p2.getbPointX() * (p1.getbPointY() - this.yLine1);
                double a = Math.sqrt((p1.getbPointY() - this.yLine1) * (p2.getbPointY() - this.yLine1));
                double d = Math.sqrt(((p1.getbPointX() - p2.getbPointX()) * (p1.getbPointX() - p2.getbPointX()) + (p1.getbPointY() - p2.getbPointY()) * (p1.getbPointY() - p2.getbPointY())));
                double b = p2.getbPointY() - p1.getbPointY();
                double resultForX = (e + (a * d)) / b;

                double a1 = p1.getA();
                double a2 = p2.getA();
                double resultForY = a1 * ((resultForX - p1.getbPointX()) * (resultForX - p1.getbPointX())) + 0.5 * (p1.getbPointY() + this.yLine1);

                this.addDotForLine(resultForX,resultForY);
                context.strokeOval(resultForX, resultForY, 5, 5);

                if(resultForX <=450 && resultForX >=0){
                    if (p1.getbPointX() > p2.getbPointX()) {
//                        p1.setMinusXPointValue(resultForX);
//                        p2.setPlusXPointValue(resultForX);
                    } else if (p2.getbPointX() > p1.getbPointX()) {
//                        p1.setPlusXPointValue(resultForX);
//                        p2.setMinusXPointValue(resultForX);
                    }
                }
            }
        }
    }
    //----------------------------------------------------------------------
    public void calculateAndStoreParabola(Dot d, double i, int parabolaID){
        double a = 0;
        double u = 0;
        double v = 0;
        double pointY = 0;
        double pointX = 0;

        for (i = minusXPoint; i < plusXPoint; i++) {
            a = 1 / (2 * (d.getY() - this.yLine1));
            u = d.getX();
            v = 0.5 * (d.getY() + this.yLine1);
            pointY = a * ((i - u) * (i - u)) + v;
            pointX = i;
            context.strokeOval(pointX, pointY, 1, 1);
            context.fillOval(pointX, pointY, 1, 1);

            this.paraboles.get(parabolaID).setA(a);
            this.paraboles.get(parabolaID).setU(u);
            this.paraboles.get(parabolaID).setV(v);
            this.paraboles.get(parabolaID).setIncrementedValue(i);
            this.paraboles.get(parabolaID).setbPointX(d.getX());
            this.paraboles.get(parabolaID).setbPointY(d.getY());

        }
//        minusXPoint = this.paraboles.get(parabolaID).getMinusXPointValue();K
//        plusXPoint = this.paraboles.get(parabolaID).getPlusXPointValue();
    }
    //---------------------------------------------------------------------
    public void drawParabolaforEachDot() {
        double i = 0;
        int parabolaID = 0;
        for (Dot d : activatedDots) {
            calculateAndStoreParabola(d,i,parabolaID);
            scannForParabolaCollision();
            parabolaID++;
>>>>>>> Stashed changes
        }
    }
    //---------------------------------------------------------------------
}
