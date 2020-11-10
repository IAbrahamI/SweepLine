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
    double minusXPoint = 0;
    double plusXPoint = 450;
    private double xLine1, yLine1, xLine2, yLine2, xParabole;
    private Random rd = new Random();
    private GraphicsContext context;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Dot> activatedDots = new ArrayList<Dot>();
    private ArrayList<Dot> voronoiDots = new ArrayList<Dot>();
    private ArrayList<Parabole> paraboles = new ArrayList<Parabole>();

    //---------------------------------------------------------------------
    @FXML
    void startAction(ActionEvent event) {
        this.addEmptyParabolas();
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
        final AnimationTimer timer = new AnimationTimer() {
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
                strokeDotLines();

                context.setStroke(Color.RED);
                context.strokeLine(xLine1, yLine1, xLine2, yLine2);
                if (yLine1 == 1200) {
                    this.stop();
                }
            }
        };
        timer.start();
    }

    //---------------------------------------------------------------------
    public void addDot(double x, double y) {
        dots.add(new Dot(x, y));
    }

    public void addActivatedDot(double x, double y, double xMin, double xMax) {
        activatedDots.add(new Dot(x, y, xMin, xMax));
    }

    public void addDotForLine(double x, double y) {
        voronoiDots.add(new Dot(x, y));
    }

    public void addParabola(double a, double u, double v, double incrementedValue, double bPointX, double bPointY) {
        paraboles.add(new Parabole(a, u, v, incrementedValue, bPointX, bPointY));
    }

    //---------------------------------------------------------------------
    public void addEmptyParabolas() {
        for (int i = 0; i < 8; i++) {
            this.addParabola(0, 0, 0, 0, 0, 0);
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
        for (Dot d : dots) {
            context.setStroke(Color.GREEN);
            context.setFill(Color.GREEN);
            context.strokeOval(d.getX(), d.getY(), 6, 6);
            context.fillOval(d.getX(), d.getY(), 6, 6);
        }
    }

    //---------------------------------------------------------------------
    public void strokeDotLines() {
        for (Dot d : voronoiDots) {
            context.setStroke(Color.BLACK);
            context.setFill(Color.BLACK);
            context.strokeOval(d.getX(), d.getY(), 2, 2);
            context.fillOval(d.getX(), d.getY(), 2, 2);
        }
    }

    //---------------------------------------------------------------------
    public void scannForLineDotCollision(double x, double y) {
        if (this.yLine1 == y && this.yLine2 == y) {
            xParabole = 0;
            this.addActivatedDot(x, y, 0, 450);
        } else {
        }
    }

    //---------------------------------------------------------------------
    public void scannForParaboleCollision(Dot dot, double bPointX, double bPointY, double aParabola) {
        for (Parabole p : paraboles) {
            double e = bPointX * (p.getbPointY() - this.yLine1) - p.getbPointX() * (bPointY - this.yLine1);
            double a = Math.sqrt((bPointY - this.yLine1) * (p.getbPointY() - this.yLine1));
            double d = Math.sqrt(((bPointX - p.getbPointX()) * (bPointX - p.getbPointX()) + (bPointY - p.getbPointY()) * (bPointY - p.getbPointY())));
            double b = p.getbPointY() - bPointY;
            double resultForX = (e + (a * d)) / b;

            double a1 = aParabola;
            double a2 = p.getA();
            double resultForY = a1 * ((resultForX - bPointX) * (resultForX - bPointX)) + 0.5 * (bPointY + this.yLine1);

            if (resultForX <= 450 && resultForX >= 0 && resultForY>=0 && resultForY<=600) {
                if (p.getbPointY() == 0 && bPointY > 0) {
                    this.addDotForLine(0, 0);
                } else if (p.getbPointX() > bPointX) {
//                    dot.setxMax(resultForX);
                } else if (bPointX > p.getbPointX()) {
//                    dot.setxMin(resultForX);
                }
                for (Dot dotVoronoi : voronoiDots) {
                    if (dotVoronoi.getX() == resultForX || dotVoronoi.getY() == resultForY) {
                    } else if (dotVoronoi.getX() != resultForX && dotVoronoi.getY() != resultForY) {
                        this.addDotForLine(resultForX, resultForY);
                        break;
                    }
                }
            }
        }
    }

    //---------------------------------------------------------------------
    public void calculateAndStoreParabola(Dot d, int parabolaID) {
        double a = 0;
        double u = 0;
        double v = 0;
        double pointY = 0;
        double pointX = 0;
        double i;
        for (i = d.getxMin(); i < d.getxMax(); i++) {
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
        scannForParaboleCollision(d, this.paraboles.get(parabolaID).getbPointX(), this.paraboles.get(parabolaID).getbPointY(), this.paraboles.get(parabolaID).getA());
    }

    //---------------------------------------------------------------------
    public void drawParaboleforEachDot() {
        int parabolaID = 0;
        for (Dot d : activatedDots) {
            calculateAndStoreParabola(d, parabolaID);
            parabolaID++;
        }
    }
    //---------------------------------------------------------------------
}
