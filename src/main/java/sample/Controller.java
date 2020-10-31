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

    private int anzahlParaboles = 8;
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
        this.addEmptyParabolas();
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
            }
        };
        timer.start();
    }
    //---------------------------------------------------------------------
    public void addDot(double x, double y) {
        dots.add(new Dot(x, y));
    }

    public void addActivatedDot(double x, double y, double xParabole) {
        activatedDots.add(new Dot(x, y, xParabole));
    }

    public void addParabola(double a, double u, double v, double minusXPointValue, double plusXPointValue, double incrementedValue) {
        paraboles.add(new Parabole(a, u, v, minusXPointValue, plusXPointValue, incrementedValue));
    }
    //---------------------------------------------------------------------
    public void addEmptyParabolas(){
        for(int i = 0; i<8;i++){
            this.addParabola(0,0,0,0,0,0);
        }
    }
    //---------------------------------------------------------------------
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
        for (int i = 1; i <= anzahlParaboles; i++) {
            int randomXValue = rd.nextInt(430);
            int randomYValue = rd.nextInt(530);
            this.addDot(randomXValue, randomYValue);
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
    public void scannForLineDotCollision(double x, double y) {
        if (this.yLine1 == y && this.yLine2 == y) {
            xParabole = 0;
            this.addActivatedDot(x, y, xParabole);
        } else {
        }
    }
    //---------------------------------------------------------------------
    public void scannForParaboleCollision() {
    }
    //---------------------------------------------------------------------
    public void drawParaboleforEachDot() {

        double a = 0;
        double u = 0;
        double v = 0;
        double minusXPoint = 0;
        double plusXPoint = 450;
        for (Dot d : activatedDots) {
            double i;
            for (i = minusXPoint; i < plusXPoint; i++) {
                a = 1 / (2 * (d.getY() - this.yLine1));
                u = d.getX();
                v = 0.5 * (d.getY() + this.yLine1);
                double pointY = a * ((i - u) * (i - u)) + v;
                double pointX = i;
                context.strokeOval(pointX, pointY, 1, 1);
                context.fillOval(pointX, pointY, 1, 1);

//                this.paraboles.get(activatedDots.size()).setA(a);
//                this.paraboles.get(0).setU(u);
//                this.paraboles.get(0).setV(v);
//                this.paraboles.get(0).setMinusXPointValue(minusXPoint);
//                this.paraboles.get(0).setPlusXPointValue(plusXPoint);
//                this.paraboles.get(0).setIncrementedValue(i);
            }
        }
    }
    //---------------------------------------------------------------------
}
